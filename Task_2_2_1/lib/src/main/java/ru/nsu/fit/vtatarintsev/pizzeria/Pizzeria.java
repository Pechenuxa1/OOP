package ru.nsu.fit.vtatarintsev.pizzeria;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Pizzeria implements Producer {

  int orderNumber = 0;
  BlockingQueue<Integer> orders;
  LinkedBlockingQueue<String> messageQueue;

  public Pizzeria(BlockingQueue<Integer> orders, LinkedBlockingQueue<String> messageQueue) {
    this.orders = orders;
    this.messageQueue = messageQueue;
  }

  public synchronized void orderPizza(String order) throws InterruptedException {
    orderNumber += 1;
    putOrder(orderNumber);
    messageQueue.put("[ " + orderNumber + " ], " + "[Order is accepted]");
  }
  @Override
  public void putOrder(Object order) {
    try {
      orders.put((Integer) order);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
