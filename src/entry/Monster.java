package entry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * This class represents the monster in the game. It assigns the monster in the dungeon and exit the
 * game if the monster eats the player. It also stores the monster location in the dungeon.
 */
public class Monster implements MonsterInterface {
  private final int count;
  private List<String> monsterLocation;
  private final Random random;
  private int thiefPosition;
  private int movingMonsterPosition;
  private int movingMonsterHealth;
  private boolean isMovingMonsterAlive;

  /**
   * This method gets the monster health.
   *
   * @return the monster health.
   */
  @Override
  public int getMovingMonsterHealth() {
    return movingMonsterHealth;
  }


  /**
   * Constructor for the monster class. It takes the monster count and the random as input.
   *
   * @param monsterCount Represents the monster count.
   * @param random       Represents the random.
   */
  public Monster(int monsterCount, Random random) {
    if (monsterCount <= 0) {
      throw new IllegalArgumentException("Monster count cannot be less than or equal to zero");
    }
    if (random == null) {
      throw new IllegalArgumentException("Random cannot be null");
    }
    this.count = monsterCount;
    this.monsterLocation = new ArrayList<>();
    this.random = random;
    this.thiefPosition = 0;
    this.movingMonsterPosition = 0;
    this.movingMonsterHealth = 100;
    this.isMovingMonsterAlive = true;
  }

  /**
   * This method gets the thief position.
   *
   * @return the thief position.
   */
  @Override
  public int getThiefPosition() {
    return thiefPosition;
  }

  @Override
  public boolean isMovingMonsterAlive() {
    return isMovingMonsterAlive;
  }


  /**
   * This method gets the moving monster position.
   *
   * @return the moving monster position.
   */
  @Override
  public int getMovingMonsterPosition() {
    return movingMonsterPosition;
  }

  /**
   * This method get the monster count.
   *
   * @return the monster count.
   */
  @Override
  public int getCount() {
    return count;
  }

  /**
   * This method calculate the theif next position and return it back to the controller.
   *
   * @param startPoint  Represents the start point.
   * @param maxLocation Represents the max location of the dungeon.
   * @return thief next position.
   */
  @Override
  public int findThiefPosition(int maxLocation, int startPoint) {
    int pos = startPoint;
    while (pos == startPoint) {
      thiefPosition = RandomNumberGenerator.getRandomThiefPosition(maxLocation, random);
      pos = thiefPosition;
    }
    return thiefPosition;
  }

  /**
   * This method sets the monster location in the dungeon.
   * @param maxLocation Represents the max location of the dungeon.
   * @param startPoint Represents the start point of the dungeon.
   * @param endPoint   Represents the end point of the dungeon.
   * @return monster location.
   */
  @Override
  public int findMovingMonster(int maxLocation, int startPoint, int endPoint) {
    int pos = startPoint;
    while (pos == startPoint || pos == endPoint || monsterLocation.get(pos - 1).equals("MONSTER")
            || monsterLocation.get(pos - 1).equals("MONSTER DAMAGED")) {

      movingMonsterPosition = RandomNumberGenerator.getRandomThiefPosition(maxLocation, random);
      pos = movingMonsterPosition;
    }
    return movingMonsterPosition;
  }

  /**
   * This method checks if the monster health is decreased or not.
   */
  @Override
  public void updateMonsterHealth() {
    movingMonsterHealth -= 20;
    if (movingMonsterHealth <= 0) {
      movingMonsterHealth = 0;
    }
  }

  /**
   * This method checks if the monster is alive or not.
   * @param b Represents the boolean value.
   */
  @Override
  public void setMovingMonsterAlive(boolean b) {
    isMovingMonsterAlive = b;
  }

  /**
   * This method sets the monster location in the dungeon.
   *
   * @param i Represents the position.
   */
  @Override
  public void setMovingMonsterPosition(int i) {
    movingMonsterPosition = i;
  }

  /**
   * This method is used to set the thief position for testing purpose.
   *
   * @param i Represents the position.
   */
  @Override
  public void setThiefPosition(int i) {
    thiefPosition = i;
  }

  /**
   * This method assigns the monster in the dungeon only on cave position and check all invalid
   * inputs.
   *
   * @param endPoint    Represents the end point of the dungeon.
   * @param graph       Represents the graph of the dungeon.
   * @param maxLocation Represents the max location of the dungeon.
   * @param startPoint  Represents the start point of the dungeon.
   */
  @Override
  public void assignMonster(int endPoint, Node[][] graph, int maxLocation, int startPoint) {
    if (endPoint <= 0 || endPoint > maxLocation) {
      throw new IllegalArgumentException(
              "End point cannot be less than or equal to zero or " + "greater than max location");
    }
    if (graph == null) {
      throw new IllegalArgumentException("Graph cannot be null");
    }
    if (startPoint <= 0 || startPoint > maxLocation || startPoint == endPoint) {
      throw new IllegalArgumentException(
              "Start point cannot be less than or equal to zero or "
                      + "greater than max location or equal to end point");
    }
    if (maxLocation <= 0) {
      throw new IllegalArgumentException("Max location cannot be less than or equal to zero");
    }
    int caveCount = 0;
    for (Node[] nod : graph) {
      for (Node node : nod) {
        if (Objects.equals(node.getType(), "C")) {
          caveCount++;
        }
      }
    }
    if (count > caveCount) {
      throw new IllegalArgumentException("Monster count cannot be greater than cave count");
    }
    monsterLocation.clear();
    int finalVal = count;

    for (int i = 0; i < maxLocation; i++) {
      monsterLocation.add("NULL");
    }

    this.monsterLocation.set(endPoint - 1, "MONSTER");
    finalVal -= 1;
    while (finalVal != 0) {

      for (Node[] nodes : graph) {
        for (Node node : nodes) {
          if (Objects.equals(node.getType(), "C")) {
            if (finalVal != 0 && node.getVal() != startPoint && node.getVal() != endPoint) {
              this.monsterLocation.set(node.getVal() - 1, "MONSTER");
              finalVal -= 1;
            } else if (node.getVal() != endPoint) {
              this.monsterLocation.set(node.getVal() - 1, "NO MONSTER");
            }
          } else {
            this.monsterLocation.set(node.getVal() - 1, "Tunnel");
          }
        }
      }
    }
  }

  /**
   * This method check if the monster is present at the current location.
   *
   * @param currentLocation Represents the current location.
   * @param maxLocation     Represents the max location of the dungeon.
   * @param dungeon         Represents the dungeon representation.
   * @return true if the monster is present at the current location.
   */
  @Override
  public String checkMonster(int currentLocation, int maxLocation, DungeonInterface dungeon) {
    if (currentLocation <= 0 || currentLocation > maxLocation) {
      throw new IllegalArgumentException(
              "Current location cannot be less "
                      + "than or equal to zero or greater than max location");
    }
    if (dungeon == null) {
      throw new IllegalArgumentException("Dungeon cannot be null");
    }
    if (maxLocation <= 9) {
      throw new IllegalArgumentException("No shortest distance possible in this dungeon");
    }
    String output = "";
    List<List<Integer>> adjListMonster = dungeon.getAdjList();
    List<Integer> allPath = new ArrayList<>();
    List<Integer> distanceTwo = new ArrayList<>();
    List<Integer> distanceOne = new ArrayList<>();
    int v = maxLocation + 1;
    for (int i = 1; i < maxLocation; i++) {
      int path = Dungeon.printShortestDistance(adjListMonster, currentLocation, i, v);
      if (path == 2 && Objects.equals(monsterLocation.get(i - 1), "MONSTER")
              || Objects.equals(monsterLocation.get(i - 1), "MONSTER DAMAGED")) {
        distanceTwo.add(i);
      }
      if (path == 1 && Objects.equals(monsterLocation.get(i - 1), "MONSTER")
              || Objects.equals(monsterLocation.get(i - 1), "MONSTER DAMAGED")) {
        distanceOne.add(i);
      }
    }
    if (distanceOne.size() > 0) {
      output = "More pungent smell of a monster nearby!!";
    } else if (distanceTwo.size() == 1) {
      output = "Less pungent smell of a monster nearby!!";
    } else if (distanceTwo.size() >= 2) {
      output = "More pungent smell of multiple monster nearby!!";
    } else {
      output = "No monster near your location!!";
    }
    return output;
  }

  /**
   * This method get the list of monsters in the dungeon.
   *
   * @return the list of monsters in the dungeon.
   */
  @Override
  public List<String> getMonsterLocation() {
    return new ArrayList<>(monsterLocation);
  }

  /**
   * This method check if the monster exist at the current location.
   *
   * @param currentLocation Represents the current location.
   * @return true if the monster exist at the current location.
   */
  @Override
  public boolean checkMonsterExist(int currentLocation) {
    if (currentLocation <= 0) {
      throw new IllegalArgumentException("Invalid current location passed");
    }
    return Objects.equals(monsterLocation.get(currentLocation - 1), "MONSTER");
  }

  /**
   * This method check if the monster is damaged exist at the current location.
   *
   * @param currentLocation Represents the current location.
   * @return true if the monster is damaged exist at the current location.
   */
  @Override
  public boolean checkMonsterDamaged(int currentLocation) {
    if (currentLocation <= 0) {
      throw new IllegalArgumentException("Invalid current location passed");
    }
    if (Objects.equals(monsterLocation.get(currentLocation - 1), "MONSTER DAMAGED")) {
      int halfProb = RandomNumberGenerator.getPlayerProbability(random);
      //      System.out.println("Half probability of monster being damaged is: " + halfProb);
      // player is dead
      return halfProb == 1; // player is safe
    }
    return false;
  }

  /**
   * This method check if the monster is no monster or tunnel exist at the current location.
   *
   * @param currentLocation Represents the current location.
   * @return true if the monster is no monster or tunnel exist at the current location.
   */
  @Override
  public boolean checkNoMonster(int currentLocation) {
    if (currentLocation <= 0) {
      throw new IllegalArgumentException("Invalid current location passed");
    }
    return Objects.equals(monsterLocation.get(currentLocation - 1), "NO MONSTER")
            || Objects.equals(monsterLocation.get(currentLocation - 1), "Tunnel");
  }

  /**
   * This method check if the monster is present at the current location if yes than set it as
   * damaged monster.
   *
   * @param arrowLocation Represents the current location.
   */
  @Override
  public void damageMonster(int arrowLocation) {
    if (arrowLocation <= 0) {
      throw new IllegalArgumentException("Invalid current location passed");
    }
    if (Objects.equals(monsterLocation.get(arrowLocation - 1), "MONSTER")) {
      monsterLocation.set(arrowLocation - 1, "MONSTER DAMAGED");
    } else {
      monsterLocation.set(arrowLocation - 1, "NO MONSTER");
    }
  }

  /**
   * This method prints the monster count.
   *
   * @return the monster count.
   */
  @Override
  public String toString() {
    return "Monster Count:" + count;
  }

  /**
   * This method check if the monster is dead at the current location.
   *
   * @param arrowLocation Represents the current location.
   * @return true if the monster is dead at the current location.
   */
  @Override
  public boolean checkMonsterDead(int arrowLocation) {
    if (arrowLocation <= 0) {
      throw new IllegalArgumentException("Invalid current location passed");
    }
    return !Objects.equals(monsterLocation.get(arrowLocation - 1), "MONSTER DAMAGED");
  }
}
