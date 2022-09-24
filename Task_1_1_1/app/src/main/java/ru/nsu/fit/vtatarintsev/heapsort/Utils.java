package ru.nsu.fit.vtatarintsev.heapsort;

/**
 * Utils class contains the methods necessary for sorting.
 */
public class Utils extends Heapsort {

  /**
   * siftDown finds the largest element starting from the root.
   *
   * @param heap is array.
   */
  public static void siftDown(int[] heap) {
    if (((v1 * 2) + 2) < n) {
      if ((heap[v1] < heap[(v1 * 2) + 1]) || (heap[v1] < heap[(v1 * 2) + 2])) {
        if (heap[(v1 * 2) + 1] >= heap[(v1 * 2) + 2]) {
          v2 = (v1 * 2) + 1;
          swap(heap);
          v1 = v2;
          siftDown(heap);
        } else if (heap[(v1 * 2) + 1] < heap[(v1 * 2) + 2]) {
          v2 = (v1 * 2) + 2;
          swap(heap);
          v1 = v2;
          siftDown(heap);
        }
      }
    } else if (((v1 * 2) + 2) == n) {
      if ((heap[v1] < heap[(v1 * 2) + 1])) {
        v2 = (v1 * 2) + 1;
        swap(heap);
        v1 = v2;
        siftDown(heap);
      }
    }
  }

  /**
   * siftUp puts the largest element of the array at the root.
   *
   * @param heap is array.
   */
  public static void siftUp(int[] heap) {
    if (heap[v1] > heap[v2] && v1 != 0) {
      swap(heap);
      v1 = v2;
      v2 = (v2 - 1) / 2;
      siftUp(heap);
    }
  }

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
