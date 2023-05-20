package ru.nsu.fit.vtatarintsev.pizzeria;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Deliveryman extends Worker implements Consumer {

  List<Integer> trunk;
  int trunkCapacity;
  BlockingQueue<Integer> storage; //storage is ArrayBlockingQueue потому что ограниченный размер
  BlockingQueue<String> messageQueue;
  int deliveryTime;

  public Deliveryman(BlockingQueue<Integer> storage, int trunkCapacity,
      BlockingQueue<String> messageQueue) {
    this.storage = storage;
    this.trunkCapacity = trunkCapacity;
    this.trunk = new ArrayList<>(trunkCapacity);
    this.messageQueue = messageQueue;
  }

  @Override
  public void run() {
    try {
      while (!Thread.currentThread().isInterrupted()) {
        for (Integer orderNumber : trunk) {
          orderNumber = takeOrder();
          if (orderNumber == 0) {
            break;
          }
        }
        for (Integer orderNumber : trunk) {
          if (orderNumber == 0) {
            break;
          }
          messageQueue.put("[ " + orderNumber + " ], " + "[Order is delivered]");
        }
        for (Integer orderNumber : trunk) {
          if (orderNumber == 0) {
            Thread.currentThread().interrupt();
            break;
          }
          //Thread.sleep(deliveryTime);
          messageQueue.put("[ " + orderNumber + " ], " + "[Order completed]");
        }
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
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
