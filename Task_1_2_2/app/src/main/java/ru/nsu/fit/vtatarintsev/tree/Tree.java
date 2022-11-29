package ru.nsu.fit.vtatarintsev.tree;

import java.util.ArrayList;
import java.util.Iterator;

public class Tree<T> implements Iterable<T> {

  public enum Type {DFS, BFS}

  private final ArrayList<Tree<T>> children;
  private T value;
  private Tree<T> parent;
  public int numOfNodes;

  public Tree() {
    this.value = null;
    this.parent = null;
    this.children = new ArrayList<>();
    this.numOfNodes = 0;
  }

  public Tree(T value) {
    this.value = value;
    this.parent = null;
    this.children = new ArrayList<>();
    this.numOfNodes = 1;
  }

  public Tree<T> add(T value) {
    this.addNumOfNodes();
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

  public void delete() {
    this.deleteNumOfNodes();
    this.value = null;
    for (Tree<T> son : children) {
      son.parent = this.parent;
      this.parent.children.add(son);
    }
    this.parent.children.remove(this);
    this.children.clear();
    this.parent = null;
  }

  private void addNumOfNodes() {
    this.numOfNodes++;
    if (this.parent != null) {
      this.parent.addNumOfNodes();
    }
  }

  private void deleteNumOfNodes() {
    this.numOfNodes--;
    if (this.parent != null) {
      this.parent.deleteNumOfNodes();
    }
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
