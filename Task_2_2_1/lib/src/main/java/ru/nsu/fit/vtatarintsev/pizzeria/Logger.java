package ru.nsu.fit.vtatarintsev.pizzeria;

import java.util.concurrent.BlockingQueue;

/**
 * The class for displaying messages from messageQueue.
 */
public class Logger extends Thread {

  private final BlockingQueue<String> messageQueue;

  public Logger(BlockingQueue<String> messageQueue) {
    this.messageQueue = messageQueue;
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      try {
        String message = messageQueue.take();
        if (message.equals("stop")) {
          Thread.currentThread().interrupt();
        } else {
          System.out.println(message);
        }
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
