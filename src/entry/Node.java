package entry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class creates the node and find the top, bottom, left and right present to each node. It
 * also assigns each node value as T or C.
 */
public class Node {
  private final int val;
  private Node left;
  private Node right;
  private Node top;
  private Node bottom;
  private String type;
  private static List<List<Node>> leftOver = new ArrayList<>();
  private static List<List<Node>> nodePairs = new ArrayList<>();
  private StringBuilder output = new StringBuilder();

  /**
   * Constructor to create a node and assign value to each node.
   *
   * @param val Represents the value passed to the node.
   */
  public Node(int val) {
    if (val <= 0) {
      throw new IllegalArgumentException("Value cannot be negative!");
    }
    this.val = val;
  }

  /**
   * This method assign each node as T or C based on the number of connection to each neighbour.
   *
   * @param graph Represents the graph of the dungeon.
   * @return the node representing as T or C.
   */
  public static Node[][] setVertexType(Node[][] graph) {
    if (graph.length < 0 || graph == null) {
      throw new IllegalArgumentException("Invalid graph passed to the method");
    }
    int numEntries;
    for (Node[] nodes : graph) {
      for (Node node : nodes) {
        numEntries = 0;
        if (node.getLeft() != null) {
          numEntries++;
        }
        if (node.getRight() != null) {
          numEntries++;
        }
        if (node.getTop() != null) {
          numEntries++;
        }
        if (node.getBottom() != null) {
          numEntries++;
        }
        String type = getNodeType(numEntries);
        node.setType(type);
      }
    }
    return Arrays.stream(graph).map(Node[]::clone).toArray(Node[][]::new);
  }

  /**
   * This method finds the type of node based on number of entries.
   *
   * @param numEntries Represents the number of connection a particular node.
   * @return the string representaion as T or C for each node.
   */
  public static String getNodeType(int numEntries) {
    if (numEntries <= 0) {
      throw new IllegalArgumentException("Invalid entries val passed it cannot be less than zero");
    }
    if (numEntries == 1 || numEntries == 3 || numEntries == 4) {
      return "C";
    }
    if (numEntries == 2) {
      return "T";
    }
    return "T";
  }

  /**
   * This method prints the dungeon formed after assigning each node as T or C.
   *
   * @param resultGraph Represents the graph after setting up each node value.
   * @return the string representation of the entire dungeon.
   */
  public static String showDungeon(Node[][] resultGraph) {
    if (resultGraph.length < 0 || resultGraph == null) {
      throw new IllegalArgumentException("Invalid graph passed to the method");
    }

    StringBuilder output = new StringBuilder();
    for (Node[] nodes : resultGraph) {
      for (Node node : nodes) {
        output.append(node.getVal()).append(": ").append(node.getType()).append(" ");
      }
      output.append('\n');
    }
    return output.toString();
  }

  /**
   * This method find the type of the node based on the value of node passed to the method.
   *
   * @param graph Represents the graph of the dungeon.
   * @param val Represents the value of the node.
   * @return the string representation of the node.
   */
  public static String showNodeType(Node[][] graph, int val) {
    if (graph.length < 0 || graph == null) {
      throw new IllegalArgumentException("Invalid graph passed to the method");
    }
    if (val == 0) {
      throw new IllegalArgumentException("Invalid value passed to the method");
    }
    for (Node[] nodes : graph) {
      for (Node node : nodes) {
        if (node.getVal() == val) {
          return node.getType();
        }
      }
    }
    return "";
  }

  /**
   * This method prints the list of possible path from one node to another in the dungeon.
   *
   * @param resultGraph Represents the graph after setting up each node value.
   * @return the String representation of list of possible path from each node to other node.
   */
  public static String showPairs(Node[][] resultGraph) {
    if (resultGraph.length < 0 || resultGraph == null) {
      throw new IllegalArgumentException("Invalid graph passed to the method");
    }
    String p = "";
    StringBuilder dungeon = new StringBuilder();
    for (Node[] nodes : resultGraph) {
      for (Node node : nodes) {
        dungeon.append("\n");
        p += node;
        dungeon.append("Node: ").append(node);
        if (node.getLeft() != null) {
          p += dungeon.append("\n").append(node).append("->");
          dungeon.append(node.getLeft());
        }
        if (node.getRight() != null) {
          dungeon.append("\n").append(node).append("->");
          dungeon.append(node.getRight());
        }
        if (node.getTop() != null) {
          dungeon.append("\n").append(node).append("->");
          dungeon.append(node.getTop());
        }
        if (node.getBottom() != null) {
          dungeon.append("\n").append(node).append("->");
          dungeon.append(node.getBottom());
        }
      }
    }
    return dungeon.toString();
  }

  /**
   * This method creates an array of max_locations.
   *
   * @param numCols Represents the num of cols of the dungeon.
   * @param numRows Represents the num of rows of the dungeon.
   * @return the 2d array to store element connections.
   */
  public static int[][] getArrayOfRowsAndCol(int numCols, int numRows) {
    if (numRows <= 3) {
      throw new IllegalArgumentException("Invalid no of rows entered, number of rows too less");
    }
    if (numCols <= 3) {
      throw new IllegalArgumentException("Invalid no of cols entered, number of cols are too less");
    }
    int[][] arr = new int[numRows][numCols];

    int val = 1;
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        arr[i][j] = val++;
      }
    }

    return arr;
  }

  /**
   * Thsi method add node together and form a cloud of nodes.
   *
   * @param graph Represents the all the nodes present in dungeon.
   * @return the clouds connections formed.
   */
  public static List<List<Node>> addNodesToCloud(Node[][] graph) {
    if (graph.length < 0 || graph == null) {
      throw new IllegalArgumentException("Invalid graph passed to the method");
    }
    List<List<Node>> clouds = new ArrayList<>();
    for (Node[] nodes : graph) {
      for (Node node : nodes) {
        clouds.add(
            new ArrayList<>() {
              {
                add(node);
              }
            });
      }
    }
    List<List<Node>> copy = clouds.stream().collect(Collectors.toList());
    return copy;
  }

  /**
   * This method print the dungeon based on rows and cols.
   *
   * @return the string representation of the dungeon.
   */
  public static String printDungeonWithConnection() {
    String result =
        "1 -- 2 -- 3    4\n"
            + "     |    |    |\n"
            + "5 -- 6    7    8\n"
            + "     |    |    |\n"
            + "9    10   11 -- 12\n"
            + "|    |    |  \n"
            + "13 --14  15 -- 16\n";
    return result;
  }

  /**
   * This method print the dungeon based on rows and cols.
   *
   * @return the string representation of the dungeon.
   */
  public static String printDungeonWithConnectionAndNonWrapped() {
    String result =
        "1 -- 2    3 -- 4 -- 5--\n"
            + "|    |    |\n"
            + "6    7 -- 8 -- 9 -- 10\n"
            + "|    |               | \n"
            + "11   12   13   14 -- 15\n"
            + "     |    |    |     |\n"
            + "16 --17 --18   19    20--\n"
            + "     |               |  \n"
            + "21 --22 --23 --24    25\n"
            + "|                    |  ";
    return result;
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
    Node node = (Node) o;
    return val == node.val;
  }

  /**
   * This method check the hashcode of two player object.
   *
   * @return the integer value as 1,0 or -1.
   */
  @Override
  public int hashCode() {
    return Objects.hash(val);
  }

  /**
   * This method find the possible path from one node to other end and end returns a list of node
   * representing the minimum path to traverse each node.
   *
   * @param random Represents the random object.
   * @param nodePairs Represents the list of node pairs.
   * @param clouds Represents the clouds formed after combining neighbours.
   * @param leftOver Represents the left over after one single cloud is formed.
   * @return the list of node representing the minimum path to traverse each node.
   */
  public static List<Node> possiblePath(
      Random random,
      List<List<Node>> nodePairs,
      List<List<Node>> clouds,
      List<List<Node>> leftOver) {
    if (random == null) {
      throw new IllegalArgumentException("Random object cannot be null!");
    }
    if (nodePairs == null) {
      throw new IllegalArgumentException("Invalid node pairs passed from user to this method");
    }
    if (clouds.size() <= 0 || clouds == null) {
      throw new IllegalArgumentException("Invalid clouds passed from user to this method");
    }
    if (leftOver == null) {
      throw new IllegalArgumentException("Invalid left over passed from user to this method");
    }
    int randIdx;
    List<Node> nodePair;
    do {
      randIdx = random.nextInt(nodePairs.size());
      nodePair = nodePairs.get(randIdx);
      mergeClouds(nodePair, clouds, leftOver);
      nodePairs.remove(randIdx);
    }
    while (nodePairs.size() != 0);

    List<Node> allVertexPosition = new ArrayList<>();
    for (int i = 0; i < clouds.size(); i++) {
      for (int j = 0; j < clouds.get(i).size(); j++) {
        allVertexPosition.add(clouds.get(i).get(j));
      }
    }
    List<Node> copy = allVertexPosition.stream().collect(Collectors.toList());
    return copy;
  }

  /**
   * This method add leftOver to node pairs to increase connection based on the connectivity given
   * by the player.
   *
   * @param random Represents the random object.
   * @param nodePairs Represents the list of node pairs.
   * @param leftOver Represents the left over node pairs.
   * @param connectivity Represents the integer value representing the connectivity.
   * @return the list of nodes showing new updated minimum path.
   */
  public static List<List<Node>> addPairsToAllVertex(
      Random random, List<List<Node>> nodePairs, List<List<Node>> leftOver, int connectivity) {
    if (nodePairs == null) {
      throw new IllegalArgumentException("Invalid node pairs passed from user to this method");
    }
    if (random == null) {
      throw new IllegalArgumentException("Random object cannot be null!");
    }
    if (leftOver == null) {
      throw new IllegalArgumentException("Invalid left over passed from user to this method");
    }
    if (connectivity < 1 || connectivity > leftOver.size()) {
      throw new IllegalArgumentException("Invalid connectivity value entered");
    }
    int randIdx;
    List<Node> nodePair;
    int c = connectivity;
    do {
      randIdx = random.nextInt(leftOver.size());
      nodePair = leftOver.get(randIdx);
      nodePairs.add(nodePair);
      leftOver.remove(randIdx);
      c--;
    }
    while (c != 0);
    List<List<Node>> copy = nodePairs.stream().collect(Collectors.toList());
    return copy;
  }

  /**
   * This method assign the left node to the left variable.
   *
   * @param left Represents the left variable.
   */
  private void setLeft(Node left) {
    this.left = left;
  }

  /**
   * This method assign the right node to the right variable.
   *
   * @param right Represents the right variable.
   */
  private void setRight(Node right) {
    this.right = right;
  }

  /**
   * This method assign the top node to the top variable.
   *
   * @param top Represents the top variable.
   */
  private void setTop(Node top) {
    this.top = top;
  }

  /**
   * This method assign the bottom node to the bottom variable.
   *
   * @param bottom Represents the bottom variable.
   */
  private void setBottom(Node bottom) {
    this.bottom = bottom;
  }

  /**
   * This method assign the type node to the type variable.
   *
   * @param type Represents the type variable.
   */
  private void setType(String type) {
    this.type = type;
  }

  /**
   * This method finds the left of the node.
   *
   * @return the left value of the node.
   */
  public Node getLeft() {
    return left;
  }

  /**
   * This method finds the top of the node.
   *
   * @return the top value of the node.
   */
  public Node getTop() {
    return top;
  }

  /**
   * This method finds the right of the node.
   *
   * @return the right value of the node.
   */
  public Node getRight() {
    return right;
  }

  /**
   * This method finds the bottom of the node.
   *
   * @return the bottom value of the node.
   */
  public Node getBottom() {
    return bottom;
  }

  /**
   * This method finds the val of the node.
   *
   * @return the val value of the node.
   */
  public int getVal() {
    return val;
  }

  /**
   * This method finds the type of the node.
   *
   * @return the type value of the node.
   */
  public String getType() {
    return type;
  }

  /**
   * This method finds the string representation of the node.
   *
   * @return the string representation of the node.
   */
  @Override
  public String toString() {
    return "" + val;
  }

  /**
   * This method find the list of node pairs for unwrapped dungeon.
   *
   * @return the list of node pairs for unwrapped dungeon.
   */
  public List<List<Node>> getNodePairs() {
    Set<List<Node>> nodePairs = new HashSet<>();
    getNodePairsRight(nodePairs);
    getNodePairsBottom(nodePairs);

    List<List<Node>> nodePairsList = new ArrayList<>(nodePairs);
    nodePairsList.sort(Comparator.comparingInt(o -> o.get(0).getVal()));
    List<List<Node>> copy = nodePairsList.stream().collect(Collectors.toList());
    return copy;
  }

  /**
   * This method finds the list of node pairs in case dungeon is wrapped.
   *
   * @return the list of node pairs for wrapped dungeon.
   */
  public List<List<Node>> getNodePairsWrapped() {
    Set<List<Node>> nodePairs = new HashSet<>();
    getNodePairsRight(nodePairs);
    getNodePairsBottom(nodePairs);
    getNodePairsLeft(nodePairs);

    List<List<Node>> nodePairsList = new ArrayList<>(nodePairs);
    nodePairsList.sort(Comparator.comparingInt(o -> o.get(0).getVal()));
    List<List<Node>> copy = nodePairsList.stream().collect(Collectors.toList());
    return copy;
  }

  private void getNodePairsRight(Set<List<Node>> nodePairs) {
    if (this.right != null) {
      List<Node> nodePair = new ArrayList<>();
      nodePair.add(this);
      nodePair.add(this.right);
      nodePairs.add(nodePair);
      if (this.right.getBottom() != null) {
        this.right.getNodePairsBottom(nodePairs);
      }
      this.right.getNodePairsRight(nodePairs);
    }
  }

  private void getNodePairsBottom(Set<List<Node>> nodePairs) {
    if (this.bottom != null) {
      List<Node> nodePair = new ArrayList<>();
      nodePair.add(this);
      nodePair.add(this.bottom);
      nodePairs.add(nodePair);
      if (this.bottom.getRight() != null) {
        this.bottom.getNodePairsRight(nodePairs);
      }
      this.bottom.getNodePairsBottom(nodePairs);
    }
  }

  private void getNodePairsTop(Set<List<Node>> nodePairs) {
    if (this.top != null) {
      List<Node> nodePair = new ArrayList<>();
      nodePair.add(this);
      nodePair.add(this.top);
      nodePairs.add(nodePair);
      if (this.top.getLeft() != null) {
        this.top.getNodePairsLeft(nodePairs);
      }
      this.top.getNodePairsTop(nodePairs);
    }
  }

  private void getNodePairsLeft(Set<List<Node>> nodePairs) {
    if (this.left != null) {
      List<Node> nodePair = new ArrayList<>();
      nodePair.add(this);
      nodePair.add(this.left);
      nodePairs.add(nodePair);
      if (this.left.getTop() != null) {
        this.left.getNodePairsTop(nodePairs);
      }
      this.left.getNodePairsLeft(nodePairs);
    }
  }

  /**
   * This method generates the graph based on the arr of size based on rows and column.
   *
   * @param arr Represents the 2d array passed based on dungeon rows and column.
   * @return the 2d array of type node.
   */
  public static Node[][] generateGraphFromArr(int[][] arr) {
    if (arr == null) {
      throw new IllegalArgumentException("Array cannot be null");
    }
    Node[][] nodes = new Node[arr.length][arr[0].length];
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[i].length; j++) {
        nodes[i][j] = new Node(arr[i][j]);
      }
    }

    for (int i = 0; i < nodes.length; i++) {
      for (int j = 0; j < nodes[i].length; j++) {
        if ((i - 1) >= 0) {
          nodes[i][j].setTop(nodes[i - 1][j]);
        }

        if ((i + 1) < nodes.length) {
          nodes[i][j].setBottom(nodes[i + 1][j]);
        }

        if ((j - 1) >= 0) {
          nodes[i][j].setLeft(nodes[i][j - 1]);
        }

        if ((j + 1) < nodes[i].length) {
          nodes[i][j].setRight(nodes[i][j + 1]);
        }
      }
    }

    Node[][] copy = nodes.clone();
    return copy;
  }

  /**
   * This method merge the clouds and form one clouds based on all the node pairs created randomly.
   *
   * @param nodePair Represents the list of node pairs of type node.
   * @param clouds Represents the list of cloud of type node.
   * @param leftOver Represents the leftOver array list of node.
   */
  public static void mergeClouds(
      List<Node> nodePair, List<List<Node>> clouds, List<List<Node>> leftOver) {
    if (nodePair == null) {
      throw new IllegalArgumentException("Invalid possible path passed from user to this method");
    }
    if (clouds == null) {
      throw new IllegalArgumentException("Invalid clouds passed from user to this method!");
    }
    if (leftOver == null) {
      throw new IllegalArgumentException("Invalid left over passed from user to this method");
    }
    for (int i = 0; i < clouds.size(); i++) {
      if (clouds.get(i).contains(nodePair.get(0))) {
        List<Node> toRemove = findCloud(nodePair.get(1), clouds);
        if (toRemove != null) {
          if (toRemove.contains(nodePair.get(0))) {
            leftOver.add(nodePair);
            removeNeighbour(nodePair.get(0), nodePair.get(1));
            removeNeighbour(nodePair.get(1), nodePair.get(0));
          } else {
            clouds.get(i).addAll(toRemove);
            deleteCloud(toRemove, clouds);
          }
        }
      }
    }
  }

  private static void removeNeighbour(Node sourceNode, Node targetNode) {
    if (sourceNode.getLeft() != null && sourceNode.getLeft().getVal() == targetNode.getVal()) {
      sourceNode.setLeft(null);
    } else if (sourceNode.getRight() != null
        && sourceNode.getRight().getVal() == targetNode.getVal()) {
      sourceNode.setRight(null);
    } else if (sourceNode.getTop() != null && sourceNode.getTop().getVal() == targetNode.getVal()) {
      sourceNode.setTop(null);
    } else if (sourceNode.getBottom() != null
        && sourceNode.getBottom().getVal() == targetNode.getVal()) {
      sourceNode.setBottom(null);
    }
  }

  private static void deleteCloud(List<Node> removeCloud, List<List<Node>> clouds) {
    for (int i = 0; i < clouds.size(); i++) {
      if (clouds.get(i).equals(removeCloud)) {
        clouds.remove(removeCloud);
      }
    }
  }

  private static List<Node> findCloud(Node removeCloud, List<List<Node>> clouds) {
    for (int i = 0; i < clouds.size(); i++) {
      if (clouds.get(i).contains(removeCloud)) {
        return clouds.get(i);
      }
    }

    return null;
  }
}
