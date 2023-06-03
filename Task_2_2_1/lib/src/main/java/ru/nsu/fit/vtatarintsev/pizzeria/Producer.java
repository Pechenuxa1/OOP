package ru.nsu.fit.vtatarintsev.pizzeria;

/**
 * The interface for describing the work of a producer worker.
 */
public interface Producer {

  void putOrder(Object order);
}
