package ru.nsu.fit.vtatarintsev.threads;

import java.util.concurrent.LinkedBlockingQueue;

public class Worker implements Runnable {

  int idThread;
  LinkedBlockingQueue<Runnable>[] tasks;
  final Object monitor;

  public Worker(int idThread, LinkedBlockingQueue<Runnable>[] tasks, Object monitor) {
    this.idThread = idThread;
    this.tasks = tasks;
    this.monitor = monitor;
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

