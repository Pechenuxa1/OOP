package ru.nsu.fit.vtatarintsev.pizzeria;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * The class for loading pizzeria parameters.
 */
public class Configuration {

  JSONObject parser;

  /**
   * The constructor to create a parser for a specific json file.
   *
   * @param fileName json file name to parse
   */
  public Configuration(String fileName) {
    try {
      parser = (JSONObject) new JSONParser().parse(new FileReader(fileName));
    } catch (IOException | ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public int getNumBakers() {
    return ((Long) parser.get("NumBakers")).intValue();
  }

  /**
   * The method for reading pizza cooking time for each baker.
   *
   * @return List of cooking times for each baker.
   */
  public List<Integer> getCookingTime() {
    List<Integer> cookingTime = new ArrayList<>();
    JSONArray jsonCookingTime = (JSONArray) parser.get("CookingTimeInMs");
    for (Object time : jsonCookingTime) {
      cookingTime.add(((Long) time).intValue());
    }
    return cookingTime;
  }

  public int getNumDeliverymen() {
    return ((Long) parser.get("NumDeliverymen")).intValue();
  }

  /**
   * The method for reading trunk capacity for each deliveryman.
   *
   * @return List of trunk capacities for each deliveryman.
   */
  public List<Integer> getTrunkCapacity() {
    List<Integer> trunkCapacity = new ArrayList<>();
    JSONArray jsonTrunkCapacity = (JSONArray) parser.get("TrunkCapacity");
    for (Object capacity : jsonTrunkCapacity) {
      trunkCapacity.add(((Long) capacity).intValue());
    }
    return trunkCapacity;
  }

  public int getStorageCapacity() {
    return ((Long) parser.get("StorageCapacity")).intValue();
  }
}
