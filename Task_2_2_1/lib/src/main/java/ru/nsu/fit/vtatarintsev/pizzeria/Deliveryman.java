package ru.nsu.fit.vtatarintsev.pizzeria;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Deliveryman extends Worker implements Consumer  {

  List<Integer> trunk;
  int trunkCapacity;
  BlockingQueue<Integer> storage; //storage is ArrayBlockingQueue потому что ограниченный размер
  LinkedBlockingQueue<String> messageQueue;
  int deliveryTime;

  public Deliveryman(BlockingQueue<Integer> storage, int trunkCapacity, LinkedBlockingQueue<String> messageQueue) {
    this.storage = storage;
    this.trunkCapacity = trunkCapacity;
    this.trunk = new ArrayList<>(trunkCapacity);
    this.messageQueue = messageQueue;
  }

  @Override
  public void run() {
    for (Integer orderNumber : trunk) {
      orderNumber = takeOrder();
    }
    for(Integer orderNumber : trunk) {
      try {
        messageQueue.put("[ " + orderNumber + " ], " + "[Order is delivered]");
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    for (Integer orderNumber : trunk) {
      try {
        Thread.sleep(deliveryTime);
        messageQueue.put("[ " + orderNumber + " ], " + "[Order completed]");
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public int takeOrder() {
    try {
      return storage.take();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
