/*
 * This Java source file was generated by the Gradle 'init' task.
 */

package ru.nsu.fit.vtatarintsev.pizzeria;

/**
 * The class for creating an order by the client.
 */
public class Client extends Thread {

  private final Pizzeria pizzeria;

  public Client(Pizzeria pizzeria) {
    this.pizzeria = pizzeria;
  }

  @Override
  public void run() {
    pizzeria.orderPizza();
  }
}
