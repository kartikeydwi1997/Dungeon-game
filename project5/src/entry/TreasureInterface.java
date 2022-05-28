package entry;

import java.util.List;
import java.util.Random;

/**
 * Treasure interface to assign treasure to the dungeon graph and perform other operations
 * such as update treasure list once player collects the treasure.
 */
public interface TreasureInterface {
  /**
   * This method finds the treasure present at that index and also removes that treasure once
   * collected.
   *
   * @param pos Represents the current position of the player.
   * @return the treasure present at that location.
   */
  String treasureAtIndex(int pos);

  /**
   * This method assign the random treasure to each position of the graph after checking
   * is that position is not a vertex.
   *
   * @param graph  Represents the graph passed to the method.
   * @param random Represents the random object.
   * @return the list of string as the treasure.
   */
  List<String> assignTreasure(Node[][] graph, Random random);

  /**
   * Getter to get the treasure list of the dungeon.
   *
   * @return the treasure list of the dungeon.
   */
  List<String> getTreasureList();

  /**
   * Getter to get the max number of locations in the dungeon.
   *
   * @return the max location value.
   */
  int getMaxLocations();

  /**
   * Getter to get the treasure percentage of the treasure.
   *
   * @return the treasure percentage value.
   */
  double getTreasurePerct();

  /**
   * This method check if the treasure is present at that index.
   *
   * @param position Represents the current position of the player.
   * @return true if the treasure is present at that index.
   */
  boolean getTreasureAtPosition(int position);

}
