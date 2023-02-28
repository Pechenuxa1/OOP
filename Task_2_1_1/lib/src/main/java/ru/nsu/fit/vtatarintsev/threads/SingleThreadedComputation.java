package ru.nsu.fit.vtatarintsev.threads;

import java.util.ArrayList;

public class SingleThreadedComputation {

  public boolean searchNonPrimeNumber(ArrayList<Integer> numbers) {
    for (Integer number : numbers) {
      if (!PrimeNumberChecker.isPrime(number)) {
        return true;
      }
    }
    return false;
  }
}
