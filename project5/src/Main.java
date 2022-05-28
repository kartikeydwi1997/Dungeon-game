import controller.GameController;
import controller.GameInterface;
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
import viewnew.DungeonView;
import viewnew.MainView;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * Run a TicTacToe game interactively on the console.
 */
public class Main {


  /**
   * Run a TicTacToe game interactively on the console.
   */
  public static void main(String[] args) throws IOException {
    String numRows = "";
    String numCols = "";
    String isWrap = "";
    String connectivity = "";
    String treasurePerct = "";
    String monsterCount = "";
    if (args.length != 0) {
      numRows = args[0];
      numCols = args[1];
      isWrap = args[2];
      connectivity = args[3];
      treasurePerct = args[4];
      monsterCount = args[5];
    } else {
      numRows = JOptionPane.showInputDialog("Enter the number of rows");
      numCols = JOptionPane.showInputDialog("Enter the number of columns");
      isWrap = JOptionPane.showInputDialog("Enter the wrap value as YES/NO");
      connectivity = JOptionPane.showInputDialog("Enter the connectivity value");
      treasurePerct = JOptionPane.showInputDialog("Enter the treasure percentage");
      monsterCount = JOptionPane.showInputDialog("Enter the monster count");
    }
    if (Objects.equals(numRows, "") || Integer.parseInt(numRows) < 0) {
      JOptionPane.showMessageDialog(null, "Invalid Row value entered!");
    }
    if (Objects.equals(numCols, "") || Integer.parseInt(numCols) < 0) {
      JOptionPane.showMessageDialog(null, "Invalid Column value entered!");
    }
    if (Objects.equals(isWrap, "")) {
      JOptionPane.showMessageDialog(null, "Invalid Wrap value entered!");
    }
    if (Objects.equals(treasurePerct, "") || Integer.parseInt(treasurePerct) < 0
            || Integer.parseInt(treasurePerct) > 100) {
      JOptionPane.showMessageDialog(null,
              "Invalid Treasure Percentage value entered!");
    }
    if (Objects.equals(monsterCount, "")
            || Integer.parseInt(monsterCount) < 0 || Integer.parseInt(monsterCount) > 100) {
      JOptionPane.showMessageDialog(null,
              "Invalid Monster Count value entered!");
    }
    Random random = new Random(44);
    Player dp = new DungeonPlayer(RandomNumberGenerator.getRandomName(random),
            RandomNumberGenerator.getRandomCharacter(random));
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt(numCols), Integer.parseInt(numRows));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = new ArrayList<>();
    if (isWrap.equals("YES")) {
      nodePairs = head.getNodePairsWrapped();
    } else if (isWrap.equals("NO")) {
      nodePairs = head.getNodePairs();
    }

    List<List<Node>> leftOver = new ArrayList<>();
    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    int start_point = Dungeon.randomStartPoint(random, Integer.parseInt(numCols),
            Integer.parseInt(numRows));
    Node[][] resultGraph = Node.setVertexType(graph);
    int currentLocation = start_point;
    checkConnectivity(connectivity, leftOver);
    DungeonInterface dungeon = new Dungeon(Integer.parseInt(numRows), Integer.parseInt(numCols),
            allVertexPosition, start_point, currentLocation, isWrap, graph, connectivity);
    int end_point = dungeon.randomEndPoint(allVertexPosition, random, start_point, resultGraph,
            Integer.parseInt(numCols), Integer.parseInt(numRows));
    int max_location = Integer.parseInt(numRows) * Integer.parseInt(numCols);
    TreasureInterface treasure = new Treasure(max_location, Double.parseDouble(treasurePerct));
    WeaponInterface weapon = new Weapon(max_location, Double.parseDouble(treasurePerct), random);
    List<List<Node>> nodePairsForConnec = new ArrayList<>(nodePairs);
    nodePairs = Node.addPairsToAllVertex(random, nodePairsForConnec, leftOver,
            Integer.parseInt(connectivity));
    List<String> treasureList = treasure.assignTreasure(graph, random);
    MonsterInterface monster = new Monster(Integer.parseInt(monsterCount), random);
    System.out.println("Dungeon is with T and C: ");
    String resultGraphWithDungeon = new String();
    resultGraphWithDungeon = Node.showDungeon(resultGraph);
    System.out.println(resultGraphWithDungeon);
    String showAllPairs = Node.showPairs(resultGraph);
    if (args.length != 0) {
      Readable in = new InputStreamReader(System.in);
      Appendable output = System.out;
      new GameControllerWithArgs(in, output).playGame(dungeon, treasure, monster, dp, weapon);
    }
    if (args.length == 0) {
      DungeonView view = new MainView();
      GameInterface controller = new GameController(dungeon, treasure, monster, dp, weapon, view);
      view.addClickListener(controller);
      view.addKeyListener(controller);
      controller.playGame();
    }
  }

  private static void checkConnectivity(String connectivity, List<List<Node>> leftOver) {
    if (Integer.parseInt(connectivity) < 0 || Integer.parseInt(connectivity) > leftOver.size()) {
      throw new IllegalArgumentException("Illegal connectivity entered!");
    }
  }
}