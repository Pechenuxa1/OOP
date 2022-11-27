package ru.nsu.fit.vtatarintsev.tree;

import java.util.ArrayList;
import java.util.Iterator;

public class TreeIterator<T> implements Iterator<T> {

  private ArrayList<Tree<T>> iteratedNodes;
  private String type;

  public TreeIterator(Tree<T> tree, String type) {
    iteratedNodes = new ArrayList<>();
    this.type = type;
    iteratedNodes.add(tree);
  }

  @Override
  public boolean hasNext() {
    return iteratedNodes.size() != 0;
  }

  @Override
  public T next() {
    Tree<T> node = iteratedNodes.get(0);
    if (type == "DFS") {
      iteratedNodes.remove(0);
      iteratedNodes.addAll(0, node.children);
    } else if (type == "BFS") {
      iteratedNodes.remove(0);
      iteratedNodes.addAll(node.children);
    }
    return node.value;
  }

}
