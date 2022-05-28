import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import entry.Dungeon;
import entry.Node;
import entry.RandomNumberGenerator;
import entry.Treasure;
import entry.DungeonPlayer;
import entry.TreasureType;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

/**
 * A junit class to test all dungeon operations and player moves in the game.
 */
public class DungeonTest {
  private Random random;

  /**
   * Set up the random object and assign a seed to it.
   */
  @Before
  public void setUp() {
    random = new Random(42);
  }

  /**
   * Test if dungeon contains equal number of vertex position as expected.
   */
  @Test
  public void testDungeonCreation() {
    int rows = 6;
    int cols = 8;
    int[][] arr = Node.getArrayOfRowsAndCol(rows, cols);
    assertEquals(8, Arrays.stream(arr).count());
  }

  /**
   * Test if dungeon player description is displayed correctly.
   */
  @Test
  public void testPlayerCreationNameAndDesc() {
    Random random = new Random(42);
    DungeonPlayer dp = new DungeonPlayer(RandomNumberGenerator.getRandomName(random),
            RandomNumberGenerator.getRandomCharacter(random));
    assertEquals("\n" +
            "Name='AHWMA'\n" +
            "Character='The mighty king born  to be a leader'", dp.toString());
  }

  /**
   * Test if dungeon player description cannot be null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayerCreationNameAndDescIsNull() {
    DungeonPlayer dp = new DungeonPlayer(RandomNumberGenerator.getRandomName(null),
            RandomNumberGenerator.getRandomCharacter(null));
    assertEquals("Player :\n"
            + "Name='AHWMA'\n"
            + "Character='The mighty king born  to be a leader'", dp.toString());
  }

  /**
   * Test if the dungeon player toString value is equal as expected.
   */
  @Test
  public void testDungeonPlayer() {
    random = new Random(42);
    DungeonPlayer dp = new DungeonPlayer(RandomNumberGenerator.getRandomName(random),
            RandomNumberGenerator.getRandomCharacter(random));
    assertEquals("\n" +
            "Name='AHWMA'\n" +
            "Character='The mighty king born  to be a leader'", dp.toString());
  }

  /**
   * Test if the vertex should be T and C based on the connections.
   */
  @Test
  public void testVertexSetAsTAndC() {
    Random random = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    String[] expectedType = {"T", "C", "C", "T", "C", "C", "C", "C", "C", "C", "C",
        "C", "T", "C", "C", "T"};
    int[] expectedConnection = {2, 3, 3, 2, 3, 4, 4, 3, 3, 4, 4, 3, 2, 3, 3, 2};
    int i = 0;
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
        assertEquals(expectedConnection[i], numEntries);
        String type = Node.getNodeType(numEntries);
        assertEquals(expectedType[i], type);
        i++;
      }
    }
  }

  /**
   * Test the value is T and C based on the number of connection.
   */
  @Test
  public void testVertexAsTandC() {
    assertEquals("T", Node.getNodeType(2));
    assertEquals("C", Node.getNodeType(1));
    assertEquals("C", Node.getNodeType(3));
    assertEquals("C", Node.getNodeType(4));
  }

  /**
   * Test print dungeon with connection when it is a wrapping dungeon.
   */
  @Test
  public void testDungeonWithConnectionIsNotWrapped() {
    Random random = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = new ArrayList<>();
    nodePairs = head.getNodePairs();
    Node[][] resultGraph = Node.setVertexType(graph);
    List<List<Node>> leftOver = new ArrayList<>();

    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    String showAllPairs = Node.showPairs(resultGraph);
    Dungeon dungeon = new Dungeon(4, 4, allVertexPosition,
             15, 15, "NO", graph, "2");
    String dungeonWithConnection = Node.printDungeonWithConnection();
    assertEquals("1 -- 2 -- 3    4\n"
            + "     |    |    |\n"
            + "5 -- 6    7    8\n"
            + "     |    |    |\n"
            + "9    10   11 -- 12\n"
            + "|    |    |  \n"
            + "13 --14  15 -- 16\n", dungeonWithConnection);
  }


  /**
   * Test print dungeon with connection when it is a wrapping dungeon.
   */
  @Test
  public void testDungeonWithConnectionIsWrapped() {
    Random random = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(5, 5);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = new ArrayList<>();
    nodePairs = head.getNodePairs();
    Node[][] resultGraph = Node.setVertexType(graph);
    List<List<Node>> leftOver = new ArrayList<>();

    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    String showAllPairs = Node.showPairs(resultGraph);
    Dungeon dungeon = new Dungeon(5, 5, allVertexPosition,
            4, 4, "YES", graph, "2");
    String dungeonWithConnection = Node.printDungeonWithConnectionAndNonWrapped();
    assertEquals("1 -- 2    3 -- 4 -- 5--\n" +
            "|    |    |\n" +
            "6    7 -- 8 -- 9 -- 10\n" +
            "|    |               | \n" +
            "11   12   13   14 -- 15\n" +
            "     |    |    |     |\n" +
            "16 --17 --18   19    20--\n" +
            "     |               |  \n" +
            "21 --22 --23 --24    25\n" +
            "|                    |  ", dungeonWithConnection);
  }

  /**
   * Test if the minimum path, dungeon creation, treasure list and all possible path.
   */
  @Test
  public void testMinimumPathAndTreasureListAndDungeon() {
    Random random = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = new ArrayList<>();
    nodePairs = head.getNodePairs();
    List<List<Node>> leftOver = new ArrayList<>();
    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    int start_point = Dungeon.randomStartPoint(random, 4, 4);
    assertEquals(3, start_point);
    Node[][] resultGraph = Node.setVertexType(graph);
    String showAllPairs = Node.showPairs(resultGraph);
    Dungeon dungeon = new Dungeon(4, 4, allVertexPosition,
            start_point, start_point, "NO", graph, "2");
    int end_point = dungeon.randomEndPoint(allVertexPosition, random, start_point, resultGraph,
            4, 4);
    assertEquals(10, end_point);
    Treasure treasure = new Treasure(16, 40);
    List<String> treasureList = treasure.assignTreasure(graph, random);
    assertEquals("[T, T, T, RUBIES, T, T, T, T, T, RUBIES, RUBIES, DIAMOND, "
            + "RUBIES, DIAMOND, NT, NT]", treasureList.toString());
    assertEquals("[4, 9, 13, 1, 2, 3, 7, 11, 15, 8, 12, 16, 5, 6, 10, 14]",
            allVertexPosition.toString());
    assertEquals("[[2, 6], [10, 11], [6, 7], [14, 15], [15, 16], [13, 14], [5, 9],"
            + " [3, 4], [7, 8]]", leftOver.toString());
    String resultGraphWithDungeon = Node.showDungeon(resultGraph);
    assertEquals("1: T 2: T 3: T 4: C \n"
            + "5: T 6: T 7: T 8: T \n"
            + "9: T 10: C 11: C 12: C \n"
            + "13: C 14: C 15: C 16: C \n", resultGraphWithDungeon);
    assertEquals("\nNode: 1\n"
            + "1->2\n"
            + "1->5\n"
            + "Node: 2\n"
            + "2->1\n"
            + "2->3\n"
            + "Node: 3\n"
            + "3->2\n"
            + "3->7\n"
            + "Node: 4\n"
            + "4->8\n"
            + "Node: 5\n"
            + "5->6\n"
            + "5->1\n"
            + "Node: 6\n"
            + "6->5\n"
            + "6->10\n"
            + "Node: 7\n"
            + "7->3\n"
            + "7->11\n"
            + "Node: 8\n"
            + "8->4\n"
            + "8->12\n"
            + "Node: 9\n"
            + "9->10\n"
            + "9->13\n"
            + "Node: 10\n"
            + "10->9\n"
            + "10->6\n"
            + "10->14\n"
            + "Node: 11\n"
            + "11->12\n"
            + "11->7\n"
            + "11->15\n"
            + "Node: 12\n"
            + "12->11\n"
            + "12->8\n"
            + "12->16\n"
            + "Node: 13\n"
            + "13->9\n"
            + "Node: 14\n"
            + "14->10\n"
            + "Node: 15\n"
            + "15->11\n"
            + "Node: 16\n"
            + "16->12", showAllPairs);

  }

  /**
   * Test connectivity by checking the size of leftOver before and after
   * implementing interconnectivity.
   */
  @Test
  public void testIncConnectivity() {
    Random random = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = head.getNodePairs();
    List<List<Node>> leftOver = new ArrayList<>();
    List<List<Node>> leftOverAfterConn = new ArrayList<>();
    Node[][] resultGraph = Node.setVertexType(graph);
    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    List<List<Node>> nodePairsForConnec = new ArrayList<>(nodePairs);
    assertEquals(9, leftOver.size());
    assertEquals("[[2, 6], [10, 11], [6, 7], [14, 15], [15, 16],"
            + " [13, 14], [5, 9], [3, 4], [7, 8]]", leftOver.toString());
    nodePairs = Node.addPairsToAllVertex(random, nodePairsForConnec, leftOver, 2);
    assertEquals(2, nodePairs.size());
    assertEquals(7, leftOver.size());
    assertEquals("[[2, 6], [10, 11], [6, 7], [14, 15], [15, 16],"
            + " [5, 9], [3, 4]]", leftOver.toString());
  }

  /**
   * Test that the minimum distance between random selected points is at least 5.
   */
  @Test
  public void testMinDistLegal() {
    Random random = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = head.getNodePairs();
    List<List<Node>> leftOver = new ArrayList<>();
    Node[][] resultGraph = Node.setVertexType(graph);
    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    int start_point = Dungeon.randomStartPoint(random, 4, 4);
    assertEquals(3, start_point);
    Dungeon dungeon = new Dungeon(4, 4, allVertexPosition,
            start_point, start_point, "NO", graph, "2");
    int end_point = dungeon.randomEndPoint(allVertexPosition, random, start_point, resultGraph,
            4, 4);
    assertEquals(10, end_point);
    String showAllPairs = Node.showPairs(resultGraph);
    ArrayList<List<Integer>> adj = new ArrayList<>();
    ArrayList<Integer> a1 = new ArrayList<Integer>();
    adj.add(a1);
    ArrayList<Integer> a2 = new ArrayList<Integer>();
    a2.add(2);
    a2.add(2);
    adj.add(a2);
    ArrayList<Integer> a3 = new ArrayList<Integer>();
    a3.add(1);
    a3.add(1);
    a3.add(3);
    a3.add(6);
    a3.add(3);
    a3.add(6);
    adj.add(a3);
    ArrayList<Integer> a4 = new ArrayList<Integer>();
    a4.add(2);
    a4.add(2);
    a4.add(7);
    a4.add(7);
    adj.add(a4);
    ArrayList<Integer> a5 = new ArrayList<Integer>();
    a5.add(8);
    a5.add(8);
    adj.add(a5);
    ArrayList<Integer> a6 = new ArrayList<Integer>();
    a6.add(6);
    a6.add(6);
    adj.add(a6);
    ArrayList<Integer> a7 = new ArrayList<Integer>();
    a7.add(2);
    a7.add(5);
    a7.add(5);
    a7.add(2);
    a7.add(10);
    a7.add(10);
    adj.add(a7);
    ArrayList<Integer> a8 = new ArrayList<Integer>();
    a8.add(3);
    a8.add(3);
    a8.add(11);
    a8.add(11);
    adj.add(a8);
    ArrayList<Integer> a9 = new ArrayList<Integer>();
    a9.add(4);
    a9.add(4);
    a9.add(12);
    a9.add(12);
    adj.add(a9);
    ArrayList<Integer> a10 = new ArrayList<Integer>();
    a10.add(13);
    a10.add(13);
    adj.add(a10);
    ArrayList<Integer> a11 = new ArrayList<Integer>();
    a11.add(6);
    a11.add(6);
    a11.add(14);
    a11.add(14);
    adj.add(a11);
    ArrayList<Integer> a12 = new ArrayList<Integer>();
    a12.add(7);
    a12.add(12);
    a12.add(7);
    a12.add(15);
    a12.add(12);
    a12.add(15);
    adj.add(a12);
    ArrayList<Integer> a13 = new ArrayList<Integer>();
    a13.add(8);
    a13.add(11);
    a13.add(11);
    a13.add(8);
    adj.add(a13);
    ArrayList<Integer> a14 = new ArrayList<Integer>();
    a14.add(9);
    a14.add(14);
    a14.add(9);
    a14.add(14);
    adj.add(a14);
    ArrayList<Integer> a15 = new ArrayList<Integer>();
    a15.add(10);
    a15.add(13);
    a15.add(13);
    a15.add(10);
    adj.add(a15);
    ArrayList<Integer> a16 = new ArrayList<Integer>();
    a16.add(11);
    a16.add(16);
    a16.add(11);
    a16.add(16);
    adj.add(a16);
    ArrayList<Integer> a17 = new ArrayList<Integer>();
    a17.add(15);
    a17.add(15);
    int path = Dungeon.printShortestDistance(adj, 1, 15, 17);
    assertEquals(5, path);
    assertNotEquals(2, path);
  }

  /**
   * Test when node 16 is not connected to graph hence error will be thrown since their is
   * no common path.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMinDistIllegal() {
    ArrayList<List<Integer>> adj = new ArrayList<>();
    ArrayList<Integer> a1 = new ArrayList<Integer>();
    adj.add(a1);
    ArrayList<Integer> a2 = new ArrayList<Integer>();
    a2.add(2);
    a2.add(2);
    adj.add(a2);
    ArrayList<Integer> a3 = new ArrayList<Integer>();
    a3.add(1);
    a3.add(1);
    a3.add(3);
    a3.add(6);
    a3.add(3);
    a3.add(6);
    adj.add(a3);
    ArrayList<Integer> a4 = new ArrayList<Integer>();
    a4.add(2);
    a4.add(2);
    a4.add(7);
    a4.add(7);
    adj.add(a4);
    ArrayList<Integer> a5 = new ArrayList<Integer>();
    a5.add(8);
    a5.add(8);
    adj.add(a5);
    ArrayList<Integer> a6 = new ArrayList<Integer>();
    a6.add(6);
    a6.add(6);
    adj.add(a6);
    ArrayList<Integer> a7 = new ArrayList<Integer>();
    a7.add(2);
    a7.add(5);
    a7.add(5);
    a7.add(2);
    a7.add(10);
    a7.add(10);
    adj.add(a7);
    ArrayList<Integer> a8 = new ArrayList<Integer>();
    a8.add(3);
    a8.add(3);
    a8.add(11);
    a8.add(11);
    adj.add(a8);
    ArrayList<Integer> a9 = new ArrayList<Integer>();
    a9.add(4);
    a9.add(4);
    a9.add(12);
    a9.add(12);
    adj.add(a9);
    ArrayList<Integer> a10 = new ArrayList<Integer>();
    a10.add(13);
    a10.add(13);
    adj.add(a10);
    ArrayList<Integer> a11 = new ArrayList<Integer>();
    a11.add(6);
    a11.add(6);
    a11.add(14);
    a11.add(14);
    adj.add(a11);
    ArrayList<Integer> a12 = new ArrayList<Integer>();
    a12.add(7);
    a12.add(12);
    a12.add(7);
    a12.add(15);
    a12.add(12);
    a12.add(15);
    adj.add(a12);
    ArrayList<Integer> a13 = new ArrayList<Integer>();
    a13.add(8);
    a13.add(11);
    a13.add(11);
    a13.add(8);
    adj.add(a13);
    ArrayList<Integer> a14 = new ArrayList<Integer>();
    a14.add(9);
    a14.add(14);
    a14.add(9);
    a14.add(14);
    adj.add(a14);
    ArrayList<Integer> a15 = new ArrayList<Integer>();
    a15.add(10);
    a15.add(13);
    a15.add(13);
    a15.add(10);
    adj.add(a15);
    ArrayList<Integer> a16 = new ArrayList<Integer>();
    a16.add(11);
    a16.add(11);
    adj.add(a16);
    //16 connection is removed intentionally.
    ArrayList<Integer> a17 = new ArrayList<Integer>();
    a17.add(15);
    a17.add(15);
    int path = Dungeon.printShortestDistance(adj, 1, 16, 17);
  }

  /**
   * Test interconnectivity should be between 1 and leftOver list size.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInterConnectivityWithIllegalValue() {
    int start_point1 = 15;
    int end_point1 = 7;
    int connectivity = -2;
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = head.getNodePairs();
    List<List<Node>> nodePairsForConnec = new ArrayList<>(nodePairs);
    List<List<Node>> leftOver = new ArrayList<>();

    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
  }

  /**
   * Test interconnectivity by checking the leftOver pairs before and after the
   * implementation of connectivity.
   */
  @Test
  public void testInterConnectivity() {
    Random random = new Random(42);
    int start_point1 = 15;
    int end_point1 = 7;
    int connectivity = 2;
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = head.getNodePairs();
    List<List<Node>> nodePairsForConnec = new ArrayList<>(nodePairs);
    List<List<Node>> leftOver = new ArrayList<>();
    int nodePairsLengthBeforeConnectivity = nodePairs.size() + 2;
    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    nodePairs = Node.addPairsToAllVertex(random, nodePairsForConnec, leftOver, connectivity);
    System.out.println(nodePairs);
    int nodePairsLengthAfterConnectivity = nodePairs.size();
    assertEquals(26, nodePairsLengthBeforeConnectivity);
    assertEquals(26, nodePairsLengthAfterConnectivity);
  }

  /**
   * Test if the treasure percentage is equal to expected value.
   */
  @Test
  public void testTreasurePercentage() {
    int max_location = 20;
    double treasurePerct = 20;
    Treasure treasure = new Treasure(max_location, treasurePerct);
    assertEquals(20, treasure.getTreasurePerct(), 1e-6);
  }

  /**
   * Test if the treasure value throws error if passed an illegal value.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testTreasurePercentageIllegalValue() {
    int max_location = 20;
    double treasurePerct = -20;
    Treasure treasure = new Treasure(max_location, treasurePerct);

  }

  /**
   * Testing the max locations value is equal to expected value.
   */
  @Test
  public void testMaxLocationsValue() {
    int max_location = 20;
    double treasurePerct = 30;
    Treasure treasure = new Treasure(max_location, treasurePerct);
    assertEquals(20, treasure.getMaxLocations());
  }

  /**
   * Test max locations should not be an illegal value.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMaxLocationsIllegalValue() {
    int max_location = -20;
    double treasurePerct = 30;
    Treasure treasure = new Treasure(max_location, treasurePerct);

  }

  /**
   * Test the random object cannot be null when we are calculating treasure.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testTreasureNotNull() {
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    Treasure treasure = new Treasure(16, 40);
    List<String> treasureList = treasure.assignTreasure(graph, null);
  }

  /**
   * Test the start point random throws error when passed null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testStartPointRandomNull() {

    int start_point = Dungeon.randomStartPoint(null, 4, 4);
  }

  /**
   * Test the start point with expected value.
   */
  @Test
  public void testStartPoint() {
    Random random = new Random(42);
    int start_point = Dungeon.randomStartPoint(random, 4, 4);
    assertEquals(10, start_point);
  }


  /**
   * Test the end point with expected value.
   */
  @Test
  public void testEndPoint() {
    Random random = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];

    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = new ArrayList<>();
    List<List<Node>> leftOver = new ArrayList<>();
    nodePairs = head.getNodePairs();
    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(random, 4, 4);
    assertEquals(3, start_point);
    Dungeon dungeon = new Dungeon(4, 4, allVertexPosition,
            start_point, start_point, "NO", graph, "2");
    int end_point = dungeon.randomEndPoint(allVertexPosition, random, start_point, resultGraph,
            4, 4);
    assertEquals(10, end_point);
  }

  /**
   * Test the end point with expected value.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testEndPointRandomIsNotNull() {
    Random random = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];

    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = new ArrayList<>();
    List<List<Node>> leftOver = new ArrayList<>();
    nodePairs = head.getNodePairs();
    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(random, 4, 4);
    assertEquals(3, start_point);
    Dungeon dungeon = new Dungeon(4, 4, allVertexPosition,
            start_point, start_point, "NO", graph, "2");
    int end_point = dungeon.randomEndPoint(allVertexPosition, null, start_point,
            resultGraph, 4, 4);

  }


  /**
   * Test the treasure is equal to expected value.
   */
  @Test
  public void testTreasure() {
    Random random = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = new ArrayList<>();
    nodePairs = head.getNodePairs();
    List<List<Node>> leftOver = new ArrayList<>();
    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    int start_point = Dungeon.randomStartPoint(random, 4, 4);
    Node[][] resultGraph = Node.setVertexType(graph);

    Treasure treasure = new Treasure(16, 40);
    List<String> treasureList = treasure.assignTreasure(graph, random);

    assertEquals("[T, T, T, DIAMOND, T, T, T, T, T,"
            + " RUBIES, RUBIES, RUBIES, DIAMOND, RUBIES, NT, NT]", treasureList.toString());
  }

  /**
   * Test the treasure is not assigned to tunnel but only cave.
   */
  @Test
  public void testTreasureNotAssignedToT() {
    Random random = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = new ArrayList<>();
    nodePairs = head.getNodePairs();
    List<List<Node>> leftOver = new ArrayList<>();
    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    int start_point = Dungeon.randomStartPoint(random, 4, 4);
    Node[][] resultGraph = Node.setVertexType(graph);
    System.out.println(resultGraph.toString());
    String resultGraphWithDungeon = new String();
    resultGraphWithDungeon = Node.showDungeon(resultGraph);
    List<String> originalListBeforeTreasureAssigned = new ArrayList<>();
    originalListBeforeTreasureAssigned.add("T");
    originalListBeforeTreasureAssigned.add("T");
    originalListBeforeTreasureAssigned.add("T");
    originalListBeforeTreasureAssigned.add("C");
    originalListBeforeTreasureAssigned.add("T");
    originalListBeforeTreasureAssigned.add("T");
    originalListBeforeTreasureAssigned.add("T");
    originalListBeforeTreasureAssigned.add("T");
    originalListBeforeTreasureAssigned.add("T");
    originalListBeforeTreasureAssigned.add("C");
    originalListBeforeTreasureAssigned.add("C");
    originalListBeforeTreasureAssigned.add("C");
    originalListBeforeTreasureAssigned.add("C");
    originalListBeforeTreasureAssigned.add("C");
    originalListBeforeTreasureAssigned.add("C");
    originalListBeforeTreasureAssigned.add("C");
    Treasure treasure = new Treasure(16, 40);
    List<String> treasureList = treasure.assignTreasure(graph, random);
    assertEquals(originalListBeforeTreasureAssigned.get(0), treasureList.get(0));
    assertEquals(originalListBeforeTreasureAssigned.get(2), treasureList.get(2));
    assertEquals(originalListBeforeTreasureAssigned.get(6), treasureList.get(6));
    assertNotEquals(originalListBeforeTreasureAssigned.get(3), treasureList.get(3));
    assertEquals("[T, T, T, DIAMOND, T, T, T, T, T,"
            + " RUBIES, RUBIES, RUBIES, DIAMOND, RUBIES, NT, NT]", treasureList.toString());
  }

  /**
   * Test player takes both the treasure present at the position.
   */
  @Test
  public void testPlayerTakesBothTreasure() {
    Random random = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = new ArrayList<>();
    nodePairs = head.getNodePairs();
    List<List<Node>> leftOver = new ArrayList<>();
    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    Node[][] resultGraph = Node.setVertexType(graph);
    Treasure treasure = new Treasure(16, 40);
    String allTreasure = new String();
    List<String> treasureList = treasure.assignTreasure(graph, random);
    treasureList.set(3, "DIAMOND RUBIES");
    treasureList.set(9, "SAPPHIRES RUBIES");

    allTreasure = treasure.treasureAtIndex(4 - 1);
    assertEquals("DIAMOND RUBIES", allTreasure);
    assertEquals("NT", treasureList.get(3));

    allTreasure = treasure.treasureAtIndex(10 - 1);
    assertEquals("SAPPHIRES RUBIES", allTreasure);
    assertEquals("NT", treasureList.get(3));

    allTreasure = treasure.treasureAtIndex(2 - 1);
    assertEquals("T", allTreasure);
    assertEquals("T", treasureList.get(2));

  }


  /**
   * Test treasure is removed once player traversed a particular vertex.
   */
  @Test
  public void testTreasureRemovedAfterPlayerMovesToPos() {
    Random random = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = new ArrayList<>();
    nodePairs = head.getNodePairs();
    List<List<Node>> leftOver = new ArrayList<>();
    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    Node[][] resultGraph = Node.setVertexType(graph);
    Treasure treasure = new Treasure(16, 40);
    String allTreasure = new String();
    List<String> treasureList = treasure.assignTreasure(graph, random);
    allTreasure = treasure.treasureAtIndex(12 - 1);
    assertEquals("RUBIES", allTreasure);
    assertEquals("NT", treasureList.get(11));

    allTreasure = treasure.treasureAtIndex(10 - 1);
    assertEquals("DIAMOND", allTreasure);
    assertEquals("NT", treasureList.get(9));
  }

  /**
   * Test possible path are printed correctly for one vertex.
   */
  @Test
  public void testPossiblePath() {
    Random random = new Random(42);
    int start_point = 12;
    int end_point = 10;
    int currentLocation = 12;
    Treasure treasure = new Treasure(16, 40);
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = head.getNodePairs();
    List<List<Node>> leftOver = new ArrayList<>();
    StringBuilder allTreasure = new StringBuilder();
    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    Node[][] resultGraph = Node.setVertexType(graph);
    Dungeon dungeon = new Dungeon(4, 4, allVertexPosition,
            start_point, currentLocation, "NO", graph, "2");
    List<StringBuilder> showTreasure = new ArrayList<>();
    List<String> treasureList = treasure.assignTreasure(graph, random);
    dungeon.movePlayer(graph);
    List<Node> allPath = dungeon.getPossiblePath();
    int iterate = 1;
    List<String> possiblePathFromVertex12 = new ArrayList<>();
    possiblePathFromVertex12.add("1. Possible Path to: 11");
    possiblePathFromVertex12.add("2. Possible Path to: 8");
    possiblePathFromVertex12.add("3. Possible Path to: 16");
    for (Node n : allPath) {
      assertEquals(possiblePathFromVertex12.get(iterate - 1),
              iterate + ". " + "Possible Path to: " + n);
      iterate++;
    }
  }

  /**
   * Test player each move with expected current location, treasure collected and input value.
   */
  @Test
  public void dungeonPlayerMove() {
    Random random = new Random(42);
    int start_point = 15;
    int end_point = 10;
    int currentLocation = 15;
    Treasure treasure = new Treasure(16, 40);
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = head.getNodePairs();
    List<List<Node>> leftOver = new ArrayList<>();
    String allTreasure = new String();
    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    Node[][] resultGraph = Node.setVertexType(graph);
    Dungeon dungeon = new Dungeon(4, 4, allVertexPosition,
            start_point, currentLocation, "NO", graph, "2");
    List<String> showTreasure = new ArrayList<>();
    List<String> treasureList = treasure.assignTreasure(graph, random);
    int mov = 0;
    while (currentLocation != end_point) {
      allTreasure = treasure.treasureAtIndex(currentLocation - 1);
      if (allTreasure.equals("T") || allTreasure.equals("NT")) {
        //DO NOTHING
      } else {
        showTreasure.add(allTreasure);
      }
      dungeon.movePlayer(graph);
      List<Node> allPath = dungeon.getPossiblePath();
      int[] user = {1, 2, 1, 1, 1, 2, 1, 2};
      int[] expectedCurrentLocation = {11, 7, 3, 2, 1, 5, 6, 10};
      currentLocation = dungeon.changeCurrentLocation(allPath.get(user[mov] - 1));
      assertEquals(expectedCurrentLocation[mov], currentLocation);
      String[] expectedTreasure = {"[]", "[RUBIES]", "[RUBIES]", "[RUBIES]", "[RUBIES]", "[RUBIES]",
        "[RUBIES]", "[RUBIES]"};
      assertEquals(expectedTreasure[mov], showTreasure.toString());
      mov++;
    }
    allTreasure = treasure.treasureAtIndex(currentLocation - 1);
    if ((allTreasure.equals("T")) || (allTreasure.equals("NT"))) {
      //
    } else {
      showTreasure.add(allTreasure);
    }
    assertEquals("[RUBIES, DIAMOND]", showTreasure.toString());
  }

  /**
   * Test illegal value entered from user while traversing the graph.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayerIllegalInput() {
    Random random = new Random(42);
    int start_point = 15;
    int end_point = 10;
    int currentLocation = 15;
    Treasure treasure = new Treasure(16, 40);
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = head.getNodePairs();
    List<List<Node>> leftOver = new ArrayList<>();
    String allTreasure = new String();
    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    Node[][] resultGraph = Node.setVertexType(graph);
    Dungeon dungeon = new Dungeon(4, 4, allVertexPosition,
            start_point, currentLocation, "NO", graph, "2");
    List<String> showTreasure = new ArrayList<>();
    List<String> treasureList = treasure.assignTreasure(graph, random);
    int mov = 0;
    while (currentLocation != end_point) {
      allTreasure = treasure.treasureAtIndex(currentLocation - 1);
      if (allTreasure.equals("T") || allTreasure.equals("NT")) {
        //DO NOTHING
      } else {
        showTreasure.add(allTreasure);
      }
      dungeon.movePlayer(graph);
      List<Node> allPath = dungeon.getPossiblePath();
      currentLocation = dungeon.validateUserInput(3, allPath);
    }
  }

  /**
   * Test the dungeon toString method print all values correctly.
   */
  @Test
  public void testDungeonValue() {
    Random random = new Random(42);
    int start_point = 15;
    int currentLocation = 15;
    Treasure treasure = new Treasure(16, 40);
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = head.getNodePairs();
    List<List<Node>> leftOver = new ArrayList<>();

    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    Node[][] resultGraph = Node.setVertexType(graph);
    Dungeon dungeon = new Dungeon(4, 4, allVertexPosition,
            start_point, currentLocation, "NO", graph, "2");
    List<String> showTreasure = new ArrayList<>();
    treasure.assignTreasure(graph, random);
    assertEquals("Dungeon{rows=4, cols=4, vertexPosition=[4, 9, 13, 1, 2, 3, 7,"
            + " 11, 15, 8, 12, 16, 5, 6, 10, 14], startPoint=15, endPoint=0,"
            + " currentPoint=15, possiblePath=[]}", dungeon.toString());
  }

  /**
   * Test if all legal values are passed to the constructor and value are checked as expected.
   */
  @Test
  public void testValuePassedToDungeon() {
    Random random = new Random(42);
    int start_point = 15;
    int end_point = 2;
    int currentLocation = 15;
    Treasure treasure = new Treasure(16, 40);
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = head.getNodePairs();
    List<List<Node>> leftOver = new ArrayList<>();

    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    Dungeon dungeon = new Dungeon(4, 4, allVertexPosition, start_point,
            currentLocation, "NO", graph, "2");
    assertEquals("NO", dungeon.getIsWrap());
    assertEquals(4, dungeon.getRows());
    assertEquals(4, dungeon.getCols());
    assertEquals(15, dungeon.getStartPoint());
    assertEquals(0, dungeon.getEndPoint());
    assertEquals("[4, 9, 13, 1, 2, 3, 7, 11, 15, 8, 12, 16, 5, 6, 10, 14]",
            dungeon.getVertexPosition().toString());
  }

  /**
   * Test rows length to be greater than 3 and not negative values.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalRowsInput() {
    Random random = new Random(42);
    int start_point = 15;
    int end_point = 2;
    int currentLocation = 15;
    Treasure treasure = new Treasure(16, 40);
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = head.getNodePairs();
    List<List<Node>> leftOver = new ArrayList<>();
    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    Dungeon dungeon = new Dungeon(-4, 4, allVertexPosition, start_point,
            currentLocation, "NO", graph, "2");

  }

  /**
   * Test cols length to be greater than 3 and not negative values.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalColsInput() {
    Random random = new Random(42);
    int start_point = 15;
    int end_point = 2;
    int currentLocation = 15;
    Treasure treasure = new Treasure(16, 40);
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = head.getNodePairs();
    List<List<Node>> leftOver = new ArrayList<>();
    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    Dungeon dungeon = new Dungeon(4, -4, allVertexPosition, start_point,
            currentLocation, "NO", graph, "2");

  }

  /**
   * Test if the start point is similar to the current location at the start of the game.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalStartPointInput() {
    Random random = new Random(42);
    int start_point = 20;
    int end_point = 2;
    int currentLocation = 15;
    Treasure treasure = new Treasure(16, 40);
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = head.getNodePairs();
    List<List<Node>> leftOver = new ArrayList<>();
    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    Dungeon dungeon = new Dungeon(4, 4, allVertexPosition, start_point,
            currentLocation, "NO", graph, "2");

  }

  /**
   * Test if the end point is not present in the graph.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalEndPointInput() {
    Random random = new Random(42);
    int start_point = 15;
    int end_point = 20;
    int currentLocation = 15;
    Treasure treasure = new Treasure(16, 40);
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = head.getNodePairs();
    List<List<Node>> leftOver = new ArrayList<>();
    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    Dungeon dungeon = new Dungeon(4, 4, allVertexPosition, start_point,
            currentLocation, "NO", graph, "2");

  }

  /**
   * Test if the start point is similar to the current location at the start of the game.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalCurrentLocationInput() {
    Random random = new Random(42);
    int start_point = 15;
    int end_point = 2;
    int currentLocation = 1;
    Treasure treasure = new Treasure(16, 40);
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = head.getNodePairs();
    List<List<Node>> leftOver = new ArrayList<>();
    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    Dungeon dungeon = new Dungeon(4, 4, allVertexPosition, start_point,
            currentLocation, "NO", graph, "2");
  }

  /**
   * Test dungeon wrapping should not be null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testWrapIllegalValueAsNull() {
    Random random = new Random(42);
    int start_point = 15;
    int end_point = 2;
    int currentLocation = 15;
    Treasure treasure = new Treasure(16, 40);
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = head.getNodePairs();
    List<List<Node>> leftOver = new ArrayList<>();

    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    Dungeon dungeon = new Dungeon(4, 4, allVertexPosition, start_point,
            currentLocation, null, graph, "2");
  }

  /**
   * Test dungeon wrapping should be anything else than YES/NO.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testWrapIllegalValueOtherThanYesAndNo() {
    Random random = new Random(42);
    int start_point = 15;
    int end_point = 2;
    int currentLocation = 15;
    Treasure treasure = new Treasure(16, 40);
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = head.getNodePairs();
    List<List<Node>> leftOver = new ArrayList<>();

    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    Dungeon dungeon = new Dungeon(4, 4, allVertexPosition, start_point,
            currentLocation, "no", graph, "2");
  }

  /**
   * Test treasure should contain exactly the same number of treasures as expected
   * based on the treasure percentage.
   */
  @Test
  public void testPercentageInTreasure() {
    Random random = new Random(42);
    int start_point = 15;
    int end_point = 2;
    int currentLocation = 15;
    int[][] arr = Node.getArrayOfRowsAndCol(4, 4);
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = head.getNodePairs();
    List<List<Node>> leftOver = new ArrayList<>();
    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    Node[][] resultGraph = Node.setVertexType(graph);
    Treasure treasure = new Treasure(16, 40);
    List<String> treasureList = treasure.assignTreasure(graph, random);
    int count = 0;
    for (String i : treasureList) {
      if (i.equals("SAPPHIRES") || i.equals("DIAMOND") || i.equals("RUBIES")) {
        count++;
      }
    }
    assertEquals(6, count);
  }

  /**
   * Test the type of treasure with expected value.
   */
  @Test
  public void testTreasureType() {
    String t = TreasureType.DIAMONDS.getConstantVal();
    assertEquals("DIAMOND", t);
    String t1 = TreasureType.RUBIES.getConstantVal();
    assertEquals("RUBIES", t1);
  }

  /**
   * Test adjacency list cannot be null.
   */
  @Test
  public void testPrintShortestDistance() {
    try {
      int path = Dungeon.printShortestDistance(null, 15, 5, 17);
      fail("Adjacency list cannot be null!");
    } catch (NumberFormatException ignored) {

    } catch (IllegalArgumentException ignored) {

    }
  }

  /**
   * Test the vertex path cannot be null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testVertexList() {
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    Dungeon dungeon = new Dungeon(4, 4, new ArrayList<>(), 12,
            5,  "NO",graph, "2");
  }

  /**
   * Test invalid start point provided.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStartPointProvided() {
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<Node> allVertexPosition = new ArrayList<>();
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    Dungeon dungeon = new Dungeon(4, 4, allVertexPosition, 20,
            5, "NO",  graph,"2");
  }

  /**
   * Test invalid end point provided.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidEndPointProvided() {
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<Node> allVertexPosition = new ArrayList<>();
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    Dungeon dungeon = new Dungeon(4, 4, allVertexPosition, 12,
            20,  "NO", graph,"2");
  }

  /**
   * Test user enter invalid input when input was expected as something else.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCurrentLocationPassedIllegalValue() {
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<Node> allVertexPosition = new ArrayList<>();
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    allVertexPosition.add(new Node(2));
    List<Node> allPath = new ArrayList<>();
    allPath.add(new Node(11));
    allPath.add(new Node(8));
    Dungeon dungeon = new Dungeon(4, 4, allVertexPosition, 12,
            5,  "NO", graph,"2");
    int currentLocation = dungeon.validateUserInput(3, allPath);
  }

  /**
   * Test graph is null if no graph is created.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullGraph() {
    Node[][] resultGraph = new Node[2][2];
    String resultGraphWithDungeon = Node.showDungeon(resultGraph);
  }


}