package entry;

import java.util.List;

/**
 * Interface to perform all operations on the player class such as assigning random character and
 * random name.
 */
public interface Player {
  /**
   * Getter to get the random character of the player.
   *
   * @return the random character of the player.
   */
  String getRandomCharacter();

  /**
   * Getter to get the random name of the player.
   *
   * @return the random name of the player.
   */
  String getRandomName();

  /**
   * This method is used to get the player status.
   *
   * @return the player status.
   */
  boolean isAlive();

  /**
   * This method is used to get the player treasure.
   *
   * @return the player treasure.
   */
  List<String> getPlayerTreasure();

  /**
   * This method is used to add the treasure to the player.
   *
   * @param playerTreasure the treasure to be added.
   */
  void setPlayerTreasure(String playerTreasure);

  /**
   * This method is used to set the player status.
   *
   * @param b the player status.
   */
  void setPlayerAlive(boolean b);

  /**
   * This method is used to update the arrow count of the user.
   *
   * @param i the number of arrows.
   */
  void updateArrowCount(int i);

  /**
   * This method is used to check if the player has arrows left or not.
   *
   * @return true if the player has arrows left.
   */
  boolean hasArrows();

  /**
   * This method is used to get the player health.
   * @return the player health.
   */
  int getPlayerHealth();

  /**
   * This method is used to get the number of arrows.
   *
   * @return the number of arrows.
   */
  int getNumArrows();

  /**
   * This method is used to remove the treasure when the thief will encounter the player.
   */
  void removePlayerTreasure();

  /**
   * This method update the player health when the moving monster will hit the player.
   * @param i the damage to be done.
   */
  void updatePlayerHealth(int i);
}
