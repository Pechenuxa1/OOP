package ru.nsu.fit.vtatarintsev.tree;

import java.util.ArrayList;
import java.util.Iterator;

public class Tree<T> implements Iterable<T> {

  public enum Type { DFS, BFS }

  private final ArrayList<Tree<T>> children;
  private T value;
  private Tree<T> parent;
  public int numOfOperations;

  public Tree() {
    this.value = null;
    this.parent = null;
    this.children = new ArrayList<>();
    this.numOfOperations = 0;
  }

  public Tree(T value) {
    this.value = value;
    this.parent = null;
    this.children = new ArrayList<>();
    this.numOfOperations = 1;
  }

  public Tree<T> add(T value) {
    this.addNumOfOperations();
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

  private void addNumOfOperations() {
    this.numOfOperations++;
    if (this.parent != null) {
      this.parent.addNumOfOperations();
    }
  }

  public void delete() {
    this.addNumOfOperations();
    this.value = null;
    this.parent.children.remove(this);
    this.parent = null;
    this.children.clear();
  }

  public ArrayList<T> getChildrenValues() {
    ArrayList<T> childrenValues = new ArrayList<>();
    for (Tree<T> son : this.children) {
      childrenValues.add(son.value);
    }
    return childrenValues;
  }

  public ArrayList<Tree<T>> getChildrenTrees() {
    return this.children;
  }

  public T getValue() {
    return this.value;
  }

  public Iterator<T> iterator(Type typeOfIterator) {
    if (typeOfIterator == Type.DFS) {
      return new TreeIterator<>(this, Type.DFS);
    } else {
      return new TreeIterator<>(this, Type.BFS);
    }
  }

  @Override
  public Iterator<T> iterator() {
    return new TreeIterator<>(this, Type.DFS);
  }
}
