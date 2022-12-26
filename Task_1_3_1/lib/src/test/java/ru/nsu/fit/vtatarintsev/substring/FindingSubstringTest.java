/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ru.nsu.fit.vtatarintsev.substring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class FindingSubstringTest {

  @Test
  void stringTest() throws IOException {
    String fileName = "./src/test/resources/string.txt";
    String subString = "aabaa";
    int[] beginningArray = {0, 3, 8, 11};
    ArrayList<Integer> beginningList = new ArrayList<>();
    for (int j : beginningArray) {
      beginningList.add(j);
    }
    try (Reader fileReader = new BufferedReader(new FileReader(fileName))) {
      assertEquals(beginningList, Substring.findingSubstring(subString,
          fileReader));
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  void noSubstringTest() throws IOException {
    String fileName = "./src/test/resources/string.txt";
    String subString = "abb";
    ArrayList<Integer> beginningList = new ArrayList<>();
    try (Reader fileReader = new BufferedReader(new FileReader(fileName))) {
      assertEquals(beginningList, Substring.findingSubstring(subString,
          fileReader));
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  void someStringTest() throws IOException {
    String fileName = "./src/test/resources/someString.txt";
    String subString = "ABCDE";
    int[] beginningArray = {16};
    ArrayList<Integer> beginningList = new ArrayList<>();
    for (int j : beginningArray) {
      beginningList.add(j);
    }
    try (Reader fileReader = new BufferedReader(new FileReader(fileName))) {
      assertEquals(beginningList, Substring.findingSubstring(subString,
          fileReader));
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  void utf8Test() throws IOException {
    String fileName = "./src/test/resources/russianText.txt";
    String subString = "е";
    int[] beginningArray = {2, 7, 10, 29};
    ArrayList<Integer> beginningList = new ArrayList<>();
    for (int j : beginningArray) {
      beginningList.add(j);
    }
    try (Reader fileReader =
        new BufferedReader(
            new InputStreamReader(
                new FileInputStream(fileName), StandardCharsets.UTF_8))) {
      assertEquals(beginningList, Substring.findingSubstring(subString,
          fileReader));
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
