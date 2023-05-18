/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ru.nsu.fit.vtatarintsev.pizzeria;

public class Client implements Runnable {

  int waiting;
  int numOfPizzas;
  Pizzeria pizzeria;
  public Client(int numOfPizzas, Pizzeria pizzeria) {
    this.numOfPizzas = numOfPizzas;
    this.pizzeria = pizzeria;
  }

  @Override
  public void run() {
    try {
      while(numOfPizzas > 0) {
        Thread.sleep(waiting);
        pizzeria.orderPizza("Pizza");
        numOfPizzas -= 1;
      }
      pizzeria.orderPizza("No pizza");
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
