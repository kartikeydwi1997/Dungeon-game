package viewnew;


import controller.GameInterface;

import java.io.IOException;
import java.util.List;


/**
 * This class represents the view interface for the dungeon. It has all the methods required
 * to display the dungeon with appropriate icons and messages.
 */
public interface DungeonView {

  /**
   * This method is used to display the dungeon on the screen.
   */
  void makeVisible();

  /**
   * This method is used to set the click listener on the controller
   * and listen for any click events.
   *
   * @param controller Represents the controller interface.
   */
  void addClickListener(GameInterface controller);

  /**
   * This method is used to refresh the view after each event called.
   */
  void refresh();

  /**
   * This method is used to plot caves and tunnel images on the screen.
   *
   * @param imageName Represents the image name.
   * @param rows      Represents the number of rows.
   * @param cols      Represents the number of columns.
   * @throws IOException when the model gets any invalid input.
   */
  void plotImage(List<String> imageName, int rows, int cols) throws IOException;

  /**
   * This method listen to any key press event.
   *
   * @param controller Represents the controller interface.
   */
  void addKeyListener(GameInterface controller);

  /**
   * This method is used to plot the player on the screen.
   *
   * @param startPoint Represents the starting point of the player.
   * @param rows       Represents the number of rows.
   * @param cols       Represents the number of columns.
   * @throws IOException when the model gets any invalid input.
   */
  void plotPlayer(int startPoint, int rows, int cols) throws IOException;

  /**
   * This method is used to display game information on the screen.
   *
   * @param s string to be displayed.
   */
  void displayText(String s);

  /**
   * This method is used to plot the monster on the screen based on the monster location provided.
   *
   * @param monsterLocation Represents the monster location.
   * @param rows            Represents the number of rows.
   * @param cols            Represents the number of columns.
   * @throws IOException when the model gets any invalid input.
   */
  void plotMonster(List<String> monsterLocation, int rows, int cols) throws IOException;

  /**
   * This method is used to plot the treasure on the screen based on the treasure location provided.
   *
   * @param treasureList Represents the treasure location.
   * @param rows         Represents the number of rows.
   * @param cols         Represents the number of columns.
   */
  void plotTreasure(List<String> treasureList, int rows, int cols);

  /**
   * This method is used to plot the arrow on the screen based on the arrow location provided.
   *
   * @param weaponList Represents the arrow location.
   * @param rows       Represents the number of rows.
   * @param cols       Represents the number of columns.
   */
  void plotArrows(List<List<String>> weaponList, int rows, int cols);

  /**
   * This method update the player position after each move.
   *
   * @param currentPoint Represents the current position of the player.
   */
  void showPlayerLocation(int currentPoint);

  /**
   * This method is used to update the player arrow count on the screen.
   *
   * @param numArrows Represents the number of arrows.
   */
  void showPlayerArrows(int numArrows);

  /**
   * This method is used to update the player treasure count on the screen.
   *
   * @param playerTreasure Represents the number of treasures.
   */
  void showPlayerTreasure(List<String> playerTreasure);

  /**
   * This method is used to remove the treasure from the screen when the player
   * collects that treasure.
   *
   * @param currentLocation Represents the current location of the player.
   * @param rows            Represents the number of rows.
   * @param cols            Represents the number of columns.
   */
  void removeTreasure(int currentLocation, int rows, int cols);

  /**
   * This method is used to remove arrow from any position on the screen when the player
   * collects that arrow.
   *
   * @param currentLocation Represents the current location of the player.
   * @param rows            Represents the number of rows.
   * @param cols            Represents the number of columns.
   */
  void removeArrow(int currentLocation, int rows, int cols);

  /**
   * This method is used to show the less pungent smell of the monster when the player is two
   * distance away from the monster.
   *
   * @param currentPoint Represents the current location of the player.
   * @param rows         Represents the number of rows.
   * @param cols         Represents the number of columns.
   */
  void showLessPungentSmell(int currentPoint, int rows, int cols);

  /**
   * This method is used to show the more pungent smell of the monster when the player is one
   * distance away from the monster.
   *
   * @param currentPoint Represents the current location of the player.
   * @param rows         Represents the number of rows.
   * @param cols         Represents the number of columns.
   */
  void showMorePungentSmell(int currentPoint, int rows, int cols);

  /**
   * This method is used to remove the less pungent smell of the monster from the previous position
   * where the player was.
   *
   * @param previousLocation Represents the previous location of the player.
   * @param rows             Represents the number of rows.
   * @param cols             Represents the number of columns.
   */
  void removeLessPungentSmell(int previousLocation, int rows, int cols);

  /**
   * This method is used to remove the more pungent smell of the monster from the previous position
   * where the player was.
   *
   * @param previousLocation Represents the previous location of the player.
   * @param rows             Represents the number of rows.
   * @param cols             Represents the number of columns.
   */
  void removeMorePungentSmell(int previousLocation, int rows, int cols);

  /**
   * This method is used to add an arrow when player shot an arrow in any cave.
   *
   * @param arrowLocation Represents the location of the arrow.
   * @param rows          Represents the number of rows.
   * @param cols          Represents the number of columns.
   */
  void addArrow(int arrowLocation, int rows, int cols);

  /**
   * This method is used to remove the monster from the screen when the player has killed that
   * monster.
   *
   * @param arrowLocation Represents the location of the arrow.
   * @param rows          Represents the number of rows.
   * @param cols          Represents the number of columns.
   */
  void removeMonster(int arrowLocation, int rows, int cols);

  /**
   * This method is used to remove all the components from the screen and re- render it.
   */
  void remove();

  /**
   * This method is used to restart a new game with the same values provided earlier.
   *
   * @param b Represents the boolean value as true or false.
   */
  void gameRestart(boolean b);

  /**
   * This method is used to plot the thief on the screen and keep updating the thief location.
   *
   * @param thiefPosition Represents the location of the thief.
   * @param rows          Represents the number of rows.
   * @param cols          Represents the number of columns.
   */
  void plotThief(int thiefPosition, int rows, int cols);

  /**
   * This method is used to plot the monster on the screen and keep updating the monster location.
   *
   * @param movingMonsterPosition Represents the location of the monster.
   * @param rows                  Represents the number of rows.
   * @param cols                  Represents the number of columns.
   */
  void plotMovingMonster(int movingMonsterPosition, int rows, int cols);

  /**
   * This method is used to get the monster health all the time monster being hit by the player.
   *
   * @param movingMonsterHealth Represents the health of the monster.
   */
  void showMonsterHealth(int movingMonsterHealth);

  /**
   * This method is used to get the player health on the screen and keep updating the player
   * health.
   *
   * @param playerHealth Represents the health of the player.
   */
  void showPlayerHealth(int playerHealth);

  /**
   * This method is used to update the monster position by removing the monster from its previous
   * position and adding it to the new position.
   *
   * @param movingMonsterPosition Represents the location of the monster.
   * @param rows                  Represents the number of rows.
   * @param cols                  Represents the number of columns.
   */
  void removeMovingMonster(int movingMonsterPosition, int rows, int cols);

  /**
   * This method adds a pit in the dungeon where the player might fall and player health reduces
   * to half.
   *
   * @param pitPosition Represents the location of the pit.
   * @param rows        Represents the number of rows.
   * @param cols        Represents the number of columns.
   */
  void plotPit(int pitPosition, int rows, int cols);

  /**
   * This method is used to indicate the player about the pit location and aware the player
   * that the pit is nearby.
   *
   * @param currentPoint Represents the location of the player.
   * @param rows         Represents the number of rows.
   * @param cols         Represents the number of columns.
   */
  void plotPitIndicator(int currentPoint, int rows, int cols);

  /**
   * This method plot the black screen at the start of the game to hide the game and create more
   * suspense.
   *
   * @param startPoint Represents the location of the player.
   * @param rows       Represents the number of rows.
   * @param cols       Represents the number of columns.
   */
  void plotBlackImage(int startPoint, int rows, int cols);

  /**
   * This method is used to remove the image from the screen when the player travel through that
   * cell.
   *
   * @param currentPoint Represents the location of the player.
   * @param rows         Represents the number of rows.
   * @param cols         Represents the number of columns.
   */
  void removeBlackImage(int currentPoint, int rows, int cols);
}
