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
    Thread taskManager = new Thread(new TaskManager(numbers, numOfThreads,
        futureTasks, tasks, monitor));
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

  public class Worker implements Runnable {

    int idThread;

    public Worker(int idThread) {
      this.idThread = idThread;
    }

    @Override
    public void run() {
      while (!Thread.currentThread().isInterrupted()) {
        if (tasks[idThread].isEmpty()) {
          synchronized (monitor) {
            monitor.notifyAll();
          }
        }
        try {
          tasks[idThread].take().run();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    }
  }
}

