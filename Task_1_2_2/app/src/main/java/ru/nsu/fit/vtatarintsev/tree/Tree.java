package ru.nsu.fit.vtatarintsev.tree;

import java.util.ArrayList;
import java.util.Iterator;

public class Tree<T> implements Iterator<T> {

  private ArrayList<Tree<T>> children;
  private T value;
  private Tree<T> parent;

  public Tree() {
    this.value = null;
    this.parent = null;
    this.children = new ArrayList<>();
  }

  public Tree(T value) {
    this.value = value;
    this.parent = null;
    this.children = new ArrayList<>();
  }

  public Tree<T> add(T value) {
    if (this.value == null) {
      this.value = value;
      return this;
    } else {
      Tree<T> son = new Tree<>(value);
      son.parent = this;
      children.add(son);
      return son;
    }
  }

  public Tree<T> add(Tree<T> tree, T value) throws Exception {
    if (tree == this) {
      return this.add(value);
    } else {
      for (Tree<T> son : children) {
        son.add(tree, value);
      }
    }
    throw new Exception("Node " + tree + " not found in the " + this);
  }

  public void delete() {
    this.value = null;
    this.parent = null;
    for (Tree<T> son : children) {
      son.delete();
      children.clear();
    }
  }

  public void delete(T value) throws Exception {
    if (this.value == value) {
      this.delete();
      return;
    } else {
      for (Tree<T> son : children) {
        son.delete(value);
      }
    }
    throw new Exception("Node with value " + value + " not found in the " + this);
  }

  public void delete(Tree<T> tree) throws Exception {
    if (this == tree) {
      this.delete();
      return;
    } else {
      for (Tree<T> son : children) {
        son.delete(tree);
      }
    }
    throw new Exception("Node " + tree + " not found in the " + this);
  }

  @Override
  public boolean hasNext() {
    return children.size() != 0;
  }

  @Override
  public T next() {
    return null;
  }
}
