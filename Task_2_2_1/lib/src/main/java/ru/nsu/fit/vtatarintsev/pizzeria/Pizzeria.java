package ru.nsu.fit.vtatarintsev.pizzeria;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Pizzeria implements Producer {

  int orderNumber = 0;
  BlockingQueue<Integer> orders;
  BlockingQueue<String> messageQueue;
  List<Baker> bakers;
  List<Deliveryman> deliverymen;
  BlockingQueue<Integer> storage;

  public Pizzeria(BlockingQueue<Integer> orders, BlockingQueue<String> messageQueue,
      List<Baker> bakers, List<Deliveryman> deliverymen, BlockingQueue<Integer> storage) {
    this.orders = orders;
    this.messageQueue = messageQueue;
    this.bakers = bakers;
    this.deliverymen = deliverymen;
    this.storage = storage;
  }

  public void startWork() {
    createWorkers();
  }

  private void createWorkers() {
    for (Baker baker : bakers) {
      baker.start();
    }
    for (Deliveryman deliveryman : deliverymen) {
      deliveryman.start();
    }
  }

  public synchronized void orderPizza() throws InterruptedException {
    orderNumber += 1;
    putOrder(orderNumber);
    messageQueue.put("[ " + orderNumber + " ], " + "[Order is accepted]");
  }

  public void createPoisonPillOrder() {
    orderNumber = 0;
    finishBakers();
    finishDeliverymen();
  }

  private void finishBakers() {
    for (Baker baker : bakers) {
      try {
        orders.put(orderNumber);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private void finishDeliverymen() {
    for (Deliveryman deliveryman : deliverymen) {
      try {
        storage.put(orderNumber);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public void putOrder(Object orderNumber) {
    try {
      orders.put((Integer) orderNumber);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
