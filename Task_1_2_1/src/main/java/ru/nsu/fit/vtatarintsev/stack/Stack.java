package ru.nsu.fit.vtatarintsev.stack;

import java.util.EmptyStackException;

/**
 * Implementation of stack.
 *
 * @param <T> is type of elements in stack.
 */
public class Stack<T> {

  private int size = 5;
  @SuppressWarnings("unchecked")
  private T[] stack = (T[]) new Object[size];
  private int numElem = 0;

  /**
   * Method push puts an element on the top of the stack.
   *
   * @param elem is element.
   */
  @SuppressWarnings("unchecked")
  public void push(T elem) {
    if (numElem == size) {
      size *= 2;
      T[] bigStack = (T[]) new Object[size];
      System.arraycopy(stack, 0, bigStack, 0, numElem);
      stack = bigStack;
    }
    stack[numElem] = elem;
    numElem++;
  }

  /**
   * Method pushStack puts all elements from addedStack in stack.
   *
   * @param addedStack is stack from which we take elements.
   */
  public void pushStack(Stack<T> addedStack) {
    for (int i = 0; i < addedStack.count(); i++) {
      push(addedStack.stack[i]);
    }
  }

  /**
   * Method pop takes the top element.
   *
   * @return element from the top of the stack.
   */
  public T pop() {
    if (numElem == 0) {
      throw new EmptyStackException();
    }
    numElem--;
    return stack[numElem];
  }

  /**
   * Method popStack takes the top num elements of the stack.
   *
   * @param num is number elements to take.
   *
   * @return stack with num elements.
   */
  public Stack<T> popStack(int num) {
    if (numElem < num) {
      throw new EmptyStackException();
    }
    numElem -= num;
    Stack<T> deletedStack = new Stack<>();
    for (int i = numElem; i < (numElem + num); i++) {
      deletedStack.push(stack[i]);
    }
    return deletedStack;
  }

  public int count() {
    return numElem;
  }
}

