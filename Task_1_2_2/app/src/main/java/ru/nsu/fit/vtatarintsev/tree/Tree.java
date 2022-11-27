package ru.nsu.fit.vtatarintsev.tree;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Tree<T> implements Iterable<T> {

  public ArrayList<Tree<T>> children;
  public T value;
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

  public Tree<T> add(Tree<T> tree, T value) {
    Tree<T> newNode = null;
    if (tree == this) {
      return this.add(value);
    } else {
      for (Tree<T> son : children) {
        newNode = son.add(tree, value);
        if (newNode != null) break;
      }
    }
    return newNode;
  }

  public void delete() {
    this.value = null;
    this.parent = null;
    for (Tree<T> son : children) {
      son.delete();
      children.clear();
    }
  }

 /* public void delete(T value) throws NoSuchElementException {
    if (this.value == value) {
      this.delete();
      return;
    } else {
      for (Tree<T> son : children) {
        son.delete(value);
      }
    }
    throw new NoSuchElementException("Node with value " + value + " not found in the " + this);
  } */

  public void delete(Tree<T> tree) throws NoSuchElementException {
    if (this == tree) {
      this.delete();
      return;
    } else {
      for (Tree<T> son : children) {
        son.delete(tree);
      }
    }
    throw new NoSuchElementException("Node " + tree + " not found in the " + this);
  }

  public ArrayList<T> getChildrenValues() {
    ArrayList<T> childrenValues = new ArrayList<>();
    for (Tree<T> son : this.children) {
      childrenValues.add(son.value);
    }
    return childrenValues;
  }

  public Iterator<T> iterator(String type) throws Exception {
    if (type == "DFS") {
      return (Iterator<T>) new TreeIterator<>(this, "DFS");
    } else if (type == "BFS") {
      return (Iterator<T>) new TreeIterator<>(this, "BFS");
    }
    throw new Exception("No such an iterator" + type);
  }

  @Override
  public Iterator<T> iterator() {
    return (Iterator<T>) new TreeIterator<>(this, "DFS");
  }
}
