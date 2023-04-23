package ru.nsu.fit.vtatarintsev.pizzeria;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Deliveryman implements Runnable {

  List<Integer> trunk;
  int capacity;
  BlockingQueue<Integer> storage; //storage is ArrayBlockingQueue потому что ограниченный размер

  public Deliveryman(BlockingQueue<Integer> storage, int capacity) {
    this.storage = storage;
    this.capacity = capacity;
    this.trunk = new ArrayList<>(capacity);
  }


  @Override
  public void run() {
    try {
      for (Integer orderNumber : trunk) {
        orderNumber = storage.take();
        System.out.println("[ " + orderNumber + " ], " + "[Order is delivered]");
      }
      for (Integer orderNumber : trunk) {
        System.out.println("[ " + orderNumber + " ], " + "[Order completed]");
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
