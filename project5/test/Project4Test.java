

import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import controllerwithargs.GameControllerWithArgs;
import entry.Dungeon;
import entry.DungeonInterface;
import entry.DungeonPlayer;
import entry.Monster;
import entry.MonsterInterface;
import entry.Node;
import entry.Player;
import entry.RandomNumberGenerator;
import entry.Treasure;
import entry.TreasureInterface;
import entry.Weapon;
import entry.WeaponInterface;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Junit class to test both controller and model classes by running entire game afte passing input
 * through the controller.
 */
public class Project4Test {
  /**
   * Test if the otyugh is present in the end cave.
   */
  @Test
  public void testOtyughIsInEndCave() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    1,
                    1,
                    "NO",
                    graph,
                    "2");
    MonsterInterface monster = new Monster(Integer.parseInt("3"), rand);
    graph = dungeon.getGraph();
    monster.assignMonster(15, graph, 16, 12);
    System.out.println(monster.getMonsterLocation());
    assertTrue(monster.checkMonsterExist(15));
  }

  /**
   * Test if the otyugh is not present in the end cave.
   */
  @Test
  public void testOtyughIsNotInStartCave() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    1,
                    1,
                    "NO",
                    graph,
                    "2");
    MonsterInterface monster = new Monster(Integer.parseInt("3"), rand);
    graph = dungeon.getGraph();
    monster.assignMonster(15, graph, 16, 12);
    assertFalse(monster.checkMonsterExist(1));
    assertTrue(monster.checkNoMonster(1));
  }

  /**
   * Test if the otyugh count is equal to the provided count.
   */
  @Test
  public void testOtyughCountEqualToProvidedCount() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    1,
                    1,
                    "NO",
                    graph,
                    "2");
    MonsterInterface monster = new Monster(Integer.parseInt("3"), rand);
    graph = dungeon.getGraph();
    monster.assignMonster(15, graph, 16, 12);
    List<String> monsterList = monster.getMonsterLocation();
    int count = 0;
    for (String s : monsterList) {
      if (s.equals("MONSTER")) {
        count++;
      }
    }
    assertEquals(3, count);
  }

  /**
   * Test the otyugh is not present in the tunnel.
   */
  @Test
  public void testOtyughNotInTunnel() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    1,
                    1,
                    "NO",
                    graph,
                    "2");
    MonsterInterface monster = new Monster(Integer.parseInt("3"), rand);
    graph = dungeon.getGraph();
    monster.assignMonster(15, graph, 16, 12);
    List<String> monsterList = monster.getMonsterLocation();
    int count = 0;
    int[] tunnelArr = {1, 2, 3, 5, 6, 7, 8, 9};
    for (int i = 0; i < tunnelArr.length; i++) {
      assertTrue(monster.checkNoMonster(tunnelArr[i]));
    }
  }

  /**
   * Test the otyugh with the treasure at the position.
   */
  @Test
  public void testOtyughWithTreasure() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    1,
                    1,
                    "NO",
                    graph,
                    "2");
    MonsterInterface monster = new Monster(Integer.parseInt("3"), rand);
    graph = dungeon.getGraph();
    monster.assignMonster(15, graph, 16, 12);
    List<String> monsterList = monster.getMonsterLocation();
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    assertTrue(monster.checkMonsterExist(10));
    assertTrue(treasure.getTreasureAtPosition(10));
  }

  /**
   * Test the otyugh with the treasure at the position.
   */
  @Test
  public void testOtyughWithWeapon() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    1,
                    1,
                    "NO",
                    graph,
                    "2");
    MonsterInterface monster = new Monster(Integer.parseInt("3"), rand);
    graph = dungeon.getGraph();
    monster.assignMonster(15, graph, 16, 12);
    List<String> monsterList = monster.getMonsterLocation();
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    weapon.assignWeapon();
    assertTrue(weapon.checkWeapon(10));
    assertTrue(monster.checkMonsterExist(10));
  }

  /**
   * Test player detect less pungent smell.
   */
  @Test
  public void testPlayerDetectLessPungentSmell() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    1,
                    1,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    MonsterInterface monster = new Monster(Integer.parseInt("3"), rand);
    monster.assignMonster(end_point, graph, 16, start_point);
    String out = monster.checkMonster(5, 16, dungeon);
    assertEquals("Less pungent smell of a monster nearby!!", out);
  }

  /**
   * Test player detect more pungent smell.
   */
  @Test
  public void testPlayerDetectMorePungentSmell() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    1,
                    1,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    MonsterInterface monster = new Monster(Integer.parseInt("3"), rand);
    monster.assignMonster(end_point, graph, 16, start_point);
    String out = monster.checkMonster(6, 16, dungeon);
    assertEquals("More pungent smell of a monster nearby!!", out);
  }

  /**
   * Test player detect more pungent smell of multiple monsters.
   */
  @Test
  public void testPlayerDetectMorePungentSmellWithMultipleMonster() {
    Random rand = new Random(44);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);

    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    1,
                    1,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    MonsterInterface monster = new Monster(Integer.parseInt("3"), rand);
    monster.assignMonster(end_point, graph, 16, start_point);
    String out = monster.checkMonster(7, 16, dungeon);
    assertEquals("More pungent smell of multiple monster nearby!!", out);
  }

  /**
   * Test player detect no monster near the location.
   */
  @Test
  public void testPlayerDetectNoMonsterNearTheLocation() {
    Random rand = new Random(44);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);

    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    1,
                    1,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    MonsterInterface monster = new Monster(Integer.parseInt("3"), rand);
    monster.assignMonster(end_point, graph, 16, start_point);
    String out = monster.checkMonster(1, 16, dungeon);
    assertEquals("No monster near your location!!", out);
  }

  /**
   * Test player has three arrows in the start of the game.
   */
  @Test
  public void testPlayerArrowAsThree() {
    Random rand = new Random(42);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    assertEquals(3, dp.getNumArrows());
  }

  /**
   * Test player can find crooked arrow with the treasure.
   */
  @Test
  public void testPlayerCanFindCrookedArrowWithTreasure() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);

    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    treasure.assignTreasure(graph, rand);
    List<String> arr1 = new ArrayList<>();
    weapon.assignWeapon();
    for (Node[] nodes : resultGraph) {
      for (Node node : nodes) {
        arr1.add(node.getType());
      }
    }
    List<String> treasureList = treasure.getTreasureList();
    List<List<String>> weaponList = weapon.getWeaponList();
    assertEquals("SAPPHIRES", treasureList.get(3));
    assertEquals("Arrow", weaponList.get(3).get(0));
    assertEquals("C", arr1.get(3));
    assertEquals("DIAMOND", treasureList.get(9));
    assertEquals("C", arr1.get(9));
    assertEquals("Arrow", weaponList.get(9).get(0));
    assertEquals("DIAMOND", treasureList.get(13));
    assertEquals("C", arr1.get(13));
    assertEquals("Arrow", weaponList.get(13).get(0));
  }

  /**
   * Test arrow not always present with the treasure.
   */
  @Test
  public void testArrowNotAlwaysPresentWithTreasure() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);

    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    treasure.assignTreasure(graph, rand);
    weapon.assignWeapon();
    List<String> treasureList = treasure.getTreasureList();
    List<List<String>> weaponList = weapon.getWeaponList();
    assertEquals("T", treasureList.get(2));
    assertEquals("Arrow", weaponList.get(2).get(0));
    assertEquals("T", treasureList.get(4));
    assertEquals("Arrow", weaponList.get(4).get(0));
    assertEquals("T", treasureList.get(5));
    assertEquals("Arrow", weaponList.get(5).get(0));
  }

  /**
   * Test arrow is both in tunnel and cave.
   */
  @Test
  public void testArrowInBothTunnelAndCave() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);

    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    treasure.assignTreasure(graph, rand);
    List<String> arr1 = new ArrayList<>();
    weapon.assignWeapon();
    List<List<String>> weaponList = weapon.getWeaponList();
    assertEquals("Arrow", weaponList.get(2).get(0));
    assertEquals("T", Node.showNodeType(graph, 2));
    assertEquals("Arrow", weaponList.get(3).get(0));
    assertEquals("T", Node.showNodeType(graph, 3));
    assertEquals("Arrow", weaponList.get(4).get(0));
    assertEquals("C", Node.showNodeType(graph, 4));
    assertEquals("T", Node.showNodeType(graph, 9));
    assertEquals("Arrow", weaponList.get(9).get(0));
    assertEquals("C", Node.showNodeType(graph, 13));
    assertEquals("Arrow", weaponList.get(13).get(0));
  }

  /**
   * Test arrow count is same as treasure count.
   */
  @Test
  public void testArrowSameAsTreasure() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    treasure.assignTreasure(graph, rand);
    weapon.assignWeapon();
    List<String> treasureList = treasure.getTreasureList();
    List<List<String>> weaponList = weapon.getWeaponList();
    int weaponCount = 0;
    int treasureCount = 0;
    for (int i = 0; i < treasureList.size(); i++) {
      if (treasureList.get(i).equals("SAPPHIRES")
              || treasureList.get(i).equals("DIAMOND")
              || treasureList.get(i).equals("RUBIES")) {
        treasureCount++;
      }
    }
    for (int i = 0; i < weaponList.size(); i++) {
      if (weaponList.get(i).get(0).equals("Arrow")) {
        weaponCount++;
      }
    }
    assertEquals(treasureCount, weaponCount);
  }

  /**
   * Test player is able to shoot arrow and kill enemy.
   */
  @Test
  public void testPlayerShootArrow() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("M 1 M 1 M 2 M 1 S S 1 S S 1 M 2");
    new GameControllerWithArgs(in, out).playGame(dungeon, treasure, monster, dp, weapon);
    assertTrue(out.toString().contains("Enter the distance you want to shoot:"));
    assertTrue(out.toString().contains("Monster is hit!"));
    assertTrue(
            out.toString()
                    .contains(
                            "Monster location:[Tunnel, "
                                    + "Tunnel, Tunnel, MONSTER, Tunnel, Tunnel, Tunnel, Tunnel, "
                                    + "Tunnel, MONSTER DAMAGED, MONSTER, MONSTER, NO MONSTER, "
                                    + "NO MONSTER, NO MONSTER, NO MONSTER]"));
    assertTrue(out.toString().contains("Shoot an arrow! Enter the direction i.e N,S,E,W"));
    assertTrue(out.toString().contains("Enter the distance you want to shoot:"));
    assertTrue(out.toString().contains("Monster is hit!"));
    assertTrue(out.toString().contains("Monster is dead!"));
    assertTrue(out.toString().contains("Game over!!!"));
    assertTrue(out.toString().contains("You have reached to your end_point 10"));
  }

  /**
   * Test player is able to shoot arrow and kill enemy in a wrapping dungeon.
   */
  @Test
  public void testPlayerShootArrowWorksInWrapping() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "YES",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("M 1 M 1 M 2 M 1 S S 1 S S 1 M 2");
    new GameControllerWithArgs(in, out).playGame(dungeon, treasure, monster, dp, weapon);
    assertTrue(out.toString().contains("Enter the distance you want to shoot:"));
    assertTrue(out.toString().contains("Monster is hit!"));
    assertTrue(out.toString().contains("MMonster is still alive but injured!"));
    assertTrue(out.toString().contains("Shoot an arrow! Enter the direction i.e N,S,E,W"));
    assertTrue(out.toString().contains("Enter the distance you want to shoot:"));
    assertTrue(out.toString().contains("Monster is hit!"));
    assertTrue(out.toString().contains("Monster is dead!"));
    assertTrue(out.toString().contains("Game over!!!"));
    assertTrue(out.toString().contains("You have reached to your end_point 10"));
  }

  /**
   * Test the arrow will travel freely in the tunnel.
   */
  @Test
  public void testArrowTravelFreelyInTunnel() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("M 1 M 1 M 2 S E 1 M 1 M 2");
    assertEquals("T", Node.showNodeType(graph, 6));
    assertEquals("C", Node.showNodeType(graph, 10));
    new GameControllerWithArgs(in, out).playGame(dungeon, treasure, monster, dp, weapon);
    assertEquals(
            "Welcome to the dungeon!\n"
                    + "\n"
                    + "Player Info:\n"
                    + "Name='SHHVG'\n"
                    + "Character='The mighty king born  to be a leader'\n"
                    + "Rows entered: 4\n"
                    + "Columns entered: 4\n"
                    + "Dungeon Wrapping: NO\n"
                    + "Start Point: 3\n"
                    + "End Point: 10\n"
                    + "Connectivity: 2\n"
                    + "Treasure: Treasure Percentage=40.0\n"
                    + "Treasure List: [T, T, T, RUBIES, T, T, T, T, T, RUBIES, RUBIES, DIAMOND,"
                    + " RUBIES, DIAMOND, NT, NT]\n"
                    + "Monster: Monster Count:4\n"
                    + "Monster position: [Tunnel, Tunnel, Tunnel, MONSTER, Tunnel, Tunnel,"
                    + " Tunnel, Tunnel, Tunnel, MONSTER, MONSTER, MONSTER, NO MONSTER, "
                    + "NO MONSTER, NO MONSTER, NO MONSTER]\n"
                    + "Weapon List: [[Arrow], [Arrow], [NULL], [NULL], [Arrow], [NULL],"
                    + " [NULL], [NULL], [NULL], [Arrow], [Arrow], [NULL], [Arrow], "
                    + "[NULL], [NULL], [NULL]]\n"
                    + "Current Location is: 3\n"
                    + "Less pungent smell of a monster nearby!!\n"
                    + "Move, Pickup, or Shoot (M-P-S)? \n"
                    + "\n"
                    + "1. Possible Path to: 2 in direction: W\n"
                    + "2. Possible Path to: 7 in direction: S\n"
                    + "Where do you want to move? Type any digit between 1 and 2\n"
                    + "Current Location is: 2\n"
                    + "No monster near your location!!\n"
                    + "Move, Pickup, or Shoot (M-P-S)? \n"
                    + "\n"
                    + "1. Possible Path to: 1 in direction: W\n"
                    + "2. Possible Path to: 3 in direction: E\n"
                    + "Where do you want to move? Type any digit between 1 and 2\n"
                    + "Current Location is: 1\n"
                    + "No monster near your location!!\n"
                    + "Move, Pickup, or Shoot (M-P-S)? \n"
                    + "\n"
                    + "1. Possible Path to: 2 in direction: E\n"
                    + "2. Possible Path to: 5 in direction: S\n"
                    + "Where do you want to move? Type any digit between 1 and 2\n"
                    + "Current Location is: 5\n"
                    + "Less pungent smell of a monster nearby!!\n"
                    + "Move, Pickup, or Shoot (M-P-S)? \n"
                    + "\n"
                    + "1. Possible directions to shoot:E\n"
                    + "2. Possible directions to shoot:N\n"
                    + "Shoot an arrow! Enter the direction i.e N,S,E,W\n"
                    + "\n"
                    + "Enter the distance you want to shoot: \n"
                    + "\n"
                    + "Arrow Location:10\n"
                    + "Monster is hit!\n"
                    + "\n"
                    + "Monster is still alive but injured!\n"
                    + "\n"
                    + "Total arrows present:2\n"
                    + "weapon list:[[Arrow], [Arrow], [NULL], [NULL], [Arrow], [NULL],"
                    + " [NULL], [NULL], [NULL], [Arrow, Arrow], [Arrow], [NULL], [Arrow],"
                    + " [NULL], [NULL], [NULL]]\n"
                    + "Monster location:[Tunnel, Tunnel, Tunnel, MONSTER, Tunnel, Tunnel,"
                    + " Tunnel, Tunnel, Tunnel, MONSTER DAMAGED, MONSTER, MONSTER, "
                    + "NO MONSTER, NO MONSTER, NO MONSTER, NO MONSTER]\n"
                    + "Current Location is: 5\n"
                    + "More pungent smell of a monster nearby!!\n"
                    + "Move, Pickup, or Shoot (M-P-S)? \n"
                    + "\n"
                    + "1. Possible Path to: 6 in direction: E\n"
                    + "2. Possible Path to: 1 in direction: N\n"
                    + "Where do you want to move? Type any digit between 1 and 2\n"
                    + "Current Location is: 6\n"
                    + "More pungent smell of a monster nearby!!\n"
                    + "Move, Pickup, or Shoot (M-P-S)? \n"
                    + "\n"
                    + "1. Possible Path to: 5 in direction: W\n"
                    + "2. Possible Path to: 10 in direction: S\n"
                    + "Where do you want to move? Type any digit between 1 and 2\n"
                    + "You have encountered a monster!\n"
                    + "Chomp, chomp, chomp, you are eaten by an Otyugh!\n",
            out.toString());
  }

  /**
   * Test the arrow travel straight in the cave.
   */
  @Test
  public void testArrowTravelInStraightCave() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    assertEquals("T", Node.showNodeType(graph, 7));
    assertEquals("C", Node.showNodeType(graph, 11));
    assertEquals("C", Node.showNodeType(graph, 15));
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("S S 1 S S 2 M 1 M 1 M 2 M 1 M 2");
    new GameControllerWithArgs(in, out).playGame(dungeon, treasure, monster, dp, weapon);
    assertTrue(out.toString().contains("Current Location is: 3"));
    assertTrue(out.toString().contains("Enter the distance you want to shoot:"));
    assertTrue(out.toString().contains("Arrow Location:11"));
    assertTrue(out.toString().contains("Monster is hit!"));
    assertEquals("C", Node.showNodeType(graph, 11));
    assertTrue(out.toString().contains("Enter the distance you want to shoot: "));
    assertTrue(out.toString().contains("Arrow Location:15"));
    assertTrue(out.toString().contains("You wasted your arrow!!"));
    System.out.println(out.toString());
  }

  /**
   * Test arrow is stopped at the end of the cave.
   */
  @Test
  public void testArrowIsStoppedAtTheEndOfTheCave() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("S S 1 S S 3 M 1 M 1 M 2 M 1 M 2");
    new GameControllerWithArgs(in, out).playGame(dungeon, treasure, monster, dp, weapon);
    assertTrue(out.toString().contains("Enter the distance you want to shoot:"));
    assertTrue(out.toString().contains("Arrow Location:11"));
    assertTrue(out.toString().contains("Monster is hit!"));
    assertEquals("C", Node.showNodeType(graph, 11));
    assertTrue(out.toString().contains("Enter the distance you want to shoot: "));
    assertTrue(out.toString().contains("Arrow Location:0"));
    assertTrue(out.toString().contains("Arrow missed!"));
  }

  /**
   * Test player hit the otyugh and the monster is killed.
   */
  @Test
  public void testPlayerHitOtyugh() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    monster.assignMonster(end_point, graph, 16, start_point);
    assertEquals("MONSTER", monster.getMonsterLocation().get(10));
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("S S 1 S S 1 M 1 M 1 M 2 M 1 M 2");
    new GameControllerWithArgs(in, out).playGame(dungeon, treasure, monster, dp, weapon);
    assertTrue(out.toString().contains("Enter the distance you want to shoot:"));
    assertTrue(out.toString().contains("Arrow Location:11"));
    assertTrue(out.toString().contains("Monster is hit!"));
    assertTrue(out.toString().contains("Monster is still alive but injured!"));
    assertTrue(out.toString().contains("Shoot an arrow! Enter the direction i.e N,S,E,W"));
    assertTrue(out.toString().contains("Enter the distance you want to shoot: "));
    assertTrue(out.toString().contains("Arrow Location:11"));
    assertTrue(out.toString().contains("Monster is hit!"));
    assertTrue(out.toString().contains("Monster is dead!"));
    assertEquals("C", Node.showNodeType(graph, 11));
    assertEquals("NO MONSTER", monster.getMonsterLocation().get(10));
  }

  /**
   * Test player misses the otyugh and the arrow is wasted.
   */
  @Test
  public void testPlayerMissOtyugh() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    monster.assignMonster(end_point, graph, 16, start_point);
    assertEquals("MONSTER", monster.getMonsterLocation().get(10));
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("S S 2 S S 2 M 1 M 1 M 2 M 1 M 2");
    new GameControllerWithArgs(in, out).playGame(dungeon, treasure, monster, dp, weapon);
    assertTrue(out.toString().contains("Current Location is: 3"));
    assertTrue(out.toString().contains("Enter the distance you want to shoot:"));
    assertTrue(out.toString().contains("Arrow Location:15"));
    assertTrue(out.toString().contains("You wasted your arrow!!"));
    assertTrue(out.toString().contains("Shoot an arrow! Enter the direction i.e N,S,E,W"));
    assertTrue(out.toString().contains("Enter the distance you want to shoot: "));
    assertTrue(out.toString().contains("Arrow Location:15"));
    assertTrue(out.toString().contains("You wasted your arrow!!"));
    assertEquals("C", Node.showNodeType(graph, 11));
    assertEquals("MONSTER", monster.getMonsterLocation().get(10));
    assertEquals("[Arrow, Arrow]", String.valueOf(weapon.getWeaponList().get(14)));
    System.out.println(out.toString());
  }

  /**
   * Test the otyugh died after two hits.
   */
  @Test
  public void testOtyughDieAfterTwoHit() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    monster.assignMonster(end_point, graph, 16, start_point);
    assertEquals("MONSTER", monster.getMonsterLocation().get(10));
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("S S 1 S S 1 M 1 M 1 M 2 M 1 M 2");
    new GameControllerWithArgs(in, out).playGame(dungeon, treasure, monster, dp, weapon);
    assertTrue(
            out.toString()
                    .contains(
                            "Weapon List: [[Arrow], [Arrow], [NULL], [NULL], "
                                    + "[Arrow], [NULL], [NULL], [NULL], "
                                    + "[NULL], [Arrow], [Arrow], [NULL],"
                                    + " [Arrow], [NULL], [NULL], [NULL]]"));
    assertTrue(out.toString().contains("Enter the distance you want to shoot:"));
    assertTrue(out.toString().contains("Arrow Location:11"));
    assertTrue(out.toString().contains("Monster is hit!"));
    assertTrue(out.toString().contains("Monster is still alive but injured!"));
    assertTrue(out.toString().contains("Shoot an arrow! Enter the direction i.e N,S,E,W"));
    assertTrue(out.toString().contains("Enter the distance you want to shoot: "));
    assertTrue(out.toString().contains("Arrow Location:11"));
    assertTrue(out.toString().contains("Monster is hit!"));
    assertTrue(out.toString().contains("Monster is dead!"));
    assertEquals("C", Node.showNodeType(graph, 11));
    assertEquals("NO MONSTER", monster.getMonsterLocation().get(10));
    assertEquals("[Arrow, Arrow, Arrow]", String.valueOf(weapon.getWeaponList().get(10)));
  }

  /**
   * Test player probability when player enter with a node having damaged monster.
   */
  @Test
  public void testPlayerProbIs50PercentAndPlayerDied() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    monster.assignMonster(end_point, graph, 16, start_point);
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("M 1 M 1 M 2 M 1 S S 1 M 2");
    new GameControllerWithArgs(in, out).playGame(dungeon, treasure, monster, dp, weapon);
    assertTrue(out.toString().contains("Enter the distance you want to shoot:"));
    assertTrue(out.toString().contains("Arrow Location:10"));
    assertTrue(out.toString().contains("Monster is hit!"));
    assertTrue(out.toString().contains("Monster is still alive but injured!"));
    assertTrue(out.toString().contains("Current Location is: 6"));
    assertEquals("MONSTER DAMAGED", monster.getMonsterLocation().get(9));
    assertTrue(out.toString().contains("You have encountered a monster!"));
    assertTrue(out.toString().contains("Chomp, chomp, chomp, you are eaten by an Otyugh!"));
  }

  /**
   * Test player probability when player enter with a node having damaged monster.
   */
  @Test
  public void testPlayerProbIs50PercentAndPlayerIsLucky() {
    Random rand = new Random(11);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    monster.assignMonster(end_point, graph, 16, start_point);
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("M 1 M 2 M 1 S N 1 M 2 M 3 M 1");
    new GameControllerWithArgs(in, out).playGame(dungeon, treasure, monster, dp, weapon);
    assertTrue(out.toString().contains("Enter the distance you want to shoot:"));
    assertTrue(out.toString().contains("Arrow Location:6"));
    assertTrue(out.toString().contains("Monster is hit!"));
    assertTrue(out.toString().contains("Monster is still alive but injured!"));
    assertTrue(out.toString().contains("More pungent smell of a monster nearby!!"));
    assertTrue(out.toString().contains("Current Location is: 6"));
    assertTrue(out.toString().contains("You have encountered a monster!"));
    assertTrue(out.toString().contains("Luck is in your pocket!! Monster won't kill you"));
    assertTrue(out.toString().contains("Current Location is: 2"));
    assertFalse(monster.checkMonsterDead(6));
    assertEquals("MONSTER DAMAGED", monster.getMonsterLocation().get(5));
  }

  /**
   * Test player is able to collect both treasure and weapon.
   */
  @Test
  public void testPlayerIsAbleToCollectTreasureAndArrow() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    monster.assignMonster(end_point, graph, 16, start_point);
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("S S 1 S S 1 M 2 M 2 P M 1 M 1 M 1 M 2 M 1 S S 1 M 2");
    new GameControllerWithArgs(in, out).playGame(dungeon, treasure, monster, dp, weapon);
    assertTrue(out.toString().contains("Current Location is: 3"));
    assertTrue(out.toString().contains("Enter the distance you want to shoot:"));
    assertTrue(out.toString().contains("Arrow Location:11"));
    assertTrue(out.toString().contains("Monster is hit!"));
    assertTrue(out.toString().contains("Monster is still alive but injured!"));
    assertTrue(out.toString().contains("Arrow Location:11"));
    assertTrue(out.toString().contains("Monster is hit!"));
    assertTrue(out.toString().contains("Monster is dead!"));
    assertTrue(out.toString().contains("Current Location is: 7"));
    assertTrue(out.toString().contains("Current Location is: 11"));
    assertTrue(out.toString().contains("You picked up an arrow!"));
    assertTrue(out.toString().contains("You picked up a treasure!"));
    assertTrue(out.toString().contains("Total arrows present:4"));
    assertTrue(out.toString().contains("Total treasure: [RUBIES]"));
    assertTrue(out.toString().contains("Current Location is: 11"));
    assertTrue(out.toString().contains("You managed to get these treasures:  [RUBIES]"));
  }

  /**
   * Test player arrows are updated when player collects arrows.
   */
  @Test
  public void testPlayerArrowUpdated() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    monster.assignMonster(end_point, graph, 16, start_point);
    StringBuffer out = new StringBuffer();
    assertEquals(3, dp.getNumArrows());
    Readable in = new StringReader("S S 1 S S 1 M 2 M 2 P M 1 M 1 M 1 M 2 M 1 S S 1 M 2");
    new GameControllerWithArgs(in, out).playGame(dungeon, treasure, monster, dp, weapon);
    assertTrue(out.toString().contains("Current Location is: 3"));
    assertTrue(out.toString().contains("Shoot an arrow! Enter the direction i.e N,S,E,W"));
    assertTrue(out.toString().contains("Enter the distance you want to shoot: "));
    assertTrue(out.toString().contains("Monster is still alive but injured!"));
    assertTrue(out.toString().contains("Total arrows present:2"));
    assertTrue(out.toString().contains("Shoot an arrow! Enter the direction i.e N,S,E,W"));
    assertTrue(out.toString().contains("Enter the distance you want to shoot: "));
    assertTrue(out.toString().contains("Monster is hit!"));
    assertTrue(out.toString().contains("Monster is dead!"));
    assertTrue(out.toString().contains("Total arrows present:1"));
    assertTrue(out.toString().contains("Current Location is: 11"));
    assertTrue(out.toString().contains("You picked up an arrow!"));
    assertTrue(out.toString().contains("You picked up a treasure!"));
    assertTrue(out.toString().contains("Total arrows present:4"));
    assertEquals(4, dp.getNumArrows());
  }

  /**
   * Test player pick arrow from the node and it is set back to NULL.
   */
  @Test
  public void testPlayerPicKTreasure() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    monster.assignMonster(end_point, graph, 16, start_point);
    StringBuffer out = new StringBuffer();
    assertEquals(3, dp.getNumArrows());
    assertEquals("[]", String.valueOf(dp.getPlayerTreasure()));
    Readable in = new StringReader("S S 1 S S 1 M 2 M 2 P M 1 M 1 M 1 M 2 M 1 S S 1 M 2");
    new GameControllerWithArgs(in, out).playGame(dungeon, treasure, monster, dp, weapon);
    assertTrue(out.toString().contains("Current Location is: 3"));
    assertTrue(out.toString().contains("You picked up a treasure!"));
    assertTrue(out.toString().contains("Total treasure: [RUBIES]"));
    assertTrue(out.toString().contains("You managed to get these treasures:  [RUBIES]"));
    assertEquals(4, dp.getNumArrows());
    assertEquals("[RUBIES]", String.valueOf(dp.getPlayerTreasure()));
  }

  /**
   * Test player is dead after killed by monster.
   */
  @Test
  public void testPlayerIsDeadAfterEatenByOtyugh() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    monster.assignMonster(end_point, graph, 16, start_point);
    StringBuffer out = new StringBuffer();
    assertTrue(dp.isAlive());
    Readable in = new StringReader("M 1 M 1 M 2 M 1 S S 1 M 2");
    new GameControllerWithArgs(in, out).playGame(dungeon, treasure, monster, dp, weapon);
    assertTrue(out.toString().contains("Enter the distance you want to shoot:"));
    assertTrue(out.toString().contains("Arrow Location:10"));
    assertTrue(out.toString().contains("Monster is hit!"));
    assertTrue(out.toString().contains("Monster is still alive but injured!"));
    assertTrue(out.toString().contains("Current Location is: 6"));
    assertEquals("MONSTER DAMAGED", monster.getMonsterLocation().get(9));
    assertTrue(out.toString().contains("You have encountered a monster!"));
    assertTrue(out.toString().contains("Chomp, chomp, chomp, you are eaten by an Otyugh!"));
    assertFalse(dp.isAlive());
  }

  /**
   * Test controller take valid input for and handle invalid input except M,P,S.
   */
  @Test
  public void testControllerTakeValidInput() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    monster.assignMonster(end_point, graph, 16, start_point);
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("K M 1 M 1 M 2 M 1 S S 1 M 2");
    new GameControllerWithArgs(in, out).playGame(dungeon, treasure, monster, dp, weapon);
    assertTrue(out.toString().contains("Invalid input, please try again!"));
    assertTrue(out.toString().contains("Enter the distance you want to shoot:"));
    assertTrue(out.toString().contains("Arrow Location:10"));
    assertTrue(out.toString().contains("Monster is hit!"));
    assertTrue(out.toString().contains("Monster is still alive but injured!"));
    assertTrue(out.toString().contains("Current Location is: 6"));
    assertEquals("MONSTER DAMAGED", monster.getMonsterLocation().get(9));
    assertTrue(out.toString().contains("You have encountered a monster!"));
    assertTrue(out.toString().contains("Chomp, chomp, chomp, you are eaten by an Otyugh!"));
  }

  /**
   * Test controller handle invalid input for move.
   */
  @Test
  public void testControllerTakeValidInputAfterPassingInvalidMoveInput() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    monster.assignMonster(end_point, graph, 16, start_point);
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("M 3 1 M 1 M 2 M 1 S S 1 M 2");
    new GameControllerWithArgs(in, out).playGame(dungeon, treasure, monster, dp, weapon);
    assertTrue(
            out.toString().contains("Where do you want to move? Type any"
                    + " digit between 1 and 2"));
    assertTrue(out.toString().contains("Invalid input, try again:"));
    assertTrue(out.toString().contains("Arrow Location:10"));
    assertTrue(out.toString().contains("Monster is hit!"));
    assertTrue(out.toString().contains("Monster is still alive but injured!"));
    assertTrue(out.toString().contains("Current Location is: 6"));
    assertEquals("MONSTER DAMAGED", monster.getMonsterLocation().get(9));
    assertTrue(out.toString().contains("You have encountered a monster!"));
    assertTrue(out.toString().contains("Chomp, chomp, chomp, you are eaten by an Otyugh!"));
  }

  /**
   * Test the controller handle input after passing the invalid direction.
   */
  @Test
  public void testControllerTakeValidInputAfterPassingInvalidDirection() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    monster.assignMonster(end_point, graph, 16, start_point);
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("M 1 M 1 M 2 M 1 S E S INVALID_DISTANCE 1 M 2");
    new GameControllerWithArgs(in, out).playGame(dungeon, treasure, monster, dp, weapon);
    assertTrue(out.toString().contains("Please enter a valid direction"));
    assertTrue(out.toString().contains("Invalid distance! Please enter a valid distance"));
    assertTrue(out.toString().contains("Arrow Location:10"));
    assertTrue(out.toString().contains("Monster is hit!"));
    assertTrue(out.toString().contains("Monster is still alive but injured!"));
    assertTrue(out.toString().contains("Current Location is: 6"));
    assertEquals("MONSTER DAMAGED", monster.getMonsterLocation().get(9));
    assertTrue(out.toString().contains("You have encountered a monster!"));
    assertTrue(out.toString().contains("Chomp, chomp, chomp, you are eaten by an Otyugh!"));
  }

  /**
   * Test player arrows are exhausted and play can't shoot arrow anymore.
   */
  @Test
  public void testPlayerArrowsAreExhausted() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    monster.assignMonster(end_point, graph, 16, start_point);
    StringBuffer out = new StringBuffer();
    assertEquals(3, dp.getNumArrows());
    Readable in = new StringReader("M 1 M 1 M 2 M 1 S S 1 S S 1 S S 1 S S 1 M 2");
    new GameControllerWithArgs(in, out).playGame(dungeon, treasure, monster, dp, weapon);

    assertTrue(out.toString().contains("Arrow Location:10"));
    assertTrue(out.toString().contains("Monster is hit!"));
    assertTrue(out.toString().contains("Monster is still alive but injured!"));
    assertTrue(out.toString().contains("Total arrows present:2"));
    assertTrue(out.toString().contains("Current Location is: 6"));
    assertTrue(out.toString().contains("Arrow Location:10"));
    assertTrue(out.toString().contains("Monster is hit!"));
    assertTrue(out.toString().contains("Monster is dead!"));
    assertTrue(out.toString().contains("Total arrows present:1"));
    assertTrue(out.toString().contains("Current Location is: 6"));
    assertTrue(out.toString().contains("Arrow Location:10"));
    assertTrue(out.toString().contains("You wasted your arrow!!"));
    assertTrue(out.toString().contains("Total arrows present:0"));
    assertTrue(out.toString().contains("You don't have any arrows left!"));
    assertTrue(out.toString().contains("Invalid input, please try again!"));
    assertTrue(out.toString().contains("Game over!!!"));
    assertTrue(out.toString().contains("You have reached to your end_point 10"));
    assertEquals("NO MONSTER", monster.getMonsterLocation().get(9));
  }

  /**
   * Test the player reaches the end point of the game.
   */
  @Test
  public void testPlayerReachesEndPoint() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("M 1 M 1 M 2 S E 1 S E 1 M 1 M 2");
    new GameControllerWithArgs(in, out).playGame(dungeon, treasure, monster, dp, weapon);
    assertTrue(out.toString().contains("Start Point: 3"));
    assertTrue(out.toString().contains("End Point: 10"));
    assertTrue(out.toString().contains("Move, Pickup, or Shoot (M-P-S)? "));
    assertTrue(out.toString().contains("Current Location is: 1"));
    assertTrue(out.toString().contains("No monster near your location!!"));
    assertTrue(out.toString().contains("Current Location is: 5"));
    assertTrue(out.toString().contains("Less pungent smell of a monster nearby!!"));
    assertTrue(out.toString().contains("Arrow Location:10"));
    assertTrue(out.toString().contains("Monster is still alive but injured!"));
    assertTrue(out.toString().contains("Monster is dead!"));
    assertTrue(out.toString().contains("Current Location is: 6"));
    assertTrue(out.toString().contains("Game over!!!"));
    assertTrue(out.toString().contains("You have reached to your end_point 10"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDungeonModelIsNotInvalid() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("M 1 M 1 M 2 S E 1 S E 1 M 1 M 2");
    new GameControllerWithArgs(in, out).playGame(null, treasure, monster, dp, weapon);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTreasureModelIsNotInvalid() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("M 1 M 1 M 2 S E 1 S E 1 M 1 M 2");
    new GameControllerWithArgs(in, out).playGame(dungeon, null, monster, dp, weapon);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMonsterModelIsNotInvalid() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("M 1 M 1 M 2 S E 1 S E 1 M 1 M 2");
    new GameControllerWithArgs(in, out).playGame(dungeon, treasure, null, dp, weapon);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayerModelIsNotInvalid() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("M 1 M 1 M 2 S E 1 S E 1 M 1 M 2");
    new GameControllerWithArgs(in, out).playGame(dungeon, treasure, monster, null, weapon);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWeaponModelIsNotInvalid() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("M 1 M 1 M 2 S E 1 S E 1 M 1 M 2");
    new GameControllerWithArgs(in, out).playGame(dungeon, treasure, monster, dp, null);
  }

  /**
   * Test all possible path from one node were printed while asking user input to move in any
   * possible direction.
   */
  @Test
  public void testPossiblePathToFromOneNode() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("M 1 M 1 M 2 S E 1 S E 1 M 1 M 2");
    new GameControllerWithArgs(in, out).playGame(dungeon, treasure, monster, dp, weapon);
    assertTrue(out.toString().contains("Current Location is: 5"));
    assertTrue(out.toString().contains("Move, Pickup, or Shoot (M-P-S)? "));
    assertTrue(out.toString().contains("1. Possible directions to shoot:E"));
    assertTrue(out.toString().contains("2. Possible directions to shoot:N"));

    for (Node[] node : graph) {
      for (Node n : node) {
        if (n.getVal() == 5) {
          assertTrue(n.getRight() != null);
          assertTrue(n.getTop() != null);
        }
      }
    }
  }

  /**
   * Test controller handle no such element exception when passed few values.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDungeonHandleNoSuchElementException() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"),
            Integer.parseInt("4"));
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    start_point,
                    start_point,
                    "NO",
                    graph,
                    "2");
    int end_point =
            dungeon.randomEndPoint(
                    allVertexPosition,
                    rand,
                    start_point,
                    resultGraph,
                    Integer.parseInt("4"),
                    Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    MonsterInterface monster = new Monster(Integer.parseInt("4"), rand);
    List<String> treasureList = treasure.assignTreasure(graph, rand);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    Player dp =
            new DungeonPlayer(
                    RandomNumberGenerator.getRandomName(rand),
                    RandomNumberGenerator.getRandomCharacter(rand));
    StringBuffer out = new StringBuffer();
    Readable in = new StringReader("M 1 M 1 ");
    new GameControllerWithArgs(in, out).playGame(dungeon, treasure, monster, dp, weapon);
  }
}
