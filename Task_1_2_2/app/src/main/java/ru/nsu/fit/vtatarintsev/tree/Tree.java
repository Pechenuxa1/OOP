package ru.nsu.fit.vtatarintsev.tree;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Implementation of the tree structure.
 *
 * @param <T> is type of tree elements
 */
public class Tree<T> implements Iterable<T> {

  /**
   * Type of iterator.
   */
  public enum Type {
    DFS,
    BFS
  }

  private final ArrayList<Tree<T>> children;
  private T value;
  private Tree<T> parent;

  /**
   * Number of nodes in the tree.
   */
  public int numOfNodes;

  /**
   * Creates a new tree with null root value.
   */
  public Tree() {
    this.value = null;
    this.parent = null;
    this.children = new ArrayList<>();
    this.numOfNodes = 0;
  }

  /**
   * Creates a new tree with not null root value.
   *
   * @param value is value in the root.
   */
  public Tree(T value) {
    this.value = value;
    this.parent = null;
    this.children = new ArrayList<>();
    this.numOfNodes = 1;
  }

  /**
   * Adds new node with T value in the tree.
   *
   * @param value is value of the new node.
   * @return the added node.
   */
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

  /**
   * Deletes node from the tree. The parent of the deleted node becomes the parent for its
   * children.
   */
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

  /**
   * Increases by 1 the number of nodes in the tree and in its parent trees.
   */
  private void addNumOfNodes() {
    this.numOfNodes++;
    if (this.parent != null) {
      this.parent.addNumOfNodes();
    }
  }

  /**
   * Decreases by 1 the number of nodes in the tree and in its parent trees.
   */
  private void deleteNumOfNodes() {
    this.numOfNodes--;
    if (this.parent != null) {
      this.parent.deleteNumOfNodes();
    }
  }

  /**
   * Get all the values of the children of the tree.
   *
   * @return ArrayList of the children values.
   */
  public ArrayList<T> getChildrenValues() {
    ArrayList<T> childrenValues = new ArrayList<>();
    for (Tree<T> son : this.children) {
      childrenValues.add(son.value);
    }
    return childrenValues;
  }

  /**
   * Get all the nodes of the children of the tree.
   *
   * @return ArrayList of the children nodes.
   */
  public ArrayList<Tree<T>> getChildrenTrees() {
    return this.children;
  }

  /**
   * Get the node value.
   *
   * @return node value.
   */
  public T getValue() {
    return this.value;
  }

  /**
   * Select the iterator type.
   *
   * @param typeOfIterator is DFS or BFS.
   * @return type of iterator.
   */
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
