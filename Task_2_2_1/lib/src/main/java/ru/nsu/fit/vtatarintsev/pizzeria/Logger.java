package ru.nsu.fit.vtatarintsev.pizzeria;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Logger extends Thread {

  BlockingQueue<String> messageQueue;
  String message;

  public Logger(BlockingQueue<String> messageQueue) {
    this.messageQueue = messageQueue;
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      try {
        message = messageQueue.take();
        System.out.println(message);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
