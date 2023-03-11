package ru.nsu.fit.vtatarintsev.threads;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class ThreadPoolComputation {

  int numOfThreads;
  ExecutorService service;
  FutureTask<Boolean>[] futureTasks;

  public ThreadPoolComputation(int numOfThreads) {
    this.numOfThreads = numOfThreads;
    service = Executors.newFixedThreadPool(numOfThreads);
  }

  @SuppressWarnings("unchecked")
  public boolean isNonPrimeNumber(ArrayList<Integer> numbers)
      throws InterruptedException, ExecutionException {
    futureTasks = new FutureTask[numbers.size()];
    boolean nonPrime = false;
    for (int j = 0; j < futureTasks.length; j++) {
      int finalJ = j;
      Callable<Boolean> task = () -> !PrimeNumberChecker.isPrime(numbers.get(finalJ));
      futureTasks[finalJ] = new FutureTask<>(task);
    }
    Thread taskManager = new Thread(new TaskManager());
    taskManager.start();
    for (FutureTask<Boolean> futureTask : futureTasks) {
      if (futureTask.get()) {
        nonPrime = true;
        break;
      }
    }
    taskManager.interrupt();
    service.shutdown();
    return nonPrime;
  }

  private class TaskManager implements Runnable {

    @Override
    public void run() {
      for (FutureTask<Boolean> futureTask : futureTasks) {
        if (Thread.currentThread().isInterrupted()) {
          break;
        }
        service.submit(futureTask);
      }
    }
  }

}
