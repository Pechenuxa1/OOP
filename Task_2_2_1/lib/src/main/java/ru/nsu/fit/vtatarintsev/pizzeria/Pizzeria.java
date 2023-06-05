package ru.nsu.fit.vtatarintsev.pizzeria;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The class implements the work of the pizzeria.
 */
public class Pizzeria {

  private int orderNumber = 0;
  private final BlockingQueue<Integer> orders;
  private final BlockingQueue<String> messageQueue;
  private final List<Baker> bakers;
  private final List<Deliveryman> deliverymen;
  private final BlockingQueue<Integer> storage;
  private final Logger logger;
  private final List<Integer> cookingTime;
  private final List<Integer> trunkCapacity;
  private final int numBakers;
  private final int numDeliverymen;

  /**
   * Constructor for defining pizzeria parameters.
   *
   * @param configFileName json file to load pizzeria parameters
   */
  public Pizzeria(String configFileName) {
    Configuration config = new Configuration(configFileName);
    numBakers = config.getNumBakers();
    final int storageCapacity = config.getStorageCapacity();
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

  /**
   * The method for creating an order.
   */
  public synchronized void orderPizza() {
    try {
      orderNumber += 1;
      messageQueue.put("[ " + orderNumber + " ], " + "[Order is accepted]");
      orders.put(orderNumber);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * The method to shut down pizzeria.
   */
  public void finishWork() {
    orderNumber = 0;
    finishBakers();
    finishDeliverymen();
    finishLogger();
  }

  private void finishBakers() {
    for (Baker baker : bakers) {
      try {
        orders.put(orderNumber);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    for (Baker baker : bakers) {
      try {
        baker.join();
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
    for (Deliveryman deliveryman : deliverymen) {
      try {
        deliveryman.join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private void finishLogger() {
    try {
      messageQueue.put("stop");
      logger.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
