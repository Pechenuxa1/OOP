package ru.nsu.fit.vtatarintsev.threads;

import java.util.concurrent.LinkedBlockingQueue;

public class TaskManager implements Runnable {

  LinkedBlockingQueue<Runnable>[] tasks;
  final Object monitor;
  LinkedBlockingQueue<Runnable> futureTasks;

  public TaskManager(LinkedBlockingQueue<Runnable> futureTasks,
      LinkedBlockingQueue<Runnable>[] tasks, Object monitor) {
    this.futureTasks = futureTasks;
    this.tasks = tasks;
    this.monitor = monitor;
  }

  @Override
  public void run() {
    try {
      while (!Thread.currentThread().isInterrupted()) {
        synchronized (monitor) {
          monitor.wait();
          for (LinkedBlockingQueue<Runnable> task : tasks) {
            if (futureTasks.isEmpty()) {
              break;
            }
            task.put(futureTasks.take());
          }
        }
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
