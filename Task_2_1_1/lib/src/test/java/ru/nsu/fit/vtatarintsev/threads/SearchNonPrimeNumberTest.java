/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ru.nsu.fit.vtatarintsev.threads;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;

class SearchNonPrimeNumberTest {

  @Test
  void bigPrimeNumbersArrayTest() throws InterruptedException, ExecutionException {
    ArrayList<Integer> arrayList = new ArrayList<>(Arrays.asList(6997901, 6997927, 6997937,
        6997967, 6998009, 6998029, 6998039, 6998051, 6998053));
    assertFalse(SingleThreadedComputation.isNonPrimeNumber(arrayList));
    MultiThreadedComputation multiThreadedComputation = new MultiThreadedComputation(2);
    assertFalse(multiThreadedComputation.isNonPrimeNumber(arrayList));
    ThreadPoolComputation threadPoolComputation = new ThreadPoolComputation(2);
    assertFalse(threadPoolComputation.isNonPrimeNumber(arrayList));
    ParallelStreamComputation parallelStreamComputation = new ParallelStreamComputation();
    assertFalse(parallelStreamComputation.isNonPrimeNumber(arrayList));
  }

  @Test
  void nonPrimeNumberAtTheEndTest() throws ExecutionException, InterruptedException {
    ArrayList<Integer> arrayList = new ArrayList<>(Arrays.asList(6997901, 6997927, 6997937,
        6997967, 6998009, 6998029, 6998039, 6998051, 6998053, 4));
    assertTrue(SingleThreadedComputation.isNonPrimeNumber(arrayList));
    MultiThreadedComputation multiThreadedComputation = new MultiThreadedComputation(2);
    assertTrue(multiThreadedComputation.isNonPrimeNumber(arrayList));
    ThreadPoolComputation threadPoolComputation = new ThreadPoolComputation(2);
    assertTrue(threadPoolComputation.isNonPrimeNumber(arrayList));
    ParallelStreamComputation parallelStreamComputation = new ParallelStreamComputation();
    assertTrue(parallelStreamComputation.isNonPrimeNumber(arrayList));
  }

  @Test
  void nonPrimeNumbersTest() throws ExecutionException, InterruptedException {
    ArrayList<Integer> arrayList = new ArrayList<>(Arrays.asList(4, 6, 8));
    assertTrue(SingleThreadedComputation.isNonPrimeNumber(arrayList));
    MultiThreadedComputation multiThreadedComputation = new MultiThreadedComputation(2);
    assertTrue(multiThreadedComputation.isNonPrimeNumber(arrayList));
    ThreadPoolComputation threadPoolComputation = new ThreadPoolComputation(2);
    assertTrue(threadPoolComputation.isNonPrimeNumber(arrayList));
    ParallelStreamComputation parallelStreamComputation = new ParallelStreamComputation();
    assertTrue(parallelStreamComputation.isNonPrimeNumber(arrayList));
  }
}
