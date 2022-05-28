package controller;

import java.io.IOException;

/**
 * This is the interface of the controller class GameController which has multiple method to allow
 * player to play the game. It has methods such as hit, shoot, pick and move which helps player to
 * move through the dungeon and find with the monster.
 */
public interface GameInterface {

  /**
   * Execute a single game of Dungeon where player move in the dungeon and try to escape from the
   * monster. When the game is over, the playGame method ends.
   *
   * @throws IOException if the model gets invalid value.
   */
  void playGame()
          throws IOException;

  /**
   * This method is used to move the player inside the dungeon. And, check for smell, moving monster
   * and thief.
   *
   * @param move is the direction where the player wants to move.
   * @throws IOException if the file is not found.
   */
  void move(String move) throws IOException;

  /**
   * This method is used to pick up the treasure present the player current location.
   */
  void pickup();

  /**
   * This method reset the game and start a new game with new configuration provided by the player.
   *
   * @param numRows       number of rows in the dungeon.
   * @param numCols       number of columns in the dungeon.
   * @param isWrap        Around true if the dungeon is wrap around, false otherwise.
   * @param connectivity  Type connectivity type of the dungeon.
   * @param treasurePerct treasure percentage of the dungeon.
   * @param monsterCount  number of monsters in the dungeon.
   * @throws IOException if the file is not found.
   */
  void resetGame(String numRows, String numCols, String isWrap, String connectivity,
                 String treasurePerct, String monsterCount) throws IOException;

  /**
   * This method is used to shoot the monster whenever player wants to shoot in any direction.
   *
   * @param up Direction the direction in which player wants to shoot.
   * @param i  Distance the distance in which player wants to shoot.
   */
  void shoot(String up, int i);

  /**
   * This method is used to restart the game with the same values of the previous game.
   *
   * @throws IOException if the file is not found.
   */
  void restartGame() throws IOException;

  /**
   * This method is used to hit the moving monster found in the dungeon. It also checks if the
   * player is alive or not after a fight with a moving monster.
   */
  void hitMonster();

  /**
   * This method is used to move the player using mouse clicks to the neighbouring cells.
   *
   * @param finalI the index of the cell that the player is moving to.
   * @throws IOException if the file is not found.
   */
  void movePlayer(int finalI) throws IOException;

  /**
   * This method depicts whether the game is over or not.
   * @return true if the game is over, false otherwise.
   */
  boolean isGameOver();
}
