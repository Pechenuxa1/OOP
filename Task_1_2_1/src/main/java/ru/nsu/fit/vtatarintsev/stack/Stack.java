package ru.nsu.fit.vtatarintsev.stack;

public class Stack {

  public int size = 5;
  public int[] stack = new int[size];
  public int num_elem = 0;

  public void push(int elem) {
    if (num_elem == size) {
      size *= 2;
      int[] bigStack = new int[size];
      System.arraycopy(stack, 0, bigStack, 0, num_elem);
      stack = bigStack;
    }
    stack[num_elem++] = elem;
  }

  public void pushStack(int[] addedStack) {
    for (int i = 0; i < addedStack.length; i++) {
      push(addedStack[i]);
    }
  }

  public int pop() {
    return stack[num_elem--];
  }

  public int[] popStack(int num) {
    num_elem -= num;
    int[] deletedStack = new int[num];
    System.arraycopy(stack, num_elem, deletedStack, 0, num);
    return deletedStack;
  }

  public int count() {
    return num_elem;
  }
}

