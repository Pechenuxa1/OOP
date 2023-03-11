package ru.nsu.fit.vtatarintsev.threads;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MultiThreadedComputation {

  int numOfThreads;
  final Object monitor = new Object();
  ArrayBlockingQueue<Callable<Boolean>>[] tasks;
  BlockingQueue<Boolean> results;

  @SuppressWarnings("unchecked")
  public MultiThreadedComputation(int numOfThreads) {
    this.numOfThreads = numOfThreads;
    tasks = new ArrayBlockingQueue[numOfThreads];
  }

  public boolean isNonPrimeNumber(ArrayList<Integer> numbers)
      throws InterruptedException, ExecutionException {
    boolean nonPrime = false;
    results = new ArrayBlockingQueue<>(numbers.size());
    for (int i = 0; i < tasks.length; i++) {
      tasks[i] = new ArrayBlockingQueue<>(numbers.size());
    }
    //FutureTask<Boolean> futureTask = new FutureTask<>(() -> !PrimeNumberChecker.isPrime(numbers.get(9)));
    //futureTask.run();
    //nonPrime = futureTask.get();
    Thread taskManager = new Thread(new TaskManager(numbers));
    taskManager.start();
    ArrayList<Thread> threads = new ArrayList<>();
    for (int i = 0; i < numOfThreads; i++) {
      Thread thread = new Thread(new Worker(i));
      threads.add(thread);
      thread.start();
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
    return nonPrime;
  }

  private class Worker implements Runnable {

    int i;

    private Worker(int i) {
      this.i = i;
    }

    @Override
    public void run() {
      Callable<Boolean> task;
      while (!Thread.currentThread().isInterrupted()) {
        try {
          if (tasks[i].isEmpty()) {
            synchronized (monitor) {
              monitor.notify();
            }
          }
          task = tasks[i].take();
          results.put(task.call());
        } catch (Exception ignored) {
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
        int j = 0;
        while (j != numbers.size()) {
          synchronized (monitor) {
            monitor.wait();
            for (int i = 0; i < numOfThreads; i++) {
              if (j == numbers.size()) {
                break;
              }
              int finalJ = j;
              tasks[i].put(() -> !PrimeNumberChecker.isPrime(numbers.get(finalJ)));
              j++;
            }
          }
        }
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}

