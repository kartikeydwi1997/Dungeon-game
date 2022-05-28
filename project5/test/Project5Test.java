import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import controller.GameController;
import controller.GameInterface;
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
import viewnew.DungeonView;
import viewnew.MockView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test the view,controller, and model of the game by creating a mock view and
 * testing all controller methods.
 */
public class Project5Test {

  /**
   * Test for the move method.
   */
  @Test
  public void testMoveGame() {
    Random random = new Random(44);
    Player dp = new DungeonPlayer(RandomNumberGenerator.getRandomName(random),
            RandomNumberGenerator.getRandomCharacter(random));
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(random, head.getNodePairs(), clouds, new ArrayList<>());
    int start_point = Dungeon.randomStartPoint(random, Integer.parseInt("4"),
            Integer.parseInt("4"));
    System.out.println(start_point);
    Node[][] resultGraph = Node.setVertexType(graph);
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    12,
                    12,
                    "NO",
                    graph,
                    "3");
    int end_point = dungeon.randomEndPoint(allVertexPosition, random, start_point, resultGraph,
            Integer.parseInt("4"), Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), random);
    List<String> treasureList = treasure.assignTreasure(graph, random);
    MonsterInterface monster = new Monster(Integer.parseInt("3"), random);
    graph = dungeon.getGraph();
    monster.assignMonster(1, graph, 16, 12);
    DungeonView view = null;
    try {
      view = new MockView();
    } catch (IOException e) {
      e.printStackTrace();
    }
    GameInterface controller = new GameController(dungeon, treasure, monster, dp, weapon, view);
    view.addClickListener(controller);
    view.addKeyListener(controller);
    try {
      controller.playGame();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      controller.move("up");
      assertEquals(dungeon.getCurrentPoint(), 8);
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(dungeon.getCurrentPoint());

    try {
      controller.move("left");
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(dungeon.getCurrentPoint(), 7);
    try {
      controller.move("left");
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(dungeon.getCurrentPoint(), 6);
    assertFalse(dp.isAlive());
  }

  /**
   * Test for the pick method.
   */
  @Test
  public void pick() {
    Random random = new Random(44);
    Player dp = new DungeonPlayer(RandomNumberGenerator.getRandomName(random),
            RandomNumberGenerator.getRandomCharacter(random));
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(random, head.getNodePairs(), clouds, new ArrayList<>());
    int start_point = Dungeon.randomStartPoint(random, Integer.parseInt("4"),
            Integer.parseInt("4"));
    System.out.println(start_point);
    Node[][] resultGraph = Node.setVertexType(graph);
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    12,
                    12,
                    "NO",
                    graph,
                    "3");
    int end_point = dungeon.randomEndPoint(allVertexPosition, random, start_point, resultGraph,
            Integer.parseInt("4"), Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), random);
    List<String> treasureList = treasure.assignTreasure(graph, random);
    MonsterInterface monster = new Monster(Integer.parseInt("3"), random);
    graph = dungeon.getGraph();
    monster.assignMonster(1, graph, 16, 12);
    DungeonView view = null;
    try {
      view = new MockView();
    } catch (IOException e) {
      e.printStackTrace();
    }
    GameInterface controller = new GameController(dungeon, treasure, monster, dp, weapon, view);
    view.addClickListener(controller);
    view.addKeyListener(controller);
    try {
      controller.playGame();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      controller.move("up");
      assertEquals(dungeon.getCurrentPoint(), 8);
    } catch (IOException e) {
      e.printStackTrace();
    }
    controller.pickup();
    assertEquals(dp.getNumArrows(), 4);

    assertEquals(dp.getPlayerTreasure().toString(), "[SAPPHIRES]");
  }

  /**
   * Test the shoot method.
   */
  @Test
  public void testShoot() {
    Random random = new Random(44);
    Player dp = new DungeonPlayer(RandomNumberGenerator.getRandomName(random),
            RandomNumberGenerator.getRandomCharacter(random));
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(random, head.getNodePairs(), clouds, new ArrayList<>());
    int start_point = Dungeon.randomStartPoint(random, Integer.parseInt("4"),
            Integer.parseInt("4"));
    System.out.println(start_point);
    Node[][] resultGraph = Node.setVertexType(graph);
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    12,
                    12,
                    "NO",
                    graph,
                    "3");
    int end_point = dungeon.randomEndPoint(allVertexPosition, random, start_point, resultGraph,
            Integer.parseInt("4"), Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), random);
    List<String> treasureList = treasure.assignTreasure(graph, random);
    MonsterInterface monster = new Monster(Integer.parseInt("3"), random);
    graph = dungeon.getGraph();
    monster.assignMonster(1, graph, 16, 12);
    DungeonView view = null;
    try {
      view = new MockView();
    } catch (IOException e) {
      e.printStackTrace();
    }
    GameInterface controller = new GameController(dungeon, treasure, monster, dp, weapon, view);
    view.addClickListener(controller);
    view.addKeyListener(controller);
    try {
      controller.playGame();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(monster.getMonsterLocation().get(3), "MONSTER");
    controller.shoot("up", 2);
    assertTrue(weapon.checkWeapon(4));
    assertEquals(monster.getMonsterLocation().get(3), "MONSTER DAMAGED");
    controller.shoot("up", 2);
    assertTrue(weapon.checkWeapon(4));
    assertEquals(weapon.getWeaponList().get(3).toString(), "[Arrow, Arrow]");
    assertEquals(monster.getMonsterLocation().get(3), "NO MONSTER");
  }

  /**
   * Test to check the condition when the player encounter a moving monster and
   * player fight with the monster than their values are updated after each test.
   */
  @Test
  public void testMovingMonster() {
    Random random = new Random(44);
    Player dp = new DungeonPlayer(RandomNumberGenerator.getRandomName(random),
            RandomNumberGenerator.getRandomCharacter(random));
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(random, head.getNodePairs(), clouds, new ArrayList<>());
    int start_point = Dungeon.randomStartPoint(random, Integer.parseInt("4"),
            Integer.parseInt("4"));
    System.out.println(start_point);
    Node[][] resultGraph = Node.setVertexType(graph);
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    12,
                    12,
                    "NO",
                    graph,
                    "3");
    int end_point = dungeon.randomEndPoint(allVertexPosition, random, start_point, resultGraph,
            Integer.parseInt("4"), Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), random);
    List<String> treasureList = treasure.assignTreasure(graph, random);
    MonsterInterface monster = new Monster(Integer.parseInt("3"), random);
    graph = dungeon.getGraph();
    monster.assignMonster(1, graph, 16, 12);
    DungeonView view = null;
    try {
      view = new MockView();
    } catch (IOException e) {
      e.printStackTrace();
    }
    GameInterface controller = new GameController(dungeon, treasure, monster, dp, weapon, view);
    view.addClickListener(controller);
    view.addKeyListener(controller);
    try {
      controller.playGame();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {

      controller.move("up");
      monster.setMovingMonsterPosition(8);
    } catch (IOException e) {
      e.printStackTrace();
    }
    controller.hitMonster();
    assertEquals(90, dp.getPlayerHealth());
    assertEquals(80, monster.getMovingMonsterHealth());

  }

  /**
   * Test the mouse click method works as expected.
   */
  @Test
  public void testMouseMoveClick() {
    Random random = new Random(44);
    Player dp = new DungeonPlayer(RandomNumberGenerator.getRandomName(random),
            RandomNumberGenerator.getRandomCharacter(random));
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(random, head.getNodePairs(), clouds, new ArrayList<>());
    int start_point = Dungeon.randomStartPoint(random, Integer.parseInt("4"),
            Integer.parseInt("4"));
    System.out.println(start_point);
    Node[][] resultGraph = Node.setVertexType(graph);
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    12,
                    12,
                    "NO",
                    graph,
                    "3");
    int end_point = dungeon.randomEndPoint(allVertexPosition, random, start_point, resultGraph,
            Integer.parseInt("4"), Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), random);
    List<String> treasureList = treasure.assignTreasure(graph, random);
    MonsterInterface monster = new Monster(Integer.parseInt("3"), random);
    graph = dungeon.getGraph();
    monster.assignMonster(1, graph, 16, 12);
    DungeonView view = null;
    try {
      view = new MockView();
    } catch (IOException e) {
      e.printStackTrace();
    }
    GameInterface controller = new GameController(dungeon, treasure, monster, dp, weapon, view);
    view.addClickListener(controller);
    view.addKeyListener(controller);
    try {
      controller.playGame();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      controller.movePlayer(8);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(8, dungeon.getCurrentPoint());
  }

  /**
   * Test to check if all values remains same even after restarting the game.
   */
  @Test
  public void testResetGame() {
    Random random = new Random(44);
    Player dp = new DungeonPlayer(RandomNumberGenerator.getRandomName(random),
            RandomNumberGenerator.getRandomCharacter(random));
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(random, head.getNodePairs(), clouds, new ArrayList<>());
    int start_point = Dungeon.randomStartPoint(random, Integer.parseInt("4"),
            Integer.parseInt("4"));
    System.out.println(start_point);
    Node[][] resultGraph = Node.setVertexType(graph);
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    12,
                    12,
                    "NO",
                    graph,
                    "3");
    int end_point = dungeon.randomEndPoint(allVertexPosition, random, start_point, resultGraph,
            Integer.parseInt("4"), Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), random);
    List<String> treasureList = treasure.assignTreasure(graph, random);
    MonsterInterface monster = new Monster(Integer.parseInt("3"), random);
    graph = dungeon.getGraph();
    monster.assignMonster(1, graph, 16, 12);
    int startPoint = dungeon.getStartPoint();
    int endPoint = dungeon.getEndPoint();
    DungeonView view = null;
    try {
      view = new MockView();
    } catch (IOException e) {
      e.printStackTrace();
    }
    GameInterface controller = new GameController(dungeon, treasure, monster, dp, weapon, view);
    view.addClickListener(controller);
    view.addKeyListener(controller);
    try {
      controller.playGame();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      controller.restartGame();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(dungeon.getStartPoint(), startPoint);
    assertEquals(dungeon.getEndPoint(), endPoint);

  }

  /**
   * Test the edit configure method creates new dungeon with new values.
   */
  @Test
  public void testEditConfigure() {
    Random random = new Random(44);
    Player dp = new DungeonPlayer(RandomNumberGenerator.getRandomName(random),
            RandomNumberGenerator.getRandomCharacter(random));
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(random, head.getNodePairs(), clouds, new ArrayList<>());
    int start_point = Dungeon.randomStartPoint(random, Integer.parseInt("4"),
            Integer.parseInt("4"));
    System.out.println(start_point);
    Node[][] resultGraph = Node.setVertexType(graph);
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    12,
                    12,
                    "NO",
                    graph,
                    "3");
    int end_point = dungeon.randomEndPoint(allVertexPosition, random, start_point, resultGraph,
            Integer.parseInt("4"), Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), random);
    List<String> treasureList = treasure.assignTreasure(graph, random);
    MonsterInterface monster = new Monster(Integer.parseInt("3"), random);
    graph = dungeon.getGraph();
    monster.assignMonster(1, graph, 16, 12);
    int startPoint = dungeon.getStartPoint();
    int endPoint = dungeon.getEndPoint();
    DungeonView view = null;
    try {
      view = new MockView();
    } catch (IOException e) {
      e.printStackTrace();
    }
    GameInterface controller = new GameController(dungeon, treasure, monster, dp, weapon, view);
    view.addClickListener(controller);
    view.addKeyListener(controller);
    try {
      controller.playGame();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      controller.resetGame("5", "5", "NO", "3",
              "40", "3");
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(5, dungeon.getRows());
    assertEquals(5, dungeon.getCols());
    assertEquals("NO", dungeon.getIsWrap());
    assertEquals(3, dungeon.getConnectivity());
    assertEquals(40, treasure.getTreasurePerct(), 1e-6);
    assertEquals(3, monster.getCount());

  }

  /**
   * Test the thief position is updated when the player move to that position.
   */
  @Test
  public void testThiefPosition() {
    Random random = new Random(44);
    Player dp = new DungeonPlayer(RandomNumberGenerator.getRandomName(random),
            RandomNumberGenerator.getRandomCharacter(random));
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(random, head.getNodePairs(), clouds, new ArrayList<>());
    int start_point = Dungeon.randomStartPoint(random, Integer.parseInt("4"),
            Integer.parseInt("4"));
    System.out.println(start_point);
    Node[][] resultGraph = Node.setVertexType(graph);
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    12,
                    12,
                    "NO",
                    graph,
                    "3");
    int end_point = dungeon.randomEndPoint(allVertexPosition, random, start_point, resultGraph,
            Integer.parseInt("4"), Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), random);
    List<String> treasureList = treasure.assignTreasure(graph, random);
    MonsterInterface monster = new Monster(Integer.parseInt("3"), random);
    graph = dungeon.getGraph();
    monster.assignMonster(1, graph, 16, 12);
    int startPoint = dungeon.getStartPoint();
    int endPoint = dungeon.getEndPoint();
    DungeonView view = null;
    try {
      view = new MockView();
    } catch (IOException e) {
      e.printStackTrace();
    }
    GameInterface controller = new GameController(dungeon, treasure, monster, dp, weapon, view);
    view.addClickListener(controller);
    view.addKeyListener(controller);
    try {
      controller.playGame();
      controller.move("up");
      controller.pickup();
      controller.move("left");
      controller.pickup();
      controller.shoot("left", 1);
      controller.shoot("left", 1);
      controller.pickup();
      controller.move("left");

    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(dungeon.getCurrentPoint());
    assertEquals("[SAPPHIRES, RUBIES]", dp.getPlayerTreasure().toString());
    try {
      controller.move("down");
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals("[]", dp.getPlayerTreasure().toString());
  }

  /**
   * Test the game is over when the player reaches the end point.
   */
  @Test
  public void testGameOver() {
    Random random = new Random(44);
    Player dp = new DungeonPlayer(RandomNumberGenerator.getRandomName(random),
            RandomNumberGenerator.getRandomCharacter(random));
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(random, head.getNodePairs(), clouds, new ArrayList<>());
    int start_point = Dungeon.randomStartPoint(random, Integer.parseInt("4"),
            Integer.parseInt("4"));
    System.out.println(start_point);
    Node[][] resultGraph = Node.setVertexType(graph);
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    12,
                    12,
                    "NO",
                    graph,
                    "3");
    int end_point = dungeon.randomEndPoint(allVertexPosition, random, start_point, resultGraph,
            Integer.parseInt("4"), Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), random);
    List<String> treasureList = treasure.assignTreasure(graph, random);
    MonsterInterface monster = new Monster(Integer.parseInt("3"), random);
    graph = dungeon.getGraph();
    monster.assignMonster(1, graph, 16, 12);
    DungeonView view = null;
    try {
      view = new MockView();
    } catch (IOException e) {
      e.printStackTrace();
    }
    GameInterface controller = new GameController(dungeon, treasure, monster, dp, weapon, view);
    view.addClickListener(controller);
    view.addKeyListener(controller);
    try {
      controller.playGame();
      controller.move("up");
      controller.move("left");
      controller.move("up");
      controller.shoot("left", 1);
      controller.shoot("left", 1);
      controller.move("left");
      controller.move("left");

    } catch (IOException e) {
      e.printStackTrace();
    }
    assertTrue(controller.isGameOver());
  }

  /**
   * Test player is dead when the player encounter a monster.
   */
  @Test
  public void testPlayerIsDeadWhenEncounterAMonster() {
    Random random = new Random(44);
    Player dp = new DungeonPlayer(RandomNumberGenerator.getRandomName(random),
            RandomNumberGenerator.getRandomCharacter(random));
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(random, head.getNodePairs(), clouds, new ArrayList<>());
    int start_point = Dungeon.randomStartPoint(random, Integer.parseInt("4"),
            Integer.parseInt("4"));
    System.out.println(start_point);
    Node[][] resultGraph = Node.setVertexType(graph);
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    12,
                    12,
                    "NO",
                    graph,
                    "3");
    int end_point = dungeon.randomEndPoint(allVertexPosition, random, start_point, resultGraph,
            Integer.parseInt("4"), Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), random);
    List<String> treasureList = treasure.assignTreasure(graph, random);
    MonsterInterface monster = new Monster(Integer.parseInt("3"), random);
    graph = dungeon.getGraph();
    monster.assignMonster(1, graph, 16, 12);
    DungeonView view = null;
    try {
      view = new MockView();
    } catch (IOException e) {
      e.printStackTrace();
    }
    GameInterface controller = new GameController(dungeon, treasure, monster, dp, weapon, view);
    view.addClickListener(controller);
    view.addKeyListener(controller);
    try {
      controller.playGame();
      controller.move("up");
      controller.move("left");
      controller.move("up");
      controller.move("left");
      controller.move("left");

    } catch (IOException e) {
      e.printStackTrace();
    }
    assertFalse(dp.isAlive());
  }

  /**
   * Test moving monster is dead when encounter a player.
   */
  @Test
  public void testMovingMonsterDied() {
    Random random = new Random(44);
    Player dp = new DungeonPlayer(RandomNumberGenerator.getRandomName(random),
            RandomNumberGenerator.getRandomCharacter(random));
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(random, head.getNodePairs(), clouds, new ArrayList<>());
    int start_point = Dungeon.randomStartPoint(random, Integer.parseInt("4"),
            Integer.parseInt("4"));
    System.out.println(start_point);
    Node[][] resultGraph = Node.setVertexType(graph);
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    12,
                    12,
                    "NO",
                    graph,
                    "3");
    int end_point = dungeon.randomEndPoint(allVertexPosition, random, start_point, resultGraph,
            Integer.parseInt("4"), Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), random);
    List<String> treasureList = treasure.assignTreasure(graph, random);
    MonsterInterface monster = new Monster(Integer.parseInt("3"), random);
    graph = dungeon.getGraph();
    monster.assignMonster(1, graph, 16, 12);
    DungeonView view = null;
    try {
      view = new MockView();
    } catch (IOException e) {
      e.printStackTrace();
    }
    GameInterface controller = new GameController(dungeon, treasure, monster, dp, weapon, view);
    view.addClickListener(controller);
    view.addKeyListener(controller);
    try {
      controller.playGame();
      controller.move("up");
      controller.move("down");
      controller.move("up");
      controller.move("down");
      controller.move("up");
      controller.move("left");
      controller.move("down");
      controller.move("down");
      controller.move("up");
      controller.hitMonster();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(80, dp.getPlayerHealth());
    assertEquals(80, monster.getMovingMonsterHealth());
  }

  /**
   * Test player fell into the pit and health reduced to half.
   */
  @Test
  public void testPlayerFellIntoPit() {
    Random random = new Random(44);
    Player dp = new DungeonPlayer(RandomNumberGenerator.getRandomName(random),
            RandomNumberGenerator.getRandomCharacter(random));
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
            Node.possiblePath(random, head.getNodePairs(), clouds, new ArrayList<>());
    int start_point = Dungeon.randomStartPoint(random, Integer.parseInt("4"),
            Integer.parseInt("4"));
    System.out.println(start_point);
    Node[][] resultGraph = Node.setVertexType(graph);
    DungeonInterface dungeon =
            new Dungeon(
                    Integer.parseInt("4"),
                    Integer.parseInt("4"),
                    allVertexPosition,
                    12,
                    12,
                    "NO",
                    graph,
                    "3");
    int end_point = dungeon.randomEndPoint(allVertexPosition, random, start_point, resultGraph,
            Integer.parseInt("4"), Integer.parseInt("4"));
    TreasureInterface treasure = new Treasure(16, Double.parseDouble("40"));
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), random);
    List<String> treasureList = treasure.assignTreasure(graph, random);
    MonsterInterface monster = new Monster(Integer.parseInt("3"), random);
    graph = dungeon.getGraph();
    monster.assignMonster(1, graph, 16, 12);
    DungeonView view = null;
    try {
      view = new MockView();
    } catch (IOException e) {
      e.printStackTrace();
    }
    GameInterface controller = new GameController(dungeon, treasure, monster, dp, weapon, view);
    view.addClickListener(controller);
    view.addKeyListener(controller);
    try {
      controller.playGame();
      controller.move("up");
      controller.move("left");
      controller.shoot("left", 1);
      controller.shoot("left", 1);
      controller.move("left");
      controller.move("left");
      controller.move("down");
      controller.hitMonster();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(50, dp.getPlayerHealth());
    try {
      controller.move("up");
      controller.move("down");
      controller.hitMonster();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertFalse(dp.isAlive());
  }
}
