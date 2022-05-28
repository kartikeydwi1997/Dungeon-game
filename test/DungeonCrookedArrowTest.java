import entry.DungeonInterface;
import entry.Node;
import entry.Dungeon;
import entry.WeaponInterface;
import entry.Weapon;
import entry.Monster;
import entry.MonsterInterface;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Junit class to test dungeon crooked arrow as well as the monster. It test all methods in the
 * monster as well as dungeon class.
 */
public class DungeonCrookedArrowTest {

  /** Test if the arrow more correctly in the tunnel. */
  @Test
  public void testArrowMoveCorrectlyInTunnel() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
        Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    DungeonInterface dungeon =
        new Dungeon(
            Integer.parseInt("4"),
            Integer.parseInt("4"),
            allVertexPosition,
            5,
            5,
            "NO",
            graph,
            "2");
    weapon.assignWeapon();
    graph = dungeon.getGraph();
    dungeon.movePlayer(graph);
    List<Node> allPath = dungeon.getPossiblePath();
    int[] neighbourNode = {6, 1};
    int i = 0;
    for (Node node : allPath) {
      assertEquals(neighbourNode[i], node.getVal());
      i++;
    }
    assertEquals("[Arrow]", String.valueOf(weapon.getWeaponList().get(9)));
    int arrowLocation = dungeon.shootArrow("E", "1", 5, graph, 16);
    assertEquals(10, arrowLocation);
    weapon.addWeaponToList(arrowLocation);
    assertEquals("[Arrow, Arrow]", String.valueOf(weapon.getWeaponList().get(9)));
  }

  /** Test if the arrow more correctly in the cave. */
  @Test
  public void testArrowMoveCorrectlyInCave() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
        Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    DungeonInterface dungeon =
        new Dungeon(
            Integer.parseInt("4"),
            Integer.parseInt("4"),
            allVertexPosition,
            6,
            6,
            "NO",
            graph,
            "2");
    weapon.assignWeapon();
    graph = dungeon.getGraph();
    dungeon.movePlayer(graph);
    List<Node> allPath = dungeon.getPossiblePath();
    int[] neighbourNode = {5, 10};
    int i = 0;
    for (Node node : allPath) {
      assertEquals(neighbourNode[i], node.getVal());
      i++;
    }
    assertEquals("[Arrow]", String.valueOf(weapon.getWeaponList().get(9)));
    int arrowLocation = dungeon.shootArrow("S", "1", 6, graph, 16);
    assertEquals(10, arrowLocation);
    weapon.addWeaponToList(arrowLocation);
    assertEquals("[Arrow, Arrow]", String.valueOf(weapon.getWeaponList().get(9)));
  }

  /** Test if the monster were assigned to the dungeon correctly. */
  @Test
  public void testMonsterWereAssignedInDungeon() {
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
    assertEquals(
        "[Tunnel, Tunnel, Tunnel, MONSTER, Tunnel,"
            + " Tunnel, Tunnel, Tunnel, Tunnel, MONSTER, NO MONSTER, "
            + "NO MONSTER, NO MONSTER, NO MONSTER, MONSTER, NO MONSTER]",
        monsterList.toString());
  }

  /** Test if the player detect monster smell correctly. */
  @Test
  public void testMonsterCheckMonsterFunction() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
        Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"), Integer.parseInt("4"));
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
    MonsterInterface monster = new Monster(Integer.parseInt("6"), rand);
    graph = dungeon.getGraph();
    monster.assignMonster(15, graph, 16, 1);
    String result = monster.checkMonster(1, 16, dungeon);
    assertEquals("No monster near your location!!", result);
    String result1 = monster.checkMonster(7, 16, dungeon);
    assertEquals("More pungent smell of a monster nearby!!", result1);
  }

  /** Test if the monster is damaged after user shot an arrow. */
  @Test
  public void testDamageMonsterInMonster() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
        Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"), Integer.parseInt("4"));
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
    MonsterInterface monster = new Monster(Integer.parseInt("6"), rand);
    graph = dungeon.getGraph();
    monster.assignMonster(15, graph, 16, 1);
    assertEquals("MONSTER", monster.getMonsterLocation().get(10));
    monster.damageMonster(11);
    assertEquals("MONSTER DAMAGED", monster.getMonsterLocation().get(10));
  }

  /** Test if the monster is dead after player hit the monster with the arrow. */
  @Test
  public void testMonsterDiedAfterArrow() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
        Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"), Integer.parseInt("4"));
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
    MonsterInterface monster = new Monster(Integer.parseInt("6"), rand);
    graph = dungeon.getGraph();
    monster.assignMonster(15, graph, 16, 1);
    assertEquals("MONSTER", monster.getMonsterLocation().get(10));
    monster.damageMonster(11);
    monster.damageMonster(11);
    assertEquals("NO MONSTER", monster.getMonsterLocation().get(10));
  }

  /** Test monster count cannot be greater than the max locations. */
  @Test(expected = IllegalArgumentException.class)
  public void testMonsterCountCannotBeGreaterThanCave() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition =
        Node.possiblePath(rand, head.getNodePairs(), clouds, new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    int start_point = Dungeon.randomStartPoint(rand, Integer.parseInt("4"), Integer.parseInt("4"));
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
    MonsterInterface monster = new Monster(Integer.parseInt("10"), rand);
    graph = dungeon.getGraph();
    monster.assignMonster(15, graph, 16, 1);
  }

  /** Test the monster count cannot be null. */
  @Test(expected = IllegalArgumentException.class)
  public void testMonsterCountCannotbeNull() {
    Random rand = new Random(42);
    MonsterInterface monster = new Monster(Integer.parseInt("0"), rand);
  }

  /** Test the monster random cannot be null. */
  @Test(expected = IllegalArgumentException.class)
  public void testMonsterRandomCannotBeNull() {
    Random rand = new Random(42);
    MonsterInterface monster = new Monster(Integer.parseInt("2"), null);
  }
}
