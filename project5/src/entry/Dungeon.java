package entry;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * This class creates a Dungeon with all the parameter passed from the driver class. It selects
 * random start, end point, movePlayer, and build the dungeon using the connectivity and Kruskal's
 * algorithm.
 */
public class Dungeon implements DungeonInterface {
  private final int rows;
  private final int cols;
  private final List<Node> vertexPosition;
  private final int startPoint;
  private int endPoint;
  private final String isWrap;
  private List<Node> possiblePath;
  private final Node[][] graph;
  private int currentPoint;
  private static List<List<Integer>> adjList;
  private List<String> allDirections;
  private String connectivity;
  private int previousPosition;
  private Random random;
  private int pitPosition;

  /**
   * This method is used to get the graph of the dungeon.
   *
   * @return the graph of the dungeon.
   */
  public Node[][] getGraph() {
    return graph;
  }


  /**
   * Constructor to create a dungeon with all the parameter passed from the main class and move
   * player to any desired position.
   *
   * @param numRows           Represents the number of rows in the dungeon.
   * @param numCols           Represents the number of cols in the dungeon.
   * @param allVertexPosition Represents the possible path to all vertex in the dungeon.
   * @param startPoint        Represents the start point of the player in the dungeon.
   * @param currentLocation   Represents the current location of the player in the dungeon.
   * @param isWrap            Represents if the dungeon is wrapped or not.
   * @param graph             Represents the graph of the dungeon.
   * @param connectivity      Represents the connectivity of the dungeon.
   */
  public Dungeon(
          int numRows,
          int numCols,
          List<Node> allVertexPosition,
          int startPoint,
          int currentLocation,
          String isWrap,
          Node[][] graph,
          String connectivity) {
    performCheck(
            numRows, numCols, allVertexPosition, startPoint, currentLocation, isWrap, connectivity);
    this.rows = numRows;
    this.cols = numCols;
    this.vertexPosition = allVertexPosition;
    this.startPoint = startPoint;
    this.endPoint = 0;
    this.currentPoint = currentLocation;
    this.isWrap = isWrap;
    this.graph = graph;
    adjList = new ArrayList<>();
    possiblePath = new ArrayList<>();
    this.connectivity = connectivity;
    this.allDirections = new ArrayList<>();
    this.previousPosition = this.startPoint;
    this.pitPosition = 0;
  }


  private void performCheck(
          int numRows,
          int numCols,
          List<Node> allVertexPosition,
          int startPoint,
          int currentLocation,
          String isWrap,
          String connectivity) {
    if (isWrap == null) {
      throw new IllegalArgumentException("Wrap cannot take null or empty values");
    }
    if (numRows <= 3) {
      throw new IllegalArgumentException("Invalid no of rows entered, number of rows too less");
    }
    if (numCols <= 3) {
      throw new IllegalArgumentException("Invalid no of cols entered, number of cols are too less");
    }
    if (allVertexPosition.size() != (numRows * numCols)) {
      throw new IllegalArgumentException("Invalid shortest path");
    }
    if (startPoint <= 0 || startPoint > (numRows * numCols)) {
      throw new IllegalArgumentException("Invalid start point provided");
    }
    if (Integer.parseInt(connectivity) < 0) {
      throw new IllegalArgumentException("Invalid connectivity value");
    }
    if (currentLocation != startPoint) {
      throw new IllegalArgumentException("Current location should be similar to start point");
    }
    if (isWrap.equals("YES") || isWrap.equals("NO")) {
      // Do nothing
    } else {
      throw new IllegalArgumentException("Wrap can only hold YES or NO values!!");
    }
  }

  /**
   * This method finds the random start point for the player in the dungeon.
   *
   * @param random  Represents the random object passed to the dungeon.
   * @param numCols Represents the number of columns of the dungeon.
   * @param numRows Represents the number of rows of the dungeon.
   * @return the integer value that represents the start point.
   */
  public static int randomStartPoint(Random random, int numCols, int numRows) {
    if (random == null) {
      throw new IllegalArgumentException("random object cannot be null");
    }
    if (numRows <= 3) {
      throw new IllegalArgumentException("Invalid no of rows entered, number of rows too less");
    }
    if (numCols <= 3) {
      throw new IllegalArgumentException("Invalid no of cols entered, number of cols are too less");
    }
    int sp;
    int max_locations = numCols * numRows;

    sp = random.nextInt(max_locations + 1) + 1;
    return sp;
  }

  private static void addEdge(ArrayList<List<Integer>> adj, int i, int j) {
    adj.get(i).add(j);
    adj.get(j).add(i);
  }

  /**
   * This method is used to get the adjlist of the dungeon which represents the connection from one
   * node to another.
   *
   * @return the adjlist of the dungeon.
   */
  public List<List<Integer>> getAdjList() {
    return new ArrayList<>(adjList);
  }


  /**
   * This method find the end point after finding the possible path of length greater than 5, using
   * breath first search.
   *
   * @param random            Represents the random object passed to the dungeon.
   * @param numRows           Represents the number of rows in the dungeon.
   * @param numCols           Represents the number of cols in the dungeon.
   * @param resultGraph       Represents the result graph with each vertex marked as T and C.
   * @param startPoint        Represents the start point of the player in the dungeon.
   * @param allVertexPosition Represents the list of all the vertex positions in the dungeon.
   * @return the end point in the dungeon whose length is greater than 5.
   */
  @Override
  public int randomEndPoint(
          List<Node> allVertexPosition,
          Random random,
          int startPoint,
          Node[][] resultGraph,
          int numCols,
          int numRows) {
    if (random == null) {
      throw new IllegalArgumentException("random object cannot be null");
    }
    if (allVertexPosition.size() != (numRows * numCols)) {
      throw new IllegalArgumentException("Invalid shortest path");
    }
    if (resultGraph.length < 0) {
      throw new IllegalArgumentException("Invalid graph passed to the method");
    }
    if (numRows <= 3) {
      throw new IllegalArgumentException("Invalid no of rows entered, number of rows too less");
    }
    if (numCols <= 3) {
      throw new IllegalArgumentException("Invalid no of cols entered, number of cols are too less");
    }
    if (startPoint <= 0 || startPoint > (numRows * numCols)) {
      throw new IllegalArgumentException("Invalid start point provided");
    }
    this.random = random;
    int v = numCols * numRows + 1;
    ArrayList<List<Integer>> adj = new ArrayList<>();
    for (int i = 0; i < v; i++) {
      adj.add(new ArrayList<Integer>());
    }
    for (Node[] nodes : resultGraph) {
      for (Node node : nodes) {
        if (node.getLeft() != null) {
          addEdge(adj, node.getVal(), node.getLeft().getVal());
        }
        if (node.getRight() != null) {
          addEdge(adj, node.getVal(), node.getRight().getVal());
        }
        if (node.getTop() != null) {
          addEdge(adj, node.getVal(), node.getTop().getVal());
        }
        if (node.getBottom() != null) {
          addEdge(adj, node.getVal(), node.getBottom().getVal());
        }
      }
    }

    int startPointIdx;
    int endPointIndx;
    int endPoint = 0;
    Node sp = new Node(startPoint);
    startPointIdx = allVertexPosition.indexOf(sp);
    int path = 0;
    endPointIndx = startPointIdx;
    adjList = new ArrayList<>(adj);
    while (Math.abs(startPointIdx - endPointIndx) < 5 || path < 5) {
      endPointIndx = random.nextInt(allVertexPosition.size());
      endPoint = allVertexPosition.get(endPointIndx).getVal();

      for (Node[] nodes : resultGraph) {
        for (Node node : nodes) {
          if (node.getVal() == endPoint) {
            if (node.getType().equals("C")) {
              path = printShortestDistance(adj, startPoint, endPoint, v);
            } else {
              path = 0;
            }

          }
        }

      }
    }
    this.endPoint = endPoint;
    return endPoint;
  }

  /**
   * This method find the shortest path between two vertex .
   *
   * @param adj  Represents the adjacency list whose shows possible path from one node to another.
   * @param s    Represents the start point.
   * @param dest Represents the end point.
   * @param v    Represents the maximum location available in the graph.
   * @return the integer value representing the shortest path between start and end point.
   */
  public static int printShortestDistance(List<List<Integer>> adj, int s, int dest, int v) {
    if (s <= 0) {
      throw new IllegalArgumentException("Invalid start point");
    }
    if (dest <= 0) {
      throw new IllegalArgumentException("Invalid end point");
    }
    if (v <= 0) {
      throw new IllegalArgumentException("total vertex cannot be less than equal to zero.");
    }

    int[] pred = new int[v];
    int[] dist = new int[v];

    if (!bfs(adj, s, dest, v, pred, dist)) {
      return -1;
    }

    LinkedList<Integer> path = new LinkedList<Integer>();
    int crawl = dest;
    path.add(crawl);
    while (pred[crawl] != -1) {
      path.add(pred[crawl]);
      crawl = pred[crawl];
    }
    return dist[dest];
  }

  private static boolean bfs(
          List<List<Integer>> adj, int src, int dest, int v, int[] pred, int[] dist) {

    boolean[] visited = new boolean[v];

    for (int i = 0; i < v; i++) {
      visited[i] = false;
      dist[i] = Integer.MAX_VALUE;
      pred[i] = -1;
    }

    visited[src] = true;
    dist[src] = 0;
    LinkedList<Integer> queue = new LinkedList<Integer>();
    queue.add(src);
    while (!queue.isEmpty()) {
      int u = queue.remove();
      for (int i = 0; i < adj.get(u).size(); i++) {
        if (!visited[adj.get(u).get(i)]) {
          visited[adj.get(u).get(i)] = true;
          dist[adj.get(u).get(i)] = dist[u] + 1;
          pred[adj.get(u).get(i)] = u;
          queue.add(adj.get(u).get(i));

          if (adj.get(u).get(i) == dest) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Getter the return the possible path.
   *
   * @return the possible path of type node.
   */
  public List<Node> getPossiblePath() {
    return new ArrayList<>(possiblePath);
  }

  /**
   * Getter the return the currentPoint.
   *
   * @return the currentPoint of type integer.
   */
  public int getCurrentPoint() {
    return currentPoint;
  }

  /**
   * Getter to return the number of rows in a dungeon.
   *
   * @return the integer value as num of rows.
   */
  public int getRows() {
    return rows;
  }

  /**
   * This method is used to get the number of rows of a dungeon in string format.
   *
   * @return the string value of the number of rows.
   */
  public String getRowsInString() {
    return rows + "";
  }

  /**
   * Getter to return the number of cols in a dungeon.
   *
   * @return the integer value as num of cols.
   */
  public int getCols() {
    return cols;
  }

  /**
   * Getter to get the number of cols in string format.
   *
   * @return the string value of the number of cols.
   */
  public String getColsInString() {
    return cols + "";
  }

  /**
   * Getter to return the vertex position.
   *
   * @return the node value as vertexPosition.
   */
  public List<Node> getVertexPosition() {
    return new ArrayList<>(vertexPosition);
  }

  /**
   * Getter to return the start point in the dungeon.
   *
   * @return the integer value as start point.
   */
  public int getStartPoint() {
    return startPoint;
  }

  /**
   * Getter to get the string value of the start point.
   *
   * @return the string value of the start point.
   */
  public String getStartPointInString() {
    return startPoint + "";
  }

  /**
   * Getter to return the end point in the dungeon.
   *
   * @return the integer value as end point.
   */
  public int getEndPoint() {
    return endPoint;
  }

  /**
   * Getter to get the string value of the end point.
   *
   * @return the string value of the end point.
   */
  public String getEndPointInString() {
    return endPoint + "";
  }

  /**
   * Getter to return the isWrap value in the dungeon.
   *
   * @return the string value as YES or NO.
   */
  public String getIsWrap() {
    return isWrap;
  }

  /**
   * Getter to get the value of connectivity in the dungeon.
   *
   * @return the connectivity value of the dungeon
   */
  public String getConnectivity() {
    return connectivity;
  }

  /**
   * String method to print the dungeon data on the console.
   *
   * @return the string representation of the dungeon.
   */
  @Override
  public String toString() {
    return "Dungeon{"
            + "rows="
            + rows
            + ", cols="
            + cols
            + ", vertexPosition="
            + vertexPosition
            + ", startPoint="
            + startPoint
            + ", endPoint="
            + endPoint
            + ", currentPoint="
            + currentPoint
            + ", possiblePath="
            + possiblePath
            + '}';
  }

  /**
   * This method find the possible path from one node to another based on the graph provided as
   * input.
   *
   * @param graph Represents the dungeon representation with T and C.
   */
  public void movePlayer(Node[][] graph) {
    if (graph.length < 0 || graph == null) {
      throw new IllegalArgumentException("Invalid graph passed to the method");
    }

    possiblePath.clear();
    allDirections.clear();
    for (Node[] nodes : graph) {
      for (Node node : nodes) {
        if (currentPoint == node.getVal()) {
          if (node.getLeft() != null) {
            possiblePath.add(node.getLeft());
            allDirections.add("W");
          }
          if (node.getTop() != null) {
            possiblePath.add(node.getTop());
            allDirections.add("N");
          }
          if (node.getRight() != null) {
            possiblePath.add(node.getRight());
            allDirections.add("E");
          }

          if (node.getBottom() != null) {
            possiblePath.add(node.getBottom());
            allDirections.add("S");
          }
        }
      }
    }

  }

  /**
   * This method change the current location of user and update the current position with the new
   * value.
   *
   * @param node Represents the current node where the player is.
   * @return the new current position of the player.
   */
  public int changeCurrentLocation(Node node) {
    if (node == null) {
      throw new IllegalArgumentException("Invalid node passed to the method");
    }
    currentPoint = node.getVal();
    return currentPoint;
  }

  /**
   * This method validate the user input and check if it lies in expected position.
   *
   * @param yourInput Represents the user input provided from the keyboard.
   * @param allPath   Represents the all possible path where the user
   *                  can go from the current vertex.
   * @return the current point or throws error in case it's not a valid input.
   */
  public int validateUserInput(int yourInput, List<Node> allPath) {
    if (allPath.size() <= 0 || allPath == null) {
      throw new IllegalArgumentException("Invalid possible path passed from user to this method");
    }
    if (yourInput <= 0) {
      throw new IllegalArgumentException("Input cannot be less than equal to zero");
    }
    if (yourInput >= 1 && yourInput <= allPath.size()) {
      currentPoint = changeCurrentLocation(allPath.get(yourInput - 1));
    } else {
      throw new IllegalArgumentException("Invalid Value Entered!!");
    }
    return currentPoint;
  }

  /**
   * This method is used to get the list of all possible direction where a player can travel from
   * one node to another.
   *
   * @return the list of all possible directions.
   */
  public List<String> getAllDirections() {
    return new ArrayList<>(allDirections);
  }

  /**
   * This method is used to check if the player has entered the valid direction or not.
   *
   * @param direction     Represents the direction where the player is going to move.
   * @param allDirections Represents the list of all possible directions.
   * @return true if the direction is valid else false.
   */
  public boolean validDirection(String direction, List<String> allDirections) {
    if (direction == null || allDirections == null) {
      throw new IllegalArgumentException("Invalid direction or allDirections passed to the method");
    }
    return allDirections.contains(direction);
  }

  /**
   * This method is used to if the player entered distance is valid or not.
   *
   * @param distance    Represents the distance where the player is going to move.
   * @param maxLocation Represents the max location where the player can move.
   * @return true if the distance is valid else false.
   */
  public boolean validDistance(int distance, int maxLocation) {
    if (distance <= 0) {
      throw new IllegalArgumentException("Invalid distance passed to the method");
    }
    if (maxLocation <= 9) {
      throw new IllegalArgumentException("Invalid max location passed to the method");
    }
    return distance <= maxLocation && distance > 0;
  }

  /**
   * This method is used to check where the arrow will reach after providing all the inputs.
   *
   * @param direction       Represents the direction where the player is going to move.
   * @param distance        Represents the distance where the player is going to move.
   * @param currentLocation Represents the current location where the player is.
   * @param graph           Represents the graph where the player is.
   * @param maxLocation     Represents the max location where the player can move.
   * @return the new location of the arrow.
   */
  public int shootArrow(
          String direction, String distance, int currentLocation, Node[][] graph, int maxLocation) {
    if (direction == null || distance == null) {
      throw new IllegalArgumentException("Invalid direction or distance passed to the method");
    }
    if (!validDirection(direction, allDirections)) {
      throw new IllegalArgumentException("Invalid direction passed to the method");
    }
    if (!validDistance(Integer.parseInt(distance), maxLocation)) {
      throw new IllegalArgumentException("Invalid distance passed to the method");
    }
    if (currentLocation == 0) {
      throw new IllegalArgumentException("Invalid current location passed to the method");
    }
    if (graph == null) {
      throw new IllegalArgumentException("Invalid graph passed to the method");
    }
    if (maxLocation <= 9) {
      throw new IllegalArgumentException("Invalid max location passed to the method");
    }
    int distanceToShoot = Integer.parseInt(distance);
    int currentLocationOfArrow = currentLocation;
    int previousLocation = currentLocation;
    boolean top = false;
    boolean bottom = false;
    boolean left = false;
    boolean right = false;
    out:
    for (Node[] nodes : graph) {
      for (Node node : nodes) {

        if (node.getVal() == currentLocationOfArrow) {
          if (direction.equals("N")) {
            if (node.getTop() != null && Objects.equals(node.getTop().getType(), "T")) {
              currentLocationOfArrow = node.getTop().getVal();
            }
            if (node.getTop() != null && Objects.equals(node.getTop().getType(), "C")) {
              currentLocationOfArrow = node.getTop().getVal();
              distanceToShoot--;
            }
            top = true;
            break out;
          } else if (direction.equals("S")) {
            if (node.getBottom() != null && Objects.equals(node.getBottom().getType(), "T")) {

              currentLocationOfArrow = node.getBottom().getVal();
            }
            if (node.getBottom() != null && Objects.equals(node.getBottom().getType(), "C")) {
              currentLocationOfArrow = node.getBottom().getVal();
              distanceToShoot--;
            }
            bottom = true;
            break out;
          } else if (direction.equals("E")) {
            if (node.getRight() != null && Objects.equals(node.getRight().getType(), "T")) {
              currentLocationOfArrow = node.getRight().getVal();
            }
            if (node.getRight() != null && Objects.equals(node.getRight().getType(), "C")) {
              currentLocationOfArrow = node.getRight().getVal();
              distanceToShoot--;
            }
            right = true;
            break out;
          } else if (direction.equals("W")) {
            if (node.getLeft() != null && Objects.equals(node.getLeft().getType(), "T")) {
              currentLocationOfArrow = node.getLeft().getVal();
            }
            if (node.getLeft() != null && Objects.equals(node.getLeft().getType(), "C")) {
              currentLocationOfArrow = node.getLeft().getVal();
              distanceToShoot--;
            }
            left = true;
            break out;
          }
        }
      }
    }

    // Move arrow more than one location
    while (distanceToShoot != 0) {

      if (top) {
        if (distanceToShoot == 0) {
          break;
        }
        out:
        for (Node[] nodes : graph) {
          for (Node node : nodes) {
            if (node.getVal() == currentLocationOfArrow) {
              if (node.getTop() != null) {
                if (Objects.equals(node.getTop().getType(), "T")) {
                  previousLocation = currentLocationOfArrow;
                  currentLocationOfArrow = node.getTop().getVal();
                  break out;
                }

                if (Objects.equals(node.getTop().getType(), "C")) {
                  previousLocation = currentLocationOfArrow;
                  currentLocationOfArrow = node.getTop().getVal();
                  distanceToShoot--;
                  break out;
                }
              } else {
                if (Objects.equals(node.getType(), "T")) {
                  if (node.getBottom() != null && node.getBottom().getVal() != previousLocation) {
                    currentLocationOfArrow = node.getBottom().getVal();
                    if (Objects.equals(node.getBottom().getType(), "C")) {
                      distanceToShoot--;
                    }
                    left = false;
                    top = false;
                    bottom = true;
                    right = false;
                    break out;
                  }
                  if (node.getLeft() != null && node.getLeft().getVal() != previousLocation) {
                    currentLocationOfArrow = node.getLeft().getVal();
                    if (Objects.equals(node.getLeft().getType(), "C")) {
                      distanceToShoot--;
                    }
                    left = true;
                    top = false;
                    bottom = false;
                    right = false;
                    break out;
                  }
                  if (node.getRight() != null && node.getRight().getVal() != previousLocation) {
                    currentLocationOfArrow = node.getRight().getVal();
                    if (Objects.equals(node.getRight().getType(), "C")) {
                      distanceToShoot--;
                    }
                    left = false;
                    top = false;
                    bottom = false;
                    right = true;
                    break out;
                  }
                } else {
                  return 0;
                }
              }
            }
          }
        }
      }

      if (bottom) {
        if (distanceToShoot == 0) {
          break;
        }
        out:
        for (Node[] nodes : graph) {
          for (Node node : nodes) {
            if (node.getVal() == currentLocationOfArrow) {
              if (node.getBottom() != null) {
                if (Objects.equals(node.getBottom().getType(), "T")) {
                  previousLocation = currentLocationOfArrow;
                  currentLocationOfArrow = node.getBottom().getVal();
                  break out;
                }

                if (Objects.equals(node.getBottom().getType(), "C")) {
                  previousLocation = currentLocationOfArrow;
                  currentLocationOfArrow = node.getBottom().getVal();
                  distanceToShoot--;
                  break out;
                }
              } else {
                if (Objects.equals(node.getType(), "T")) {
                  if (node.getTop() != null && node.getTop().getVal() != previousLocation) {

                    currentLocationOfArrow = node.getTop().getVal();
                    if (Objects.equals(node.getTop().getType(), "C")) {
                      distanceToShoot--;
                    }
                    left = false;
                    top = true;
                    bottom = false;
                    right = false;
                    break out;
                  }
                  if (node.getLeft() != null && node.getLeft().getVal() != previousLocation) {
                    currentLocationOfArrow = node.getLeft().getVal();
                    if (Objects.equals(node.getLeft().getType(), "C")) {
                      distanceToShoot--;
                    }

                    left = true;
                    top = false;
                    bottom = false;
                    right = false;
                    break out;
                  }
                  if (node.getRight() != null && node.getRight().getVal() != previousLocation) {
                    currentLocationOfArrow = node.getRight().getVal();
                    if (Objects.equals(node.getRight().getType(), "C")) {
                      distanceToShoot--;
                    }
                    left = false;
                    top = false;
                    bottom = false;
                    right = true;
                    break out;
                  }
                } else {
                  return 0;
                }
              }
            }
          }
        }
      }

      if (right) {
        if (distanceToShoot == 0) {
          break;
        }
        out:
        for (Node[] nodes : graph) {
          for (Node node : nodes) {

            if (node.getVal() == currentLocationOfArrow) {
              if (node.getRight() != null) {
                if (Objects.equals(node.getRight().getType(), "T")) {
                  previousLocation = currentLocationOfArrow;
                  currentLocationOfArrow = node.getRight().getVal();
                  break out;
                }

                if (Objects.equals(node.getRight().getType(), "C")) {
                  previousLocation = currentLocationOfArrow;
                  currentLocationOfArrow = node.getRight().getVal();
                  distanceToShoot--;
                  break out;
                }
              } else {
                if (Objects.equals(node.getType(), "T")) {
                  if (node.getBottom() != null && node.getBottom().getVal() != previousLocation) {
                    previousLocation = currentLocationOfArrow;
                    currentLocationOfArrow = node.getBottom().getVal();
                    if (Objects.equals(node.getBottom().getType(), "C")) {
                      distanceToShoot--;
                    }
                    left = false;
                    top = false;
                    bottom = true;
                    right = false;
                    break out;
                  }
                  if (node.getLeft() != null && node.getLeft().getVal() != previousLocation) {
                    previousLocation = currentLocationOfArrow;
                    currentLocationOfArrow = node.getLeft().getVal();
                    if (Objects.equals(node.getLeft().getType(), "C")) {
                      distanceToShoot--;
                    }
                    left = true;
                    top = false;
                    bottom = false;
                    right = false;
                    break out;
                  }
                  if (node.getTop() != null && node.getTop().getVal() != previousLocation) {
                    previousLocation = currentLocationOfArrow;
                    currentLocationOfArrow = node.getTop().getVal();
                    if (Objects.equals(node.getTop().getType(), "C")) {
                      distanceToShoot--;
                    }
                    left = false;
                    top = true;
                    bottom = false;
                    right = false;
                    break out;
                  }
                } else {
                  return 0;
                }
              }
            }
          }
        }
      }

      if (left) {
        if (distanceToShoot == 0) {
          break;
        }
        out:
        for (Node[] nodes : graph) {
          for (Node node : nodes) {
            if (node.getVal() == currentLocationOfArrow) {
              if (node.getLeft() != null) {
                if (Objects.equals(node.getLeft().getType(), "T")) {
                  previousLocation = currentLocationOfArrow;
                  currentLocationOfArrow = node.getLeft().getVal();
                  break out;
                }
                if (Objects.equals(node.getLeft().getType(), "C")) {
                  previousLocation = currentLocationOfArrow;
                  currentLocationOfArrow = node.getLeft().getVal();
                  distanceToShoot--;
                  break out;
                }
              } else {
                if (Objects.equals(node.getType(), "T")) {
                  if (node.getBottom() != null && node.getBottom().getVal() != previousLocation) {
                    currentLocationOfArrow = node.getBottom().getVal();
                    if (Objects.equals(node.getBottom().getType(), "C")) {
                      distanceToShoot--;
                    }
                    left = false;
                    top = false;
                    bottom = true;
                    right = false;
                    break out;
                  }
                  if (node.getTop() != null && node.getTop().getVal() != previousLocation) {
                    currentLocationOfArrow = node.getTop().getVal();
                    if (Objects.equals(node.getTop().getType(), "C")) {
                      distanceToShoot--;
                    }
                    left = false;
                    top = true;
                    bottom = false;
                    right = false;
                    break out;
                  }
                  if (node.getRight() != null && node.getRight().getVal() != previousLocation) {
                    currentLocationOfArrow = node.getRight().getVal();
                    if (Objects.equals(node.getRight().getType(), "C")) {
                      distanceToShoot--;
                    }
                    left = false;
                    top = false;
                    bottom = false;
                    right = true;
                    break out;
                  }
                } else {
                  return 0;
                }
              }
            }
          }
        }
      }
    }

    return currentLocationOfArrow;
  }

  /**
   * This method is used to check if the player shot arrow was missed or it hit the target.
   *
   * @param arrowLocation the location of the arrow.
   * @return true if the arrow hit the target, false if it missed.
   */
  public boolean isArrowMissed(int arrowLocation) {
    if (arrowLocation < 0) {
      throw new IllegalArgumentException("Arrow location cannot be negative.");
    }
    return arrowLocation == 0;
  }

  @Override
  public List<String> getAllNeighbours(Node[][] graph) {
    List<String> neighbours = new ArrayList<>();
    String direction = "";
    for (Node[] nodes : graph) {
      for (Node node : nodes) {
        direction = "";
        if (node.getBottom() != null) {
          direction += "S";
        }
        if (node.getLeft() != null) {
          direction += "W";
        }
        if (node.getTop() != null) {
          direction += "N";
        }
        if (node.getRight() != null) {
          direction += "E";
        }
        neighbours.add(direction);
      }

    }

    return new ArrayList<>(neighbours);
  }

  /**
   * This method set the previous position of the player.
   *
   * @param currentPoint Represents the current location of the player.
   */
  @Override
  public void setPreviousPoint(int currentPoint) {
    previousPosition = currentPoint;

  }

  /**
   * This method gets the previous position of the player.
   *
   * @return The previous position of the player.
   */
  @Override
  public int getPreviousPoint() {
    return previousPosition;
  }

  /**
   * This method adds a pit in the graph.
   *
   * @param maxLocation Represents the max location of the dungeon.
   * @param startPoint  Represents the start location of the player.
   * @param endPoint    Represents the end location of the player.
   * @return The graph with the pit added.
   */
  @Override
  public int addPitPosition(int maxLocation, int startPoint, int endPoint) {
    int pos = this.startPoint;
    while (pos == this.startPoint || pos == endPoint) {
      pitPosition = RandomNumberGenerator.getRandomThiefPosition(maxLocation, random);
      pos = pitPosition;
    }
    return pitPosition;
  }

  /**
   * This method is used to get the pit position located in the dungeon.
   *
   * @return the pit position.
   */
  @Override
  public int getPitPosition() {
    return pitPosition;
  }
}
