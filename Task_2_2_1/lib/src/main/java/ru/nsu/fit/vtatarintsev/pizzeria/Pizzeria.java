package ru.nsu.fit.vtatarintsev.pizzeria;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Pizzeria implements Producer {

  int orderNumber = 0;
  BlockingQueue<Integer> orders;
  BlockingQueue<String> messageQueue;
  List<Baker> bakers;
  List<Deliveryman> deliverymen;
  BlockingQueue<Integer> storage;
  Logger logger;
  Configuration config;
  List<Integer> cookingTime;
  List<Integer> trunkCapacity;
  int numBakers;
  int numDeliverymen;
  int storageCapacity;
  public Pizzeria(String configFileName) {
    config = new Configuration(configFileName);
    numBakers = config.getNumBakers();
    storageCapacity = config.getStorageCapacity();
    numDeliverymen = config.getNumDeliverymen();
    cookingTime = config.getCookingTime();
    trunkCapacity = config.getTrunkCapacity();
    orders = new LinkedBlockingQueue<>();
    messageQueue = new LinkedBlockingQueue<>();
    bakers = new ArrayList<>(numBakers);
    deliverymen = new ArrayList<>(numDeliverymen);
    storage = new ArrayBlockingQueue<>(storageCapacity);
    logger = new Logger(messageQueue);
  }

  public void startWork() {
    createLogger();
    createWorkers();
  }

  private void createLogger() {
    logger.start();
  }

  private void createWorkers() {
    for (int i = 0; i < numBakers; i++) {
      int time = cookingTime.get(i);
      bakers.add(new Baker(time, orders, storage, messageQueue));
    }
    for (int i = 0; i < numDeliverymen; i++) {
      int capacity = trunkCapacity.get(i);
      deliverymen.add(new Deliveryman(storage, capacity, messageQueue));
    }
    for (Baker baker : bakers) {
      baker.start();
    }
    for (Deliveryman deliveryman : deliverymen) {
      deliveryman.start();
    }
  }

  public synchronized void orderPizza() throws InterruptedException {
    orderNumber += 1;
    messageQueue.put("[ " + orderNumber + " ], " + "[Order is accepted]");
    putOrder(orderNumber);
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
