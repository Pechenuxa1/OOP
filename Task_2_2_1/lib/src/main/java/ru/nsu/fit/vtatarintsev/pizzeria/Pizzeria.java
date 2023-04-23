package ru.nsu.fit.vtatarintsev.pizzeria;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Pizzeria {

  static int orderNumber = 0;
  static BlockingQueue<Integer> orders;

  public Pizzeria(BlockingQueue<Integer> orders) {
    Pizzeria.orders = orders;
  }

  public static synchronized void orderPizza() throws InterruptedException {
    orderNumber += 1;
    System.out.println("[ " + orderNumber + " ], " + "[Order is accepted]");
    orders.put(orderNumber);
  }

}
