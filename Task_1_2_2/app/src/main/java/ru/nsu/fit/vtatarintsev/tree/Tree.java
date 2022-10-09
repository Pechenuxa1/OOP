package ru.nsu.fit.vtatarintsev.tree;

import java.util.ArrayList;

public class Tree<T> {

  public ArrayList<T> nodes;

  public void add(T node) {
    nodes.add(node);
  }

  public void remove(T node) {
    nodes.remove(node);
  }
}
