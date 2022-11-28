package ru.nsu.fit.vtatarintsev.tree;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import ru.nsu.fit.vtatarintsev.tree.Tree.Type;

public class TreeIterator<T> implements Iterator<T> {

  private final ArrayList<Tree<T>> iteratedNodes;
  private final Tree.Type typeOfIterator;
  private final Tree<T> node;
  private final int numOfOperations;

  public TreeIterator(Tree<T> tree, Tree.Type typeOfIterator) {
    iteratedNodes = new ArrayList<>();
    this.typeOfIterator = typeOfIterator;
    iteratedNodes.add(tree);
    this.node = tree;
    this.numOfOperations = tree.numOfOperations;
  }

  @Override
  public boolean hasNext() {
    if (this.numOfOperations < node.numOfOperations) {
      throw new ConcurrentModificationException();
    }
    return iteratedNodes.size() != 0;
  }

  @Override
  public T next() {
    if (this.numOfOperations < node.numOfOperations) {
      throw new ConcurrentModificationException();
    }
    Tree<T> node = iteratedNodes.get(0);
    if (typeOfIterator == Type.DFS) {
      iteratedNodes.remove(0);
      iteratedNodes.addAll(0, node.getChildrenTrees());
    } else if (typeOfIterator == Type.BFS) {
      iteratedNodes.remove(0);
      iteratedNodes.addAll(node.getChildrenTrees());
    }
    return node.getValue();
  }

}
