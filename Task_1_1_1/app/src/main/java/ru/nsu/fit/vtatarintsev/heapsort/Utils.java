package ru.nsu.fit.vtatarintsev.heapsort;

/**
 * Utils class contains the methods necessary for sorting.
 */
public class Utils {

  /**
   * swap method swaps elements when sorting.
   *
   * @param v1 is first element of array.
   * @param v2 is second element of array.
   * @param array is array in which the elements are changed.
   */
  public static void swap(int v1, int v2, int[] array) {
    int var = array[v1];
    array[v1] = array[v2];
    array[v2] = var;
  }
}
