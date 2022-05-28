package entry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * This class takes a treasure percentage, treasure list and max locations and creates the treasure
 * for the dungeon.
 */
public class Treasure implements TreasureInterface {
  private final double treasurePerct;
  public List<String> treasureList;
  private final int maxLocations;

  /**
   * Constructor to assign the maxlocations and treasure percentage to variables.
   *
   * @param maxLocations Represents the max locations in the dungeon.
   * @param treasurePerct Represents the treasure percentage assigned to the dungeon.
   */
  public Treasure(int maxLocations, double treasurePerct) {
    performCheck(maxLocations, treasurePerct);
    this.treasurePerct = treasurePerct;
    this.maxLocations = maxLocations;
    this.treasureList = new ArrayList<String>();
  }

  private void performCheck(int maxLocations, double treasurePerct) {
    if (maxLocations < 0 ) {
      throw new IllegalArgumentException("Illegal value passed for maxLocations");
    }
    if (treasurePerct < 0 || treasurePerct > 100) {
      throw new IllegalArgumentException("Illegal value passed for treasure percentage");
    }
  }

  /**
   * Getter to get the treasure percentage of the treasure.
   *
   * @return the treasure percentage value.
   */
  @Override
  public double getTreasurePerct() {
    return treasurePerct;
  }

  /**
   * Getter to get the max number of locations in the dungeon.
   *
   * @return the max location value.
   */
  @Override
  public int getMaxLocations() {
    return maxLocations;
  }

  /**
   * Getter to get the treasure list of the dungeon.
   *
   * @return the treasure list of the dungeon.
   */
  @Override
  public List<String> getTreasureList() {
    return new ArrayList<>(treasureList);
  }

  /**
   * This method check if the treasure is present at that index.
   *
   * @param position Represents the current position of the player.
   * @return true if the treasure is present at that index.
   */
  @Override
  public boolean getTreasureAtPosition(int position) {
    if (position < 0) {
      throw new IllegalArgumentException("Invalid position provided from the driver class");
    }
    return treasureList.get(position).equals("DIAMOND")
        || treasureList.get(position).equals("RUBIES")
        || treasureList.get(position).equals("SAPPHIRE");
  }

  /**
   * This method prints the treasue percentage provided by the driver class.
   *
   * @return the treasure percentage in string representation.
   */
  @Override
  public String toString() {
    return "Treasure Percentage=" + treasurePerct;
  }

  /**
   * This method finds the treasure present at that index and also removes that treasure once
   * collected.
   *
   * @param pos Represents the current position of the player.
   * @return the treasure present at that location.
   */
  @Override
  public String treasureAtIndex(int pos) {
    if (pos < 0) {
      throw new IllegalArgumentException("Invalid position provided from the driver class");
    }
    StringBuilder t = new StringBuilder(treasureList.get(pos));
    if (t.equals("T")) {
      return new String("");
    }
    if (!Objects.equals(treasureList.get(pos), "T")) {
      treasureList.set(pos, "NT");
    }
    return t.toString();
  }

  /**
   * This method assign the random treasure to each position of the graph after checking is that
   * position is not a vertex.
   *
   * @param graph Represents the graph passed to the method.
   * @param random Represents the random object.
   * @return the list of string as the treasure.
   */
  @Override
  public List<String> assignTreasure(Node[][] graph, Random random) {
    if (graph == null) {
      throw new IllegalArgumentException("Graph cannot be null");
    }
    if (random == null) {
      throw new IllegalArgumentException("Random object cannot be null!");
    }
    double finalVal = (float) treasurePerct * maxLocations;
    double treasureFill = 1;
    finalVal = finalVal / 100;
    finalVal = Math.floor(finalVal);
    for (Node[] nodes : graph) {
      for (Node node : nodes) {
        if (Objects.equals(node.getType(), "C")) {
          if (treasureFill <= finalVal) {
            int randomTreasure = RandomNumberGenerator.getRandomTreasure(random);
            String t = TreasureType.DIAMONDS.getConstantVal();
            if (randomTreasure == 1) {
              treasureList.add(TreasureType.DIAMONDS.getConstantVal());
              treasureFill++;
            } else if (randomTreasure == 2) {
              treasureList.add(TreasureType.RUBIES.getConstantVal());
              treasureFill++;
            } else if (randomTreasure == 3) {
              treasureList.add(TreasureType.SAPPHIRES.getConstantVal());
              treasureFill++;
            }
          } else {
            treasureList.add("NT");
          }
        } else {
          treasureList.add("T");
        }
      }
    }

    return new ArrayList<>(treasureList);
  }
}
