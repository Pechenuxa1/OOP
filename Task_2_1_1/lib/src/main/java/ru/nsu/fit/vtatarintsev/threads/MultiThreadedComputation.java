package ru.nsu.fit.vtatarintsev.threads;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class MultiThreadedComputation {

  int numOfThreads;

  MultiThreadedComputation(int numOfThreads) {
    this.numOfThreads = numOfThreads;
  }

  public boolean searchNonPrimeNumber(ArrayList<Integer> numbers) throws InterruptedException {
    final boolean[] nonPrime = {false};
    BlockingQueue<Callable> tasks = new ArrayBlockingQueue<>(numbers.size());
    for (Integer number : numbers) {
      tasks.add(new Callable() {
        @Override
        public Object call() throws Exception {
          if (!PrimeNumberChecker.isPrime(number)) {
            return true;
          }
          if (tasks.isEmpty()) {
            return false;
          }
          tasks.take().call();
          return false;
        }
      });
    }
    BlockingQueue<FutureTask<Boolean>> futureTasks = new ArrayBlockingQueue<>(numbers.size());
    futureTasks.addAll(tasks);
    for (int i = 0; i < numOfThreads; i++) {
      futureTasks.add((FutureTask<Boolean>) tasks.take());
      new Thread(futureTasks.get(i)).start();
    }
    for(int i = 0; i < numbers.size(); i++) {
      futureTasks.get(i);
    }
    return false;
  }
}

