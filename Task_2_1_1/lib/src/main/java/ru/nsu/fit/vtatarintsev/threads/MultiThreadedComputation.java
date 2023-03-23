package ru.nsu.fit.vtatarintsev.threads;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MultiThreadedComputation {

  int numOfThreads;
  final Object monitor = new Object();
  ArrayBlockingQueue<Runnable>[] tasks;
  FutureTask<Boolean>[] futureTasks;

  @SuppressWarnings("unchecked")
  public MultiThreadedComputation(int numOfThreads) {
    this.numOfThreads = numOfThreads;
    tasks = new ArrayBlockingQueue[numOfThreads];
  }

  @SuppressWarnings("unchecked")
  public boolean isNonPrimeNumber(ArrayList<Integer> numbers)
      throws InterruptedException, ExecutionException {
    boolean nonPrime = false;
    for (int i = 0; i < tasks.length; i++) {
      tasks[i] = new ArrayBlockingQueue<>(numbers.size());
    }
    futureTasks = new FutureTask[numbers.size()];
    Thread taskManager = new Thread(new TaskManager(numbers));
    taskManager.start();
    ArrayList<Thread> threads = new ArrayList<>();
    for (int idThread = 0; idThread < numOfThreads; idThread++) {
      Thread thread = new Thread(new Worker(idThread));
      threads.add(thread);
      thread.start();
    }
    taskManager.join();
    for (int i = 0; i < numbers.size(); i++) {
      if (futureTasks[i].get()) {
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

    int idThread;

    private Worker(int idThread) {
      this.idThread = idThread;
    }

    @Override
    public void run() {
      while (!Thread.currentThread().isInterrupted()) {
        try {
          if (tasks[idThread].isEmpty()) {
            synchronized (monitor) {
              monitor.notifyAll();
            }
          }
          tasks[idThread].take().run();
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
            for (int idThread = 0; idThread < numOfThreads; idThread++) {
              if (j == numbers.size()) {
                break;
              }
              int finalJ = j;
              futureTasks[finalJ] = new FutureTask<>(
                  () -> !PrimeNumberChecker.isPrime(numbers.get(finalJ)));
              tasks[idThread].put(futureTasks[finalJ]);
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

