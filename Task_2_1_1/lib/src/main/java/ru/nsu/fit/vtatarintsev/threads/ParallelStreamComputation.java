package ru.nsu.fit.vtatarintsev.threads;

import java.util.ArrayList;

public class ParallelStreamComputation {

  public boolean isNonPrimeNumber(ArrayList<Integer> numbers) {
    return !numbers
        .parallelStream()
        .allMatch(PrimeNumberChecker::isPrime);
  }

}
