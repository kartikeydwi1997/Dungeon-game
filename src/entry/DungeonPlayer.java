package entry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class generates the dungeon player with its random name and random character and stores its
 * current treasure, arrow count and is the player alive or not.
 */
public class DungeonPlayer implements Player {
  private final String randomName;
  private final String randomCharacter;
  private boolean isAlive;
  private int numArrows;
  private List<String> playerTreasure;
  private int playerHealth;

  /**
   * This method is used to get the player treasure.
   *
   * @return the player treasure.
   */
  @Override
  public List<String> getPlayerTreasure() {
    return new ArrayList<>(playerTreasure);
  }

  /**
   * This method is used to add the treasure to the player.
   *
   * @param playerTreasure the treasure to be added.
   */
  @Override
  public void setPlayerTreasure(String playerTreasure) {
    if (playerTreasure == null || playerTreasure.equals("")) {
      throw new IllegalArgumentException("Invalid treasure passed");
    }
    this.playerTreasure.add(playerTreasure);
  }


  /**
   * Constructor to assign the name and character to the variable declared in the scope.
   *
   * @param randomName      Represents the random name of the player.
   * @param randomCharacter Represents the random character of the player.
   */
  public DungeonPlayer(String randomName, String randomCharacter) {
    this.numArrows = 3;
    if (randomName == null) {
      throw new IllegalArgumentException("Invalid random name passed");
    }
    if (randomCharacter == null || randomCharacter.equals("")) {
      throw new IllegalArgumentException("Invalid random character passed");
    }
    this.randomName = randomName;
    this.randomCharacter = randomCharacter;
    this.isAlive = true;
    this.playerTreasure = new ArrayList<>();
    this.playerHealth = 100;
  }

  /**
   * This method is used to get the player health.
   *
   * @return the player health.
   */
  @Override
  public int getPlayerHealth() {
    return playerHealth;
  }


  /**
   * This method is used to get the number of arrows.
   *
   * @return the number of arrows.
   */
  @Override
  public int getNumArrows() {
    return numArrows;
  }

  /**
   * This method clears the player treasure when player will encounter a thief.
   */
  @Override
  public void removePlayerTreasure() {
    playerTreasure.clear();
  }

  /**
   * This method is used to decrease the player health when moving monster will hit the player.
   */
  @Override
  public void updatePlayerHealth(int i) {
    playerHealth -= i;
    if (playerHealth <= 0) {
      playerHealth = 0;
    }
  }

  /**
   * Getter to get the random name of the player.
   *
   * @return the random name of the player.
   */
  @Override
  public String getRandomName() {
    return randomName;
  }

  /**
   * Getter to get the random character of the player.
   *
   * @return the random character of the player.
   */
  @Override
  public String getRandomCharacter() {
    return randomCharacter;
  }

  /**
   * This method checks the equality of two player.
   *
   * @param o current object o.
   * @return boolean value representing the equality of the object.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DungeonPlayer that = (DungeonPlayer) o;
    return Objects.equals(randomName, that.randomName)
            && Objects.equals(randomCharacter, that.randomCharacter);
  }

  /**
   * This method check the hashcode of two player object.
   *
   * @return the integer value as 1,0 or -1.
   */
  @Override
  public int hashCode() {
    return Objects.hash(randomName, randomCharacter);
  }

  /**
   * This method represents the player in string format.
   *
   * @return the string representation of the player.
   */
  @Override
  public String toString() {
    return "\nName='" + randomName + '\'' + "\nCharacter='" + randomCharacter + '\'';
  }

  /**
   * This method is used to get the player status.
   *
   * @return the player status.
   */
  @Override
  public boolean isAlive() {
    return isAlive;
  }

  /**
   * This method is used to set the player status.
   *
   * @param b the player status.
   */
  @Override
  public void setPlayerAlive(boolean b) {
    this.isAlive = b;
  }

  /**
   * This method is used to update the arrow count of the user.
   *
   * @param i the number of arrows.
   */
  @Override
  public void updateArrowCount(int i) {
    if (i < -1) {
      throw new IllegalArgumentException("Invalid number of arrows passed");
    }
    this.numArrows = this.numArrows + i;
  }

  /**
   * This method is used to check if the player has arrows left or not.
   *
   * @return true if the player has arrows left.
   */
  @Override
  public boolean hasArrows() {
    return this.numArrows > 0;
  }
}
