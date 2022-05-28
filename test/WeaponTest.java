import entry.Weapon;
import entry.WeaponInterface;
import entry.Dungeon;
import entry.Player;
import entry.DungeonPlayer;
import entry.Node;
import entry.RandomNumberGenerator;
import entry.DungeonInterface;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Junit test for Weapon class. It tests all the possible operations
 * to be performed on the Weapon class.
 */
public class WeaponTest {

  /**
   * This test checks if the weapon is updated when the player picks up the weapon.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMaxLocationNotNull() {
    Random r = new Random(42);
    WeaponInterface weapon = new Weapon(0, Double.parseDouble("40"), r);
  }

  /**
   * Test invalid percentage was entered.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testTreasurePerct() {
    Random r = new Random(42);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("111"), r);
  }

  /**
   * Test weapon passed to the method cannot be null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRandomNotNullInWeapon() {
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("111"), null);
  }

  /**
   * Test weapon was assigned correctly in the dungeon.
   */
  @Test
  public void testAssignWeapon() {
    Random r = new Random(42);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), r);
    List<List<String>> weaponList = weapon.assignWeapon();
    assertEquals("[[Arrow], [Arrow], [NULL], [NULL], [Arrow]," +
            " [NULL], [NULL], [NULL], [NULL], [NULL], [Arrow], [Arrow]," +
            " [NULL], [NULL], [NULL], [Arrow]]", String.valueOf(weaponList));
    int count = 0;
    for (List<String> s : weaponList) {
      if (s.get(0).equals("Arrow")) {
        count++;
      }
    }
    assertEquals(6, count);
  }

  /**
   * Test player can pick up the weapon and set it back to null.
   */
  @Test
  public void testPlayerPickWeapon() {
    Random r = new Random(42);
    Player dp = new DungeonPlayer(RandomNumberGenerator.getRandomName(r),
            RandomNumberGenerator.getRandomCharacter(r));
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), r);
    List<List<String>> weaponList = weapon.assignWeapon();
    assertEquals("Arrow", String.valueOf(weaponList.get(1).get(0)));
    assertEquals(3, dp.getNumArrows());
    int count = weapon.getWeaponList().get(2 - 1).size();
    dp.updateArrowCount(count);
    weapon.updateWeaponList(2);
    assertEquals("NULL", weapon.getWeaponList().get(1).get(0));
    assertEquals(4, dp.getNumArrows());
  }

  /**
   * Test arrow location is updated once player pick up the weapon.
   */
  @Test
  public void testArrowLocationIsUpdated() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<Node> allVertexPosition = Node.possiblePath(rand, head.getNodePairs(), clouds,
            new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    DungeonInterface dungeon = new Dungeon(Integer.parseInt("4"), Integer.parseInt("4"),
            allVertexPosition, 6, 6, "NO", graph, "2");
    weapon.assignWeapon();
    graph = dungeon.getGraph();
    dungeon.movePlayer(graph);
    List<String> allDirections = dungeon.getAllDirections();
    assertEquals("[Arrow]", String.valueOf(weapon.getWeaponList().get(9)));
    int arrowLocation = dungeon.shootArrow("S", "1", 6, graph,
            16);
    weapon.addWeaponToList(arrowLocation);
    assertEquals(10, arrowLocation);
    assertEquals("[Arrow, Arrow]", String.valueOf(weapon.getWeaponList().get(9)));
  }

  /**
   * Test player is dead after eaten by the monster.
   */
  @Test
  public void testPlayerIsDeadAfterEatenByMonster() {
    Random rand = new Random(42);
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt("4"), Integer.parseInt("4"));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    Player dp = new DungeonPlayer(RandomNumberGenerator.getRandomName(rand),
            RandomNumberGenerator.getRandomCharacter(rand));
    List<Node> allVertexPosition = Node.possiblePath(rand, head.getNodePairs(), clouds,
            new ArrayList<>());
    Node[][] resultGraph = Node.setVertexType(graph);
    WeaponInterface weapon = new Weapon(16, Double.parseDouble("40"), rand);
    DungeonInterface dungeon = new Dungeon(Integer.parseInt("4"), Integer.parseInt("4"),
            allVertexPosition, 11, 11, "NO", graph, "2");
    weapon.assignWeapon();
    graph = dungeon.getGraph();
    dungeon.movePlayer(graph);
    List<Node> allPath = dungeon.getPossiblePath();
    List<String> allDirections = dungeon.getAllDirections();
    assertTrue(dp.isAlive());

  }
}
