package ru.nsu.fit.vtatarintsev.pizzeria;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * The class implements the work of the deliveryman.
 */
public class Deliveryman extends Worker implements Consumer {

  private final List<Integer> trunk;
  private final int trunkCapacity;
  private final BlockingQueue<Integer> storage;
  private final BlockingQueue<String> messageQueue;

  /**
   * Constructor for create a deliveryman.
   *
   * @param storage queue storage from where the deliveryman takes the order for delivery.
   * @param trunkCapacity the number of pizzas that the deliveryman can take from the storage.
   * @param messageQueue queue for displaying messages about the status of the order.
   */
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
        for (int i = 0; i < trunkCapacity; i++) {
          int orderNumber = takeOrder();
          trunk.add(orderNumber);
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
          int deliveryTime = (int) (Math.random() * 1000);
          Thread.sleep(deliveryTime);
          messageQueue.put("[ " + orderNumber + " ], " + "[Order completed]");
        }
        trunk.clear();
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public synchronized int takeOrder() {
    try {
      return storage.take();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
