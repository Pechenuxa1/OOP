package ru.nsu.fit.vtatarintsev.tree;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import ru.nsu.fit.vtatarintsev.tree.Tree.Type;

/**
 * Iterator for tree structure.
 *
 * @param <T> type of objects in the tree.
 */
public class TreeIterator<T> implements Iterator<T> {

  private final ArrayList<Tree<T>> iteratedNodes;
  private final Tree.Type typeOfIterator;
  private final Tree<T> root;
  private final int finalNumOfNodes;

  /**
   * Builds iterator and adds tree node in iteratedNodes.
   *
   * @param tree           is root node.
   * @param typeOfIterator is DFS or BFS.
   */
  public TreeIterator(Tree<T> tree, Tree.Type typeOfIterator) {
    iteratedNodes = new ArrayList<>();
    this.typeOfIterator = typeOfIterator;
    iteratedNodes.add(tree);
    this.root = tree;
    this.finalNumOfNodes = tree.numOfNodes;
  }

  @Override
  public boolean hasNext() {
    if (this.finalNumOfNodes != root.numOfNodes) {
      throw new ConcurrentModificationException();
    }
    return iteratedNodes.size() != 0;
  }

  @Override
  public T next() {
    if (this.finalNumOfNodes != root.numOfNodes) {
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
