package ru.nsu.fit.vtatarintsev.pizzeria;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Baker implements Runnable {

  int cookingTime;
  BlockingQueue<Integer> orders;
  BlockingQueue<Integer> storage; //storage is ArrayBlockingQueue потому что ограниченный размер

  public Baker(int cookingTime, BlockingQueue<Integer> orders, BlockingQueue<Integer> storage) {
    this.cookingTime = cookingTime;
    this.orders = orders;
    this.storage = storage;
  }

  @Override
  public void run() {
    Integer orderNumber = null;
    try {
      orderNumber = orders.take();
      System.out.println("[ " + orderNumber + " ], " + "[Order is being prepared]");
      Thread.sleep(cookingTime);
      storage.put(orderNumber);
      System.out.println("[ " + orderNumber + " ], " + "[Order in storage]");
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
