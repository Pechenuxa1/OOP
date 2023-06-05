package ru.nsu.fit.vtatarintsev.pizzeria;

import java.util.concurrent.BlockingQueue;

/**
 * The class implements the work of the baker.
 */
public class Baker extends Worker implements Consumer, Producer {

  private final int cookingTime;
  private final BlockingQueue<Integer> orders;
  private final BlockingQueue<String> messageQueue;
  private final BlockingQueue<Integer> storage;

  /**
   * Constructor for create a baker.
   *
   * @param cookingTime constant pizza cooking time for the baker.
   * @param orders queue from which the baker takes orders.
   * @param storage queue storage where the baker puts the order after cooking.
   * @param messageQueue queue for displaying messages about the status of the order.
   */
  public Baker(int cookingTime, BlockingQueue<Integer> orders, BlockingQueue<Integer> storage,
      BlockingQueue<String> messageQueue) {
    this.cookingTime = cookingTime;
    this.orders = orders;
    this.storage = storage;
    this.messageQueue = messageQueue;
  }

  @Override
  public void run() {
    try {
      while (!Thread.currentThread().isInterrupted()) {
        int orderNumber = takeOrder();
        if (orderNumber == 0) {
          Thread.currentThread().interrupt();
        } else {
          messageQueue.put("[ " + orderNumber + " ], " + "[The order has been taken]");
          Thread.sleep(cookingTime);
          putOrder(orderNumber);
          messageQueue.put("[ " + orderNumber + " ], " + "[The order has been delivered]");
        }
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public synchronized int takeOrder() {
    try {
      return orders.take();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public synchronized void putOrder(Object orderNumber) {
    try {
      storage.put((Integer) orderNumber);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
