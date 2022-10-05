package ru.nsu.fit.vtatarintsev.stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.EmptyStackException;
import org.junit.jupiter.api.Test;

public class StackTest {

  @Test
  void test1() {
    Stack<Integer> stack = new Stack<>();
    stack.push(1);
    assertEquals(1, stack.count());
    assertEquals(1, stack.pop());
    assertEquals(0, stack.count());
  }

  @Test
  void test2() {
    Stack<Integer> stack = new Stack<>();
    Stack<Integer> pushed_stack = new Stack<>();
    Stack<Integer> newStack;
    for (int i = 1; i <= 10; i++) {
      pushed_stack.push(i);
    }
    assertEquals(10, pushed_stack.count());
    assertEquals(0, stack.count());
    stack.pushStack(pushed_stack);
    assertEquals(10, stack.count());
    assertEquals(10, stack.pop());
    assertEquals(9, stack.pop());
    assertEquals(8, stack.pop());
    assertEquals(7, stack.count());
    newStack = stack.popStack(7);
    assertEquals(7, newStack.count());
    for (int i = 7; i >= 1; i--) {
      assertEquals(i, newStack.pop());
    }
    assertEquals(0, newStack.count());
  }

  @Test
  void test3() {
    Stack<Integer> stack = new Stack<>();
    assertThrows(EmptyStackException.class, stack::pop);
    assertThrows(EmptyStackException.class, () -> stack.popStack(1));
  }

  @Test
  void test4() {
    Stack<String> stack_1 = new Stack<>();
    Stack<String> stack_2 = new Stack<>();
    Stack<String> stack_3;
    Stack<String> stack_4;
    stack_1.push("a");
    stack_1.push("b");
    stack_2.push("c");
    stack_2.push("d");
    stack_1.pushStack(stack_2);
    assertEquals(4, stack_1.count());
    assertEquals(2, stack_2.count());
    stack_3 = stack_1.popStack(2);
    assertEquals(2, stack_1.count());
    assertEquals(2, stack_3.count());
    stack_4 = stack_3.popStack(1);
    assertEquals("d", stack_4.pop());
    assertEquals("c", stack_3.pop());
    assertEquals(0, stack_3.count());
    assertEquals(0, stack_4.count());
  }
}
