package ru.nsu.fit.vtatarintsev.threads;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;

public class MultiThreadedComputation {

  int numOfThreads;
  ArrayList<LinkedBlockingQueue<Callable<Boolean>>> tasks = new ArrayList<>();
  final Object monitor = new Object();
  final Object monitor2 = new Object();
  BlockingQueue<Boolean> results;

  public MultiThreadedComputation(int numOfThreads) {
    this.numOfThreads = numOfThreads;
    //tasks = new ArrayList<>(numOfThreads);
  }

  public boolean isNonPrimeNumber(ArrayList<Integer> numbers) throws InterruptedException {
    results = new ArrayBlockingQueue<>(numbers.size());
    boolean nonPrime = false;
    ArrayList<Thread> threads = new ArrayList<>();
    Thread taskManager = new Thread(new TaskManager(numbers));
    taskManager.start();
    for (int i = 0; i < numOfThreads; i++) {
      Thread thread = new Thread(new Worker(i));
      threads.add(thread);
      thread.start();
    }
    for (Thread thread : threads) {
      thread.join();
    }
    for (int j = 0; j < numbers.size(); j++) {
      if (results.take()) {
        nonPrime = true;
        break;
      }
    }
    for (Thread thread : threads) {
      thread.interrupt();
    }
    taskManager.interrupt();
//    monitor.notify();
    return nonPrime;
  }

  private class Worker implements Runnable {

    int i;

    private Worker(int i) {
      this.i = i;
    }

    @Override
    public void run() {
      while (!Thread.currentThread().isInterrupted()) {
        try {
          if (tasks.get(i).isEmpty()) {
            monitor.notify();
          }
          Callable<Boolean> task = tasks.get(i).take();
          results.put(task.call());
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

  private class TaskManager implements Runnable {

    ArrayList<Integer> numbers;

    private TaskManager(ArrayList<Integer> numbers) {
      this.numbers = numbers;
    }

    @Override
    public void run() {
      try {
        while (!Thread.currentThread().isInterrupted()) {
          synchronized (monitor) {
            monitor.wait();
            for (int i = 0; i < numOfThreads; i++) {
              if (numbers.isEmpty()) {
                break;
              }
              tasks.get(i).put(() -> {
                if (!PrimeNumberChecker.isPrime(numbers.get(0))) {
                  numbers.remove(0);
                  return true;
                }
                numbers.remove(0);
                return false;
              });
            }
          }
        }
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}

