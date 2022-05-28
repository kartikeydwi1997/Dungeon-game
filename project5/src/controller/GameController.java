package controller;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * This is the controller class which takes all the model interfaces as the input with the view
 * and perform the game operations by calling the model interfaces and updating the view.
 */
public class GameController implements GameInterface {
  private DungeonInterface dungeon;
  private Player player;
  private DungeonView view;
  private WeaponInterface weapon;
  private MonsterInterface monster;
  private TreasureInterface treasure;
  private Node[][] graph;
  private boolean isGameOver;


  /**
   * This is the constuctor which takes the view and the model interfaces as the input and assign
   * the values to the model and view attributes.
   *
   * @param dungeon  Represents the dungeon interface.
   * @param treasure Represents the treasure interface.
   * @param monster  Represents the monster interface.
   * @param dp       Represents the dungeon player interface.
   * @param weapon   Represents the weapon interface.
   * @param view     Represents the view interface.
   */
  public GameController(DungeonInterface dungeon, TreasureInterface treasure,
                        MonsterInterface monster, Player dp, WeaponInterface weapon,
                        DungeonView view) {
    this.dungeon = dungeon;
    this.treasure = treasure;
    this.monster = monster;
    this.player = dp;
    this.weapon = weapon;
    this.view = view;
    this.isGameOver = false;
  }


  /**
   * This method is used to set values to set up the view by adding caves and tunnel images.
   * It also added the monster,treasure, weapon , player and game panel to the screen.
   *
   * @throws IOException if the file is not found.
   */
  public void playGame() throws IOException {
    if (dungeon == null) {
      throw new IllegalArgumentException("Dungeon model cannot be null!!");
    }
    if (treasure == null) {
      throw new IllegalArgumentException("Treasure model cannot be null!!");
    }
    if (monster == null) {
      throw new IllegalArgumentException("Monster model cannot be null!!");
    }
    if (player == null) {
      throw new IllegalArgumentException("Player model cannot be null!!");
    }
    if (weapon == null) {
      throw new IllegalArgumentException("Weapon model cannot be null!!");
    }
    player.setPlayerAlive(true);
    graph = dungeon.getGraph();
    int end_point = dungeon.getEndPoint();
    int start_point = dungeon.getStartPoint();
    int maxLocation = dungeon.getRows() * dungeon.getCols();
    int rows = dungeon.getRows();
    int cols = dungeon.getCols();
    int thiefPosition = monster.findThiefPosition(maxLocation, start_point);
    int pitPosition = dungeon.addPitPosition(maxLocation, start_point, end_point);
    // assign monster to list
    monster.assignMonster(end_point, graph, maxLocation, start_point);
    int movingMonsterPosition = monster.findMovingMonster(maxLocation, start_point, end_point);
    int currentLocation = dungeon.getCurrentPoint();
    List<List<String>> weaponList = weapon.assignWeapon();
    view.makeVisible();
    isGameOver = false;
    player.setPlayerAlive(true);
    monster.setMovingMonsterAlive(true);
    int path = Dungeon.printShortestDistance(dungeon.getAdjList(),
            dungeon.getCurrentPoint(), dungeon.getPitPosition(), maxLocation + 1);
    if (path == 1) {
      view.plotPitIndicator(dungeon.getCurrentPoint(), rows, cols);
      view.displayText("Beware! their is a pit nearby");
    }
    List<String> list = dungeon.getAllNeighbours(graph);
    view.plotImage(list, rows, cols);
    view.showPlayerLocation(dungeon.getCurrentPoint());
    view.showPlayerHealth(player.getPlayerHealth());
    view.showMonsterHealth(monster.getMovingMonsterHealth());
    view.showPlayerArrows(player.getNumArrows());
    view.showPlayerTreasure(player.getPlayerTreasure());
    view.plotPlayer(start_point, rows, cols);
    view.plotMonster(monster.getMonsterLocation(), rows, cols);
    view.plotArrows(weapon.getWeaponList(), rows, cols);
    view.plotTreasure(treasure.getTreasureList(), rows, cols);
    view.plotThief(monster.getThiefPosition(), rows, cols);
    view.plotMovingMonster(monster.getMovingMonsterPosition(), rows, cols);
    view.plotPit(dungeon.getPitPosition(), rows, cols);
    view.plotBlackImage(dungeon.getStartPoint(), rows, cols);
    detectMonsterSmell();
    dungeon.movePlayer(graph);

  }

  /**
   * This method is used to move the player inside the dungeon. And, check for smell, moving monster
   * and thief.
   *
   * @param move is the direction where the player wants to move.
   * @throws IOException if the file is not found.
   */
  @Override
  public void move(String move) throws IOException {
    int rows = dungeon.getRows();
    int cols = dungeon.getCols();
    dungeon.setPreviousPoint(dungeon.getCurrentPoint());
    int maxLocation = dungeon.getRows() * dungeon.getCols();
    if (player.isAlive() && !isGameOver) {
      String direction = "";
      if (move.equals("up")) {
        direction = "N";
      }
      if (move.equals("down")) {
        direction = "S";
      }
      if (move.equals("left")) {
        direction = "W";
      }
      if (move.equals("right")) {
        direction = "E";
      }
      List<Node> allPath = dungeon.getPossiblePath();
      List<String> allDirections = dungeon.getAllDirections();
      int index = allDirections.indexOf(direction);
      if (index != -1) {
        int currentLocation = dungeon.validateUserInput(index + 1, allPath);
        view.plotPlayer(currentLocation, rows, cols);
        view.removeBlackImage(dungeon.getCurrentPoint(), rows, cols);
        if (monster.checkMonsterExist(dungeon.getCurrentPoint())) {
          player.setPlayerAlive(false);
          view.displayText("You have encountered a monster!" + "\n" +
                  "Chomp, chomp, chomp, you are eaten by an Otyugh!");

        }


        if (monster.checkMonsterDamaged(dungeon.getCurrentPoint())) {
          view.displayText("You have encountered a monster!" + "\n" +
                  "Luck is in your pocket!! Monster won't kill you");
          player.setPlayerAlive(false);
        } else {
          if (!monster.checkNoMonster(dungeon.getCurrentPoint())) {
            player.setPlayerAlive(false);
            view.displayText("You have encountered a monster!" + "\n" +
                    "Chomp, chomp, chomp, you are eaten by an Otyugh!");

          }
        }

        graph = dungeon.getGraph();
        if (player.isAlive()) {
          detectMonsterSmell();
          dungeon.movePlayer(graph);

        }
        int path = Dungeon.printShortestDistance(dungeon.getAdjList(),
                dungeon.getCurrentPoint(), dungeon.getPitPosition(), maxLocation + 1);
        if (path == 1) {
          view.plotPitIndicator(dungeon.getCurrentPoint(), rows, cols);
          view.displayText("Beware! their is a pit nearby");
        }

        if (dungeon.getPitPosition() == dungeon.getCurrentPoint() && player.isAlive()) {
          player.updatePlayerHealth(50);
          if (player.getPlayerHealth() <= 0) {
            player.setPlayerAlive(false);
            view.displayText("Game Over!! You have fallen into a pit and you died!");
          } else {
            view.displayText("You have fallen into a pit and you are still alive!");
          }
        }
        int thiefPosition = monster.findThiefPosition(maxLocation, dungeon.getStartPoint());
        view.plotThief(thiefPosition, rows, cols);
        if (currentLocation == thiefPosition && player.isAlive()) {
          player.removePlayerTreasure();
          view.displayText("You encounter a thief and all " +
                  "your treasure is stolen!!");
          view.showPlayerTreasure(player.getPlayerTreasure());
        }
        int movingMonsterPosition = monster.findMovingMonster(maxLocation, dungeon.getStartPoint(),
                dungeon.getEndPoint());
        if (monster.isMovingMonsterAlive()) {
          view.plotMovingMonster(movingMonsterPosition, rows, cols);
        }
        if (currentLocation == movingMonsterPosition && monster.isMovingMonsterAlive()) {
          String displayText = "";
          if (player.getPlayerHealth() > 0) {
            displayText += "You have encountered a moving monster and the monster hit " +
                    "you." + "\n";
            player.updatePlayerHealth(10);
            if (player.getPlayerHealth() <= 0) {
              player.setPlayerAlive(false);
            }
          } else {
            player.setPlayerAlive(false);
            displayText += "You have been killed by a moving monster!!";
          }
          view.displayText(displayText);
        }

        view.showPlayerLocation(dungeon.getCurrentPoint());
        view.showPlayerHealth(player.getPlayerHealth());
        view.showMonsterHealth(monster.getMovingMonsterHealth());
        if (dungeon.getCurrentPoint() == dungeon.getEndPoint()) {
          isGameOver = true;
        }
      }
    } else {
      view.displayText("Game Over!! You have died!");
    }
    if (isGameOver) {
      if (player.isAlive()) {
        if (monster.checkMonsterExist(dungeon.getCurrentPoint())) {
          player.setPlayerAlive(false);
          view.displayText("You have encountered a monster!" + "\n"
                  + "Chomp, chomp, chomp, you are eaten by an Otyugh!");
        } else if (monster.checkNoMonster(dungeon.getCurrentPoint())) {

          view.displayText("You reached the end cave, you won!!!");

        } else if (monster.checkMonsterDamaged(dungeon.getCurrentPoint())) {
          view.displayText("You have encountered a monster!" + "\n"
                  + "Luck is in your pocket!! Monster won't kill you" + "\n"
                  + "Game over!!!");
        } else {
          if (!monster.checkNoMonster(dungeon.getCurrentPoint())) {
            player.setPlayerAlive(false);
            view.displayText("You have encountered a monster!" + "\n"
                    + "Chomp, chomp, chomp, you are eaten by an Otyugh!");

          }
        }
      }
    }
  }

  private void detectMonsterSmell() {
    int maxLocation = dungeon.getRows() * dungeon.getCols();
    int previousLocation = dungeon.getPreviousPoint();
    int rows = dungeon.getRows();
    int cols = dungeon.getCols();
    if (Objects.equals(monster.checkMonster(dungeon.getCurrentPoint(),
            maxLocation, dungeon), "Less pungent smell of a monster nearby!!")) {
      view.removeLessPungentSmell(previousLocation, rows, cols);
      view.showLessPungentSmell(dungeon.getCurrentPoint(), rows, cols);
    }
    if (Objects.equals(monster.checkMonster(dungeon.getCurrentPoint(),
            maxLocation, dungeon), "More pungent smell of a monster nearby!!")
            || Objects.equals(monster.checkMonster(dungeon.getCurrentPoint(),
            maxLocation, dungeon), "More pungent smell of multiple monster nearby!!")) {
      view.removeMorePungentSmell(previousLocation, rows, cols);
      view.showMorePungentSmell(dungeon.getCurrentPoint(), rows, cols);
    }
    if (Objects.equals(monster.checkMonster(dungeon.getCurrentPoint(),
            maxLocation, dungeon), "No monster near your location!!")) {
      view.removeMorePungentSmell(dungeon.getCurrentPoint(), rows, cols);
      view.removeLessPungentSmell(dungeon.getCurrentPoint(), rows, cols);
    }
    view.displayText(monster.checkMonster(dungeon.getCurrentPoint(), maxLocation, dungeon));
  }

  /**
   * This method is used to shoot the monster whenever player wants to shoot in any direction.
   *
   * @param move     Direction the direction in which player wants to shoot.
   * @param distance Distance the distance in which player wants to shoot.
   */
  @Override
  public void shoot(String move, int distance) {
    int maxLocation = dungeon.getRows() * dungeon.getCols();
    if (player.isAlive() && player.hasArrows() && !isGameOver) {
      String direction = "";
      if (move.equals("up")) {
        direction = "N";
      }
      if (move.equals("down")) {
        direction = "S";
      }
      if (move.equals("left")) {
        direction = "W";
      }
      if (move.equals("right")) {
        direction = "E";
      }
      List<Node> allPath = dungeon.getPossiblePath();
      List<String> allDirections = dungeon.getAllDirections();
      if (dungeon.validDirection(direction, allDirections)) {
        int arrowLocation =
                dungeon.shootArrow(direction, String.valueOf(distance),
                        dungeon.getCurrentPoint(), graph, maxLocation);
        detectMonsterSmell();
        view.displayText("You shot an arrow at location" + arrowLocation);
        if (dungeon.isArrowMissed(arrowLocation)) {
          view.displayText("Arrow missed!");
        } else if (monster.checkNoMonster(arrowLocation)) {
          view.displayText("You wasted your arrow!!");
        } else {
          if (monster.checkMonsterExist(arrowLocation)
                  || !monster.checkMonsterDead(arrowLocation)) {
            view.displayText("Monster is hit!");
            monster.damageMonster(arrowLocation);
            if (monster.checkMonsterDead(arrowLocation)) {
              view.removeMonster(arrowLocation, dungeon.getRows(), dungeon.getCols());
              view.displayText("Monster is dead!");
              detectMonsterSmell();
            } else {
              view.displayText("Monster is still alive but injured!");
            }
          }
        }
        player.updateArrowCount(-1);
        view.showPlayerArrows(player.getNumArrows());
        weapon.addWeaponToList(arrowLocation);
        view.addArrow(arrowLocation, dungeon.getRows(), dungeon.getCols());
      } else {
        view.displayText("You can't shoot in this direction");
      }
    } else if (isGameOver) {
      view.displayText("You win the game :)");
    } else {
      view.displayText("You are out of arrows!!");
    }
  }


  /**
   * This method is used to pick up the treasure present the player current location.
   */
  @Override
  public void pickup() {
    String displayMessageText = "";
    boolean isPickedArrow = false;
    if (player.isAlive() && !isGameOver) {
      int currentLocation = dungeon.getCurrentPoint();
      if (weapon.checkWeapon(currentLocation)) {
        int count = weapon.getWeaponList().get(currentLocation - 1).size();
        player.updateArrowCount(count);
        weapon.updateWeaponList(currentLocation);
        displayMessageText = "You picked up an arrow! ";
        isPickedArrow = true;
        view.removeArrow(currentLocation, dungeon.getRows(), dungeon.getCols());
      } else {
        //DO NOTHING
      }
      // Treasure picked up
      String allTreasure;
      allTreasure = treasure.treasureAtIndex(currentLocation - 1);
      if (allTreasure.equals("T") || allTreasure.equals("NT")) {
        // DO NOTHING
      } else {
        if (isPickedArrow) {
          displayMessageText = "You picked up an arrow and treasure!!";
        } else {
          displayMessageText += "You picked up a treasure!";
        }
        view.removeTreasure(currentLocation, dungeon.getRows(), dungeon.getCols());
        player.setPlayerTreasure(allTreasure);
      }
      view.displayText(displayMessageText);

      view.showPlayerArrows(player.getNumArrows());
      view.showPlayerTreasure(player.getPlayerTreasure());
    }
  }

  /**
   * This method is used to restart the game with the same values of the previous game.
   *
   * @throws IOException if the file is not found.
   */
  @Override
  public void restartGame() throws IOException {
    resetGame(String.valueOf(dungeon.getRows()), String.valueOf(dungeon.getCols()),
            String.valueOf(dungeon.getIsWrap()), String.valueOf(dungeon.getConnectivity()),
            String.valueOf(treasure.getTreasurePerct()), String.valueOf(monster.getCount()));
  }

  /**
   * This method is used to hit the moving monster found in the dungeon. It also checks if the
   * player is alive or not after a fight with a moving monster.
   */
  @Override
  public void hitMonster() {
    if (dungeon.getCurrentPoint() == monster.getMovingMonsterPosition()) {
      int playerHealth = player.getPlayerHealth();
      String displayMessageText = "";
      if (playerHealth > 0) {
        monster.updateMonsterHealth();
        if (monster.getMovingMonsterHealth() > 0) {
          displayMessageText += "You caused damaged to the moving monster and " +
                  "reduced its health to "
                  + monster.getMovingMonsterHealth() + ".";
        } else {
          monster.setMovingMonsterAlive(false);
          displayMessageText += " You have killed the moving monster!!";
        }
      } else {
        player.setPlayerAlive(false);
        displayMessageText += " You have been killed by a moving monster!!";
      }
      if (monster.isMovingMonsterAlive() && monster.getMovingMonsterHealth() > 0) {
        if (player.getPlayerHealth() > 0) {
          player.updatePlayerHealth(10);
          displayMessageText += " Moving monster hit you and your health reduced to "
                  + player.getPlayerHealth() + ".";
          if (player.getPlayerHealth() <= 0) {
            player.setPlayerAlive(false);
            displayMessageText += " You have been killed by a moving monster!!";
          }
        } else {
          player.setPlayerAlive(false);
        }
      } else {
        monster.setMovingMonsterAlive(false);
        if (displayMessageText.equals(" You have killed the moving monster!!")) {
          displayMessageText = "";
          displayMessageText += " You have killed the moving monster!!";
        }
        if (!monster.isMovingMonsterAlive()) {
          view.removeMovingMonster(monster.getMovingMonsterPosition(),
                  dungeon.getRows(), dungeon.getCols());
        }
      }
      view.displayText(displayMessageText);
      view.showPlayerHealth(player.getPlayerHealth());
      view.showMonsterHealth(monster.getMovingMonsterHealth());
    }
  }

  /**
   * This method is used to move the player using mouse clicks to the neighbouring cells.
   *
   * @param finalI the index of the cell that the player is moving to.
   * @throws IOException if the file is not found.
   */
  @Override
  public void movePlayer(int finalI) throws IOException {
    Node node = new Node(finalI);
    int rows = dungeon.getRows();
    int cols = dungeon.getCols();
    dungeon.setPreviousPoint(dungeon.getCurrentPoint());
    int maxLocation = dungeon.getRows() * dungeon.getCols();
    if (player.isAlive() && !isGameOver) {
      List<Node> allPath = dungeon.getPossiblePath();
      int index = allPath.indexOf(node);
      if (index != -1) {
        int currentLocation = dungeon.validateUserInput(index + 1, allPath);
        view.plotPlayer(currentLocation, rows, cols);
        view.removeBlackImage(dungeon.getCurrentPoint(), rows, cols);
        if (monster.checkMonsterExist(dungeon.getCurrentPoint())) {
          player.setPlayerAlive(false);
          view.displayText("You have encountered a monster!" + "\n" +
                  "Chomp, chomp, chomp, you are eaten by an Otyugh!");
        }
        if (monster.checkMonsterDamaged(dungeon.getCurrentPoint())) {
          view.displayText("You have encountered a monster!" + "\n" +
                  "Luck is in your pocket!! Monster won't kill you");
        } else {
          if (!monster.checkNoMonster(dungeon.getCurrentPoint())) {
            player.setPlayerAlive(false);
            view.displayText("You have encountered a monster!" + "\n" +
                    "Chomp, chomp, chomp, you are eaten by an Otyugh!");
          }
        }
        graph = dungeon.getGraph();
        if (player.isAlive()) {
          detectMonsterSmell();
          dungeon.movePlayer(graph);
        }
        int path = Dungeon.printShortestDistance(dungeon.getAdjList(),
                dungeon.getCurrentPoint(), dungeon.getPitPosition(), maxLocation + 1);
        if (path == 1) {
          view.plotPitIndicator(dungeon.getCurrentPoint(), rows, cols);
          view.displayText("Beware! their is a pit nearby");
        }
        if (dungeon.getPitPosition() == dungeon.getCurrentPoint() && player.isAlive()) {
          player.updatePlayerHealth(50);
          if (player.getPlayerHealth() <= 0) {
            player.setPlayerAlive(false);
            view.displayText("Game Over!! You have fallen into a pit and you died!");
          } else {
            view.displayText("You have fallen into a pit and you are still alive!");
          }
        }
        int thiefPosition = monster.findThiefPosition(maxLocation, dungeon.getStartPoint());
        view.plotThief(thiefPosition, rows, cols);
        if (currentLocation == thiefPosition && player.isAlive()) {
          player.removePlayerTreasure();
          view.displayText("You encounter a thief and all " +
                  "your treasure is stolen!!");
          view.showPlayerTreasure(player.getPlayerTreasure());
        }
        int movingMonsterPosition = monster.findMovingMonster(maxLocation, dungeon.getStartPoint(),
                dungeon.getEndPoint());
        if (monster.isMovingMonsterAlive()) {
          view.plotMovingMonster(movingMonsterPosition, rows, cols);
        }
        if (currentLocation == movingMonsterPosition && monster.isMovingMonsterAlive()) {
          String displayText = "";
          if (player.getPlayerHealth() > 0) {
            displayText += "You have encountered a moving monster and the monster hit " +
                    "you." + "\n";
            player.updatePlayerHealth(10);
            if (player.getPlayerHealth() <= 0) {
              player.setPlayerAlive(false);
              displayText += "You have been killed by a moving monster!";
            }
          } else {
            player.setPlayerAlive(false);
            displayText += "You have been killed by a moving monster!!";
          }
          view.displayText(displayText);
        }
        view.showPlayerLocation(dungeon.getCurrentPoint());
        view.showPlayerHealth(player.getPlayerHealth());
        view.showMonsterHealth(monster.getMovingMonsterHealth());
        if (dungeon.getCurrentPoint() == dungeon.getEndPoint()) {
          isGameOver = true;
        }
      }
    }
    if (isGameOver) {
      if (player.isAlive()) {
        if (monster.checkMonsterExist(dungeon.getCurrentPoint())) {
          player.setPlayerAlive(false);
          view.displayText("You have encountered a monster!" + "\n"
                  + "Chomp, chomp, chomp, you are eaten by an Otyugh!");
        } else if (monster.checkNoMonster(dungeon.getCurrentPoint())) {

          view.displayText("You reached the end cave, you won!!!");

        } else if (monster.checkMonsterDamaged(dungeon.getCurrentPoint())) {
          view.displayText("You have encountered a monster!" + "\n"
                  + "Luck is in your pocket!! Monster won't kill you" + "\n"
                  + "Game over!!!");
        } else {
          if (!monster.checkNoMonster(dungeon.getCurrentPoint())) {
            player.setPlayerAlive(false);
            view.displayText("You have encountered a monster!" + "\n"
                    + "Chomp, chomp, chomp, you are eaten by an Otyugh!");
          }
        }
      }
    }
  }

  /**
   * This method reset the game and start a new game with new configuration provided by the player.
   *
   * @param numRows       number of rows in the dungeon.
   * @param numCols       number of columns in the dungeon.
   * @param isWrap        Around true if the dungeon is wrap around, false otherwise.
   * @param connectivity  Type connectivity type of the dungeon.
   * @param treasurePerct treasure percentage of the dungeon.
   * @param monsterCount  number of monsters in the dungeon.
   * @throws IOException if the file is not found.
   */
  @Override
  public void resetGame(String numRows, String numCols, String isWrap, String connectivity,
                        String treasurePerct, String monsterCount) throws IOException {
    Random random = new Random(44);
    Player dp = new DungeonPlayer(RandomNumberGenerator.getRandomName(random),
            RandomNumberGenerator.getRandomCharacter(random));
    this.player = dp;
    int[][] arr = Node.getArrayOfRowsAndCol(Integer.parseInt(numCols), Integer.parseInt(numRows));
    Node[][] graph = Node.generateGraphFromArr(arr);
    Node head = graph[0][0];
    List<List<Node>> clouds = Node.addNodesToCloud(graph);
    List<List<Node>> nodePairs = new ArrayList<>();
    if (isWrap.equals("YES")) {
      nodePairs = head.getNodePairsWrapped();
    } else if (isWrap.equals("NO")) {
      nodePairs = head.getNodePairs();
    } else {
      throw new IllegalArgumentException("Invalid String entered!");
    }

    List<List<Node>> leftOver = new ArrayList<>();
    List<Node> allVertexPosition = Node.possiblePath(random, nodePairs, clouds, leftOver);
    int start_point = Dungeon.randomStartPoint(random, Integer.parseInt(numCols),
            Integer.parseInt(numRows));
    Node[][] resultGraph = Node.setVertexType(graph);
    int currentLocation = start_point;
    DungeonInterface dungeon = new Dungeon(Integer.parseInt(numRows), Integer.parseInt(numCols),
            allVertexPosition, start_point, currentLocation, isWrap, graph, connectivity);
    int end_point = dungeon.randomEndPoint(allVertexPosition, random, start_point, resultGraph,
            Integer.parseInt(numCols), Integer.parseInt(numRows));
    int max_location = Integer.parseInt(numRows) * Integer.parseInt(numCols);
    TreasureInterface treasure = new Treasure(max_location, Double.parseDouble(treasurePerct));
    WeaponInterface weapon = new Weapon(max_location, Double.parseDouble(treasurePerct), random);
    this.weapon = weapon;
    List<List<Node>> nodePairsForConnec = new ArrayList<>(nodePairs);
    nodePairs = Node.addPairsToAllVertex(random, nodePairsForConnec, leftOver,
            Integer.parseInt(connectivity));
    List<String> treasureList = treasure.assignTreasure(graph, random);
    MonsterInterface monster = new Monster(Integer.parseInt(monsterCount), random);
    this.monster = monster;
    String resultGraphWithDungeon = new String();
    resultGraphWithDungeon = Node.showDungeon(resultGraph);
    String showAllPairs = Node.showPairs(resultGraph);
    this.dungeon = dungeon;
    this.treasure = treasure;
    view.gameRestart(true);
    view.remove();
    view.refresh();
    playGame();
  }

  /**
   * This method depicts whether the game is over or not.
   *
   * @return true if the game is over, false otherwise.
   */
  @Override
  public boolean isGameOver() {
    return isGameOver;
  }
}