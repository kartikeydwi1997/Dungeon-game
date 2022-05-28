package entry;

import java.util.List;
import java.util.Random;

/**
 * The dungeon interface to implement all method supported by dungeon.
 */
public interface DungeonInterface {
  /**
   * This method find the possible path from one node to another based on the graph provided as
   * input.
   *
   * @param graph Represents the dungeon representation with T and C.
   */
  void movePlayer(Node[][] graph);

  /**
   * This method change the current loation of user and update the current position with the new
   * value.
   *
   * @param node Represents the current node where the player is.
   * @return the new current position of the player.
   */
  int changeCurrentLocation(Node node);

  /**
   * This method validate the user input and check if it lies in expected position.
   *
   * @param yourInput Represents the user input provided from the keyboard.
   * @param allPath   Represents the all possible path where the user can go
   *                  from the current vertex.
   * @return the current point or throws error in case it's not a valid input.
   */
  int validateUserInput(int yourInput, List<Node> allPath);

  /**
   * This method is used to find the random end point where player is going to.
   *
   * @param allVertexPosition Represents the all vertex position where player can go.
   * @param random            Represents the random number generator.
   * @param startPoint        Represents the start point where player is.
   * @param resultGraph       Represents the graph where player is going to.
   * @param cols              Represents the number of columns in the graph.
   * @param rows              Represents the number of rows in the graph.
   * @return the random end point where player is going to.
   */
  int randomEndPoint(
          List<Node> allVertexPosition,
          Random random,
          int startPoint,
          Node[][] resultGraph,
          int cols,
          int rows);

  /**
   * This method finds the number of rows in a graph in string format.
   *
   * @return the number of rows in a graph in string format.
   */
  String getRowsInString();

  /**
   * This method is used to get the number of cols of the graph in the string format.
   *
   * @return the number of cols of the graph in the string format.
   */
  String getColsInString();

  /**
   * This method is used to get if the dungeon is wrapped or not.
   *
   * @return true if the dungeon is wrapped else false.
   */
  String getIsWrap();

  /**
   * This method is used to get the start point of the dungeon in string format.
   *
   * @return the start point of the dungeon in string format.
   */
  String getStartPointInString();

  /**
   * This method is used to get the end point of the dungeon in string format.
   *
   * @return the end point of the dungeon in string format.
   */
  String getEndPointInString();

  /**
   * This method is used to get the connectivity of the graph.
   *
   * @return the connectivity of the graph.
   */
  String getConnectivity();

  /**
   * This method is used to get the current location of the player.
   *
   * @return the current location of the player.
   */
  int getCurrentPoint();

  /**
   * This method is used to get the graph in string format.
   *
   * @return the graph in string format.
   */
  Node[][] getGraph();

  /**
   * This method is used to get the end point of the dungeon.
   *
   * @return the end point of the dungeon.
   */
  int getEndPoint();

  /**
   * This method is used to get the start location of the player.
   *
   * @return the start location of the player.
   */
  int getStartPoint();

  /**
   * This method is used to get the number of rows in the graph.
   *
   * @return the number of rows in the graph.
   */
  int getRows();

  /**
   * This method is used to get the number of cols in the graph.
   *
   * @return the number of cols in the graph.
   */
  int getCols();

  /**
   * This method is used to get all the possible path of the player.
   *
   * @return all the possible path of the player.
   */
  List<Node> getPossiblePath();

  /**
   * This method is used to get all the direction from one point to another point.
   *
   * @return all the direction from one point to another point.
   */
  List<String> getAllDirections();

  /**
   * This method is used to check if the user entered the correct input.
   *
   * @param direction     Represents the direction where the player is going to.
   * @param allDirections Represents the all directions where the player can go.
   * @return true if the user entered the correct input else false.
   */
  boolean validDirection(String direction, List<String> allDirections);

  /**
   * This method is used to check if the user entered the valid direction.
   *
   * @param distance    Represents the distance where the player is going to.
   * @param maxLocation Represents the max location where the player can go.
   * @return true if the user entered the valid direction else false.
   */
  boolean validDistance(int distance, int maxLocation);

  /**
   * This method is used to calculate the location where the arrow will travel to when shot.
   *
   * @param direction       Represents the direction where the player is going to.
   * @param distance        Represents the distance where the player is going to.
   * @param currentLocation Represents the current location of the player.
   * @param graph           Represents the graph of the dungeon.
   * @param maxLocation     Represents the max location of the dungeon.
   * @return the location where the arrow will travel to when shot.
   */
  int shootArrow(
          String direction, String distance, int currentLocation, Node[][] graph, int maxLocation);

  /**
   * This method is used to check if the player arrow was missed or not.
   *
   * @param arrowLocation Represents the location where the arrow is.
   * @return true if the player arrow was missed else false.
   */
  boolean isArrowMissed(int arrowLocation);

  /**
   * This method is used to get the adj list of the graph.
   *
   * @return the adj list of the graph.
   */
  List<List<Integer>> getAdjList();

  /**
   * This method is used to get the list of all neighbours of the current location.
   *
   * @param graph Represents the graph of the dungeon.
   * @return the list of all neighbours of the current location.
   */
  List<String> getAllNeighbours(Node[][] graph);

  /**
   * This method set the previous position of the player.
   *
   * @param currentPoint Represents the current location of the player.
   */
  void setPreviousPoint(int currentPoint);

  /**
   * This method get the previous position of the player.
   *
   * @return the previous position of the player.
   */
  int getPreviousPoint();

  /**
   * This method add a pit inside the graph.
   *
   * @param maxLocation Represents the max location of the dungeon.
   * @param startPoint Represents the start location of the player.
   * @param endPoint   Represents the end location of the player.
   * @return the graph with the pit.
   */
  int addPitPosition(int maxLocation, int startPoint, int endPoint);

  /**
   * This method gets the pit position lcoated in the dungeon.
   *
   * @return the pit position located in the dungeon.
   */
  int getPitPosition();
}
