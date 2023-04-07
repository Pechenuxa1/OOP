package ru.nsu.fit.vtatarintsev.threads;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;

public class MultiThreadedComputation {

  int numOfThreads;
  final Object monitor = new Object();
  LinkedBlockingQueue<Runnable>[] tasks;
  Thread taskManager;
  ArrayList<Thread> threads;
  LinkedBlockingQueue<Runnable> futureTasks = new LinkedBlockingQueue<>();

  @SuppressWarnings("unchecked")
  public MultiThreadedComputation(int numOfThreads) {
    this.numOfThreads = numOfThreads;
    tasks = new LinkedBlockingQueue[numOfThreads];
    for (int i = 0; i < tasks.length; i++) {
      tasks[i] = new LinkedBlockingQueue<>();
    }
  }

  public void start() {
    taskManager = new Thread(new TaskManager(futureTasks, tasks, monitor));
    taskManager.start();
    threads = new ArrayList<>();
    for (int idThread = 0; idThread < numOfThreads; idThread++) {
      Thread thread = new Thread(new Worker(idThread, tasks, monitor));
      threads.add(thread);
      thread.start();
    }
  }

  public void end() {
    taskManager.interrupt();
    for (Thread thread : threads) {
      thread.interrupt();
    }
  }

  public void execute(Runnable task) {
    futureTasks.add(task);
  }
}

