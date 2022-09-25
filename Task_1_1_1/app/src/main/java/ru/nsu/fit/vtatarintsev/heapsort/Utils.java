package ru.nsu.fit.vtatarintsev.heapsort;

/**
 * Utils class contains the methods necessary for sorting.
 */
public class Utils extends Heapsort {

  /**
   * swap method swaps elements when sorting.
   *
   * @param heap is array.
   */
  public static void swap(int[] heap) {
    int var = heap[v1];
    heap[v1] = heap[v2];
    heap[v2] = var;
  }
}
