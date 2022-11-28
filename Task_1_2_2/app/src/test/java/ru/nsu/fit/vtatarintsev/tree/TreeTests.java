/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ru.nsu.fit.vtatarintsev.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.vtatarintsev.tree.Tree.Type;

class TreeTests {


  @Test
  void addRoot() {
    Tree<String> treeA = new Tree<>();
    treeA.add("A");
    assertEquals(treeA.getValue(), "A");

    Tree<String> treeB = new Tree<>("B");
    assertEquals(treeB.getValue(), "B");
  }

  @Test
  void addRootChildren() {
    Tree<String> treeA = new Tree<>("A");
    treeA.add("B");
    treeA.add("C");
    treeA.add("D");
    assertEquals(treeA.getChildrenValues().size(), 3);

    ArrayList<String> listOfChildren = new ArrayList<>();
    listOfChildren.add("B");
    listOfChildren.add("C");
    listOfChildren.add("D");
    assertEquals(treeA.getChildrenValues(), listOfChildren);
  }

  @Test
  void addOtherNodes() {
    Tree<String> treeA = new Tree<>("A");                   //               A
    Tree<String> nodeB = treeA.add("B");                         //              /  \
    treeA.add("C");                                              //             B    C
    nodeB.add("D");                                              //           / | \
    Tree<String> nodeE = nodeB.add("E");                         //          D  E  F
    nodeB.add("F");                                              //             |
    Tree<String> nodeG = nodeE.add("G");                         //             G
    Tree<String> nodeH = nodeG.add("H");                         //             |
    ArrayList<String> aChildren = new ArrayList<>();            //              H
    aChildren.add("B");
    aChildren.add("C");
    assertEquals(treeA.getChildrenValues(), aChildren);

    ArrayList<String> gChildren = new ArrayList<>();
    gChildren.add("H");
    assertEquals(nodeG.getChildrenValues(), gChildren);

    ArrayList<String> hChildren = new ArrayList<>();
    assertEquals(nodeH.getChildrenValues(), hChildren);
  }

  @Test
  void deleteNodes() {
    Tree<String> treeA = new Tree<>("A");
    Tree<String> nodeB = treeA.add("B");
    nodeB.add("C");
    nodeB.add("D");
    assertEquals(nodeB.getChildrenValues().size(), 2);

    nodeB.delete();
    assertEquals(nodeB.getChildrenTrees().size(), 0);
    assertEquals(treeA.getChildrenTrees().size(), 0);

    Tree<String> nodeD = treeA.add("D");
    Tree<String> nodeE = nodeD.add("E");
    Tree<String> nodeF = nodeD.add("F");
    nodeE.delete();
    assertEquals(nodeD.getChildrenTrees().size(), 1);

    nodeF.delete();
    assertEquals(nodeD.getChildrenTrees().size(), 0);
  }

  @Test
  void dfs() {
    Tree<String> treeA = new Tree<>("A");
    Tree<String> nodeB = treeA.add("B");
    treeA.add("C");
    nodeB.add("D");
    Tree<String> nodeE = nodeB.add("E");
    nodeB.add("F");
    Tree<String> nodeG = nodeE.add("G");
    nodeG.add("H");
    String[] dfsArray = {"A", "B", "D", "E", "G", "H", "F", "C"};
    Iterator<String> dfsIterator = treeA.iterator();
    for (String element : dfsArray) {
      assertTrue(dfsIterator.hasNext());
      assertEquals(dfsIterator.next(), element);
    }
  }

  @Test
  void bfs() {
    Tree<String> treeA = new Tree<>("A");
    Tree<String> nodeB = treeA.add("B");
    treeA.add("C");
    nodeB.add("D");
    Tree<String> nodeE = nodeB.add("E");
    nodeB.add("F");
    Tree<String> nodeG = nodeE.add("G");
    nodeG.add("H");
    String[] bfsArray = {"A", "B", "C", "D", "E", "F", "G", "H"};
    Iterator<String> bfsIterator = treeA.iterator(Type.BFS);
    for (String element : bfsArray) {
      assertTrue(bfsIterator.hasNext());
      assertEquals(bfsIterator.next(), element);
    }
  }

  @Test
  void concurrentModificationException() {
    Tree<String> treeA = new Tree<>("A");
    Tree<String> nodeB = treeA.add("B");
    treeA.add("C");
    nodeB.add("D");
    Tree<String> nodeE = nodeB.add("E");
    nodeB.add("F");
    Tree<String> nodeG = nodeE.add("G");
    nodeG.add("H");
    Iterator<String> bfsIterator = treeA.iterator(Type.BFS);
    treeA.add("I");
    assertThrows(ConcurrentModificationException.class, bfsIterator::hasNext);
    assertThrows(ConcurrentModificationException.class, bfsIterator::next);

    Iterator<String> dfsIterator = nodeB.iterator(Type.DFS);
    nodeG.delete();
    assertThrows(ConcurrentModificationException.class, dfsIterator::hasNext);
    assertThrows(ConcurrentModificationException.class, dfsIterator::next);
  }
}