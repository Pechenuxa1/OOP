package ru.nsu.fit.vtatarintsev.pizzeria;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Baker extends Worker implements Consumer, Producer {

  int cookingTime;
  BlockingQueue<Integer> orders;
  BlockingQueue<String> messageQueue;
  int orderNumber;
  BlockingQueue<Integer> storage; //storage is ArrayBlockingQueue потому что ограниченный размер

  public Baker(int cookingTime, BlockingQueue<Integer> orders, BlockingQueue<Integer> storage,
      BlockingQueue<String> messageQueue) {
    this.cookingTime = cookingTime;
    this.orders = orders;
    this.storage = storage;
    this.messageQueue = messageQueue;
  }

  @Override
  public void run() {
    try {
      while (!Thread.currentThread().isInterrupted()) {
        orderNumber = takeOrder();
        if (orderNumber == 0) {
          Thread.currentThread().interrupt();
        } else {
          messageQueue.put("[ " + orderNumber + " ], " + "[The order has been taken]");
          Thread.sleep(cookingTime);
          putOrder(orderNumber);
          messageQueue.put("[ " + orderNumber + " ], " + "[The order has been delivered]");
        }
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int takeOrder() {
    try {
      return orders.take();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void putOrder(Object orderNumber) {
    try {
      storage.put((Integer) orderNumber);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
