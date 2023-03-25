package ru.nsu.fit.vtatarintsev.threads;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.FutureTask;

public class TaskManager implements Runnable {

  ArrayList<Integer> numbers;
  int numOfThreads;
  FutureTask<Boolean>[] futureTasks;
  ArrayBlockingQueue<Runnable>[] tasks;
  final Object monitor;

  public TaskManager(ArrayList<Integer> numbers, int numOfThreads,
      FutureTask<Boolean>[] futureTasks, ArrayBlockingQueue<Runnable>[] tasks, Object monitor) {
    this.numbers = numbers;
    this.numOfThreads = numOfThreads;
    this.futureTasks = futureTasks;
    this.tasks = tasks;
    this.monitor = monitor;
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
