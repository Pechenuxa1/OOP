package ru.nsu.fit.vtatarintsev.pizzeria;

/**
 * The abstract class for describing the work of a worker.
 */
public abstract class Worker extends Thread {

  @Override
  public abstract void run();
}
