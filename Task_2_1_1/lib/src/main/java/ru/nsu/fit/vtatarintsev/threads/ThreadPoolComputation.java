package ru.nsu.fit.vtatarintsev.threads;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolComputation {

  int numOfThreads;
  static ExecutorService service;
  static ArrayList<Callable<Boolean>> tasks = new ArrayList<>();
  static BlockingQueue<Future<Boolean>> results;

  public ThreadPoolComputation(int numOfThreads) {
    this.numOfThreads = numOfThreads;
    service = Executors.newFixedThreadPool(numOfThreads);
  }

  public boolean isNonPrimeNumber(ArrayList<Integer> numbers)
      throws InterruptedException, ExecutionException {
    results = new ArrayBlockingQueue<>(numbers.size());
    boolean nonPrime = false;
    for (Integer number : numbers) {
      tasks.add(() -> !PrimeNumberChecker.isPrime(number));
    }
    Thread taskManager = new Thread(new TaskManager());
    taskManager.start();
    for (Future<Boolean> result : results) {
      if(result.get()) {
        nonPrime = true;
        break;
      }
    }
    taskManager.interrupt();
    service.shutdown();
    return nonPrime;
  }

  private static class TaskManager implements Runnable {

    @Override
    public void run() {
      for (Callable<Boolean> task : tasks) {
        try {
          if(Thread.currentThread().isInterrupted()) {
            break;
          }
          results.put(service.submit(task));
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

}
