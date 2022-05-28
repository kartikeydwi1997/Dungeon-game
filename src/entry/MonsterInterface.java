package entry;

import java.util.List;

/**
 * This MonsterInterFace is used to create a monster and perform all operations on the monster as
 * well update the monster's status on each node.
 */
public interface MonsterInterface {

  /**
   * This method assigns the monster in the dungeon only on cave position and check all invalid
   * inputs.
   *
   * @param endPoint    Represents the end point of the dungeon.
   * @param graph       Represents the graph of the dungeon.
   * @param maxLocation Represents the max location of the dungeon.
   * @param startPoint  Represents the start point of the dungeon.
   */
  void assignMonster(int endPoint, Node[][] graph, int maxLocation, int startPoint);

  /**
   * This method check if the monster is present at the current location.
   *
   * @param currentLocation Represents the current location.
   * @param maxLocation     Represents the max location of the dungeon.
   * @param dungeon         Represents the dungeon representation.
   * @return true if the monster is present at the current location.
   */
  String checkMonster(int currentLocation, int maxLocation, DungeonInterface dungeon);

  /**
   * This method get the list of monsters in the dungeon.
   *
   * @return the list of monsters in the dungeon.
   */
  List<String> getMonsterLocation();

  /**
   * This method check if the monster exist at the current location.
   *
   * @param currentLocation Represents the current location.
   * @return true if the monster exist at the current location.
   */
  boolean checkMonsterExist(int currentLocation);

  /**
   * This method check if the monster is damaged exist at the current location.
   *
   * @param currentLocation Represents the current location.
   * @return true if the monster is damaged exist at the current location.
   */
  boolean checkMonsterDamaged(int currentLocation);

  /**
   * This method check if the monster is no monster or tunnel exist at the current location.
   *
   * @param currentLocation Represents the current location.
   * @return true if the monster is no monster or tunnel exist at the current location.
   */
  boolean checkNoMonster(int currentLocation);

  /**
   * This method check if the monster is present at the current location if yes than set it as
   * damaged monster.
   *
   * @param arrowLocation Represents the current location.
   */
  void damageMonster(int arrowLocation);

  /**
   * This method check if the monster is dead at the current location.
   *
   * @param arrowLocation Represents the current location.
   * @return true if the monster is dead at the current location.
   */
  boolean checkMonsterDead(int arrowLocation);

  /**
   * This method finds the monster health.
   *
   * @return monster health.
   */
  int getMovingMonsterHealth();

  /**
   * This method finds thief position in the dungeon.
   *
   * @return thief position.
   */
  int getThiefPosition();

  /**
   * This method find if the monster is alive or not.
   *
   * @return true if the monster is alive.
   */
  boolean isMovingMonsterAlive();

  /**
   * This method assign a monster to a position and keeps updating that position when
   * player moves to another position.
   *
   * @return the location of the monster.
   */
  int getMovingMonsterPosition();

  /**
   * This method finds the monster count in the dungeon.
   *
   * @return monster count.
   */
  int getCount();

  /**
   * This method calculate the next position of the monster in the dungeon.
   *
   * @param startPoint  Represents the start point of the dungeon.
   * @param maxLocation Represents the max location of the dungeon.
   * @return next position of the monster.
   */
  int findThiefPosition(int maxLocation, int startPoint);

  /**
   * This method assign a moving monster to the dungeon.
   *
   * @param maxLocation Represents the max location of the dungeon.
   * @param startPoint Represents the start point of the dungeon.
   * @param endPoint   Represents the end point of the dungeon.
   * @return the location of the monster.
   */
  int findMovingMonster(int maxLocation, int startPoint, int endPoint);

  /**
   * This method update the monster health when player hit the monster.
   */
  void updateMonsterHealth();

  /**
   * This method finds if the moving monster is alive or not.
   *
   * @param b Represents the boolean value.
   */
  void setMovingMonsterAlive(boolean b);

  /**
   * This method set the position of moving monster for testing purpose.
   *
   * @param i Represents the position.
   */
  void setMovingMonsterPosition(int i);

  /**
   * This method set the thief position for testing position.
   *
   * @param i Represents the position.
   */
  void setThiefPosition(int i);
}
