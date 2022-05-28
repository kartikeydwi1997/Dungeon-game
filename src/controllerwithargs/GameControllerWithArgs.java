package controllerwithargs;


import entry.DungeonInterface;
import entry.MonsterInterface;
import entry.Node;
import entry.Player;
import entry.TreasureInterface;
import entry.WeaponInterface;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This is the controller class which takes all the model interfaces as the input and perform the
 * game operations by calling the model interfaces.
 */
public class GameControllerWithArgs implements GameInterfaceWithArgs {
  private final Appendable out;
  private final Scanner scan;

  /**
   * This is the constructor of the GameController class.It takes the input as readable in and out
   * and append all the operations to the output.
   *
   * @param in  Represents the input passed from the main class.
   * @param out Represents the output passed from the main class.
   */
  public GameControllerWithArgs(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    scan = new Scanner(in);
  }

  /**
   * This method is used to start the game and ask user input and play game until either the player
   * is dead or the player reaches the end location.
   *
   * @param dungeon  the dungeon represents the entire implementation of dungeon and how the graph
   *                 is formed.
   * @param treasure Represents the treasure that the player will be trying to collect.
   * @param monster  Represents the monster that the player will be trying to defeat.
   * @param player   Represents the player that will be trying to collect the treasure and defeat
   * @param weapon   Represents the weapon that the player will be using to defeat the monster.
   * @throws IOException if the output stream is closed.
   */
  public void playGame(
          DungeonInterface dungeon,
          TreasureInterface treasure,
          MonsterInterface monster,
          Player player,
          WeaponInterface weapon) {
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

    appendWithException("Welcome to the dungeon!\n", "");
    appendWithException("Player Info:", player.toString());
    appendWithException("Rows entered: ", dungeon.getRowsInString());
    appendWithException("Columns entered: ", dungeon.getColsInString());
    appendWithException("Dungeon Wrapping: ", dungeon.getIsWrap());
    appendWithException("Start Point: ", dungeon.getStartPointInString());
    appendWithException("End Point: ", dungeon.getEndPointInString());
    appendWithException("Connectivity: ", dungeon.getConnectivity());
    appendWithException("Treasure: ", treasure.toString());
    try {
      this.out.append("Treasure List: " + treasure.getTreasureList()).append("\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Treasure List cannot be null!!");
    }
    appendWithException("Monster: ", monster.toString());

    Node[][] graph = dungeon.getGraph();
    int end_point = dungeon.getEndPoint();
    int start_point = dungeon.getStartPoint();
    int maxLocation = dungeon.getRows() * dungeon.getCols();
    int thiefPosition = monster.findThiefPosition(maxLocation, start_point);

    // assign monster to list
    monster.assignMonster(end_point, graph, maxLocation, start_point);
    int movingMonsterPosition = monster.findMovingMonster(maxLocation, start_point, end_point);
    try {
      this.out.append("Monster position: " + monster.getMonsterLocation()).append("\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid input");
    }
    String allTreasure = new String();

    try {
      this.out.append("Weapon List: " + weapon.assignWeapon()).append("\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid input");
    }
    int currentLocation = dungeon.getCurrentPoint();
    while (currentLocation != end_point) {
      try {
        this.out.append("Current Location is: " + dungeon.getCurrentPoint()).append("\n");
      } catch (IOException e) {
        throw new IllegalArgumentException("Invalid input");
      }
      if (monster.checkMonsterExist(currentLocation)) {
        player.setPlayerAlive(false);
        try {
          this.out.append("You have encountered a monster!").append("\n");
          this.out.append("Chomp, chomp, chomp, you are eaten by an Otyugh!").append("\n");
          break;
        } catch (IOException e) {
          throw new IllegalArgumentException("Invalid input");
        }
      }

      if (monster.checkMonsterDamaged(currentLocation)) {
        try {
          this.out.append("You have encountered a monster!").append("\n");
          this.out.append("Luck is in your pocket!! Monster won't kill you").append("\n");
        } catch (IOException e) {
          throw new IllegalArgumentException("Invalid input");
        }
      } else {
        if (!monster.checkNoMonster(currentLocation)) {
          try {
            player.setPlayerAlive(false);
            this.out.append("You have encountered a monster!").append("\n");
            this.out.append("Chomp, chomp, chomp, you are eaten by an Otyugh!").append("\n");
            break;
          } catch (IOException e) {
            throw new IllegalArgumentException("Invalid input");
          }
        }
      }
      try {
        this.out.append(monster.checkMonster(currentLocation, maxLocation, dungeon)).append("\n");
      } catch (IOException e) {
        throw new IllegalArgumentException("Invalid input");
      }
      dungeon.movePlayer(graph);

      List<Node> allPath = dungeon.getPossiblePath();
      List<String> allDirections = dungeon.getAllDirections();
      int i = 0;
      appendWithException("Move,Pickup,Shoot,Hit or Quit (M-P-S-H-Q)? ", "\n");
      boolean validInput = false;
      while (!validInput) {
        String userInput = "";
        try {
          userInput = scan.next();
        } catch (NoSuchElementException e) {
          throw new IllegalArgumentException("No valid input passed game discontinued!!");
        }
        if (userInput.equals("M")) {
          validInput = true;
          int iterate = 1;
          for (Node n : allPath) {
            try {
              this.out
                      .append(
                              iterate
                                      + ". "
                                      + "Possible Path to: "
                                      + n
                                      + " in direction: "
                                      + allDirections.get(i))
                      .append("\n");
              iterate++;
              i++;
            } catch (IOException e) {
              throw new IllegalArgumentException("Invalid input");
            }
          }
          try {
            this.out
                    .append("Where do you want to move? Type any digit between 1 and "
                            + allPath.size()).append("\n");
          } catch (IOException e) {
            throw new IllegalArgumentException("Invalid input");
          }
          int your_input = 0;
          while (your_input == 0) {

            try {
              String input = scan.next();
              your_input = Integer.parseInt(input);
              thiefPosition = monster.findThiefPosition(maxLocation, start_point);
              movingMonsterPosition = monster.findMovingMonster(maxLocation,
                      start_point, end_point);
              currentLocation = dungeon.validateUserInput(your_input, allPath);
              if (currentLocation == thiefPosition) {
                player.removePlayerTreasure();
                appendWithException("You encounter a thief and all "
                        + "your treasure is stolen!!", "\n");
              }
              if (currentLocation == movingMonsterPosition && monster.isMovingMonsterAlive()) {
                if (monster.getMovingMonsterHealth() > 0) {
                  appendWithException("You have encountered a moving monster and the monster hit "
                          + "you", "\n");
                  if (player.getPlayerHealth() > 0) {
                    player.updatePlayerHealth(10);
                    int currentPlayerHealth = player.getPlayerHealth();
                    appendWithException("Your health is now: " + currentPlayerHealth, "\n");
                  } else {
                    player.setPlayerAlive(false);
                    appendWithException("You have been killed by a moving monster!!", "\n");
                  }
                }
              }
              break;
            } catch (IllegalArgumentException e1) {
              your_input = 0;
              appendWithException("Invalid input, try again: ", "\n");
            }
          }
        } else if (userInput.equals("P")) {
          // Arrow picked up
          if (weapon.checkWeapon(currentLocation)) {
            int count = weapon.getWeaponList().get(currentLocation - 1).size();
            player.updateArrowCount(count);
            weapon.updateWeaponList(currentLocation);
            appendWithException("You picked up an arrow!", "\n");

          } else {
            appendWithException("There is no weapon here!", "\n");
          }

          // Treasure picked up
          allTreasure = treasure.treasureAtIndex(currentLocation - 1);

          if (allTreasure.equals("T") || allTreasure.equals("NT")) {
            // DO NOTHING
          } else {
            appendWithException("You picked up a treasure!", "\n");
            player.setPlayerTreasure(allTreasure);
          }
          try {
            this.out.append("Total arrows present:" + player.getNumArrows()).append("\n");
            this.out.append("Total treasure: " + player.getPlayerTreasure()).append("\n");
          } catch (IOException e) {
            throw new IllegalArgumentException("Invalid input");
          }
          validInput = true;

        } else if (userInput.equals("S")) {
          int iterate = 1;
          if (player.hasArrows()) {
            for (String n : allDirections) {
              try {
                this.out.append(iterate + ". " + "Possible directions to shoot:" + n).append("\n");
                iterate++;
              } catch (IOException e) {
                throw new IllegalArgumentException("Invalid input");
              }
            }
            appendWithException("Shoot an arrow! Enter the direction i.e N,S,E,W", "\n");
            boolean directionValid = false;
            String direction = "";
            String distance = "";
            while (!directionValid) {
              try {
                direction = scan.next();
                if (dungeon.validDirection(direction, allDirections)) {
                  directionValid = true;
                } else {
                  appendWithException("Please enter a valid direction", "\n");
                }
              } catch (IllegalArgumentException e) {
                appendWithException("Invalid direction! Please enter a valid direction", "\n");
              }
            }
            // Distance input
            appendWithException("Enter the distance you want to shoot: ", "\n");
            boolean distanceValid = false;
            while (!distanceValid) {
              try {
                distance = scan.next();
                if (dungeon.validDistance(Integer.parseInt(distance), maxLocation)) {
                  distanceValid = true;
                } else {
                  appendWithException("Please enter a valid distance", "\n");
                }
              } catch (NumberFormatException e) {
                appendWithException("Invalid distance! Please enter a valid distance", "\n");
              }
            }
            int arrowLocation =
                    dungeon.shootArrow(direction, distance, currentLocation, graph, maxLocation);
            try {
              this.out.append("Arrow Location:" + arrowLocation).append("\n");
            } catch (IOException e) {
              throw new IllegalArgumentException("Invalid input");
            }
            if (dungeon.isArrowMissed(arrowLocation)) {
              appendWithException("Arrow missed!", "\n");
            } else if (monster.checkNoMonster(arrowLocation)) {
              appendWithException("You wasted your arrow!!", "\n");
            } else {
              if (monster.checkMonsterExist(arrowLocation)
                      || !monster.checkMonsterDead(arrowLocation)) {
                appendWithException("Monster is hit!", "\n");
                monster.damageMonster(arrowLocation);
                if (monster.checkMonsterDead(arrowLocation)) {
                  appendWithException("Monster is dead!", "\n");
                } else {
                  appendWithException("Monster is still alive but injured!", "\n");
                }
              }
            }

            player.updateArrowCount(-1);
            try {
              weapon.addWeaponToList(arrowLocation);
            } catch (IllegalArgumentException e) {

              try {
                this.out.append("Arrow was missed and lost in the darkness").append("\n");
              } catch (IOException ex) {
                throw new IllegalArgumentException("Arrow was missed and lost in the darkness");
              }
            }
            try {
              this.out.append("Total arrows present:" + player.getNumArrows()).append("\n");
              this.out.append("weapon list:" + weapon.getWeaponList()).append("\n");
              this.out.append("Monster location:" + monster.getMonsterLocation()).append("\n");
            } catch (IOException ex) {
              throw new IllegalArgumentException("Invalid values received!!");
            }
            validInput = true;
          } else {
            appendWithException("You don't have any arrows left!", "\n");
          }
        } else if (userInput.equals("H")) {
          int playerHealth = player.getPlayerHealth();
          if (playerHealth > 0) {
            monster.updateMonsterHealth();
            if (monster.getMovingMonsterHealth() > 0) {
              appendWithException("You caused damaged to the moving monster of"
                      + monster.getMovingMonsterHealth(), "\n");

            } else {
              monster.setMovingMonsterAlive(false);
              appendWithException("You have killed the moving monster", "\n");
            }
          } else {
            player.setPlayerAlive(false);
            appendWithException("You have been killed by a moving monster!!", "\n");
          }
          if (monster.isMovingMonsterAlive()) {
            appendWithException("Monster health:" + monster.getMovingMonsterHealth(), "\n");
            if (player.getPlayerHealth() > 0) {
              appendWithException("Player health:" + player.getPlayerHealth(), "\n");
              player.updatePlayerHealth(10);
              appendWithException("Moving monster hit you and health reduced to"
                      + player.getPlayerHealth(), "\n");
            }
          }
        } else if (userInput.equals("Q")) {
          System.exit(0);
        } else {
          appendWithException("Invalid input, please try again!", "\n");
        }
      }
    }
    if (player.isAlive()) {
      if (monster.checkMonsterExist(currentLocation)) {
        player.setPlayerAlive(false);
        try {
          this.out.append("You have encountered a monster!").append("\n");
          this.out.append("Chomp, chomp, chomp, you are eaten by an Otyugh!").append("\n");

        } catch (IOException e) {
          throw new IllegalArgumentException("Invalid input");
        }
      } else if (monster.checkNoMonster(currentLocation)) {
        try {
          this.out.append("Game over!!!").append("\n");
          this.out.append("You have reached to your end_point " + end_point).append("\n");
          this.out
                  .append("You managed to get these treasures:  " + player.getPlayerTreasure())
                  .append("\n");
        } catch (IOException e) {
          throw new IllegalArgumentException("Invalid input");
        }
      } else if (monster.checkMonsterDamaged(currentLocation)) {
        try {
          this.out.append("You have encountered a monster!").append("\n");
          this.out.append("Luck is in your pocket!! Monster won't kill you").append("\n");
          this.out.append("Game over!!!").append("\n");
          this.out.append("You have reached to your end_point " + end_point).append("\n");
          this.out
                  .append("You managed to get these treasures:  " + player.getPlayerTreasure())
                  .append("\n");
        } catch (IOException e) {
          throw new IllegalArgumentException("Invalid input");
        }
      } else {
        if (!monster.checkNoMonster(currentLocation)) {
          try {
            player.setPlayerAlive(false);
            this.out.append("You have encountered a monster!").append("\n");
            this.out.append("Chomp, chomp, chomp, you are eaten by an Otyugh!").append("\n");

          } catch (IOException e) {
            throw new IllegalArgumentException("Invalid input");
          }
        }
      }
      allTreasure = treasure.treasureAtIndex(currentLocation - 1);
      if ((allTreasure.equals("T")) || (allTreasure.equals("NT"))) {
        //
      } else {
        player.setPlayerTreasure(allTreasure);
      }
      if (weapon.getWeaponList().get(currentLocation - 1).equals("Arrow")) {
        player.updateArrowCount(1);
        weapon.updateWeaponList(currentLocation);
      }
    } else {
      try {
        this.out.append("Game over!!!").append("\n");
        this.out
                .append("You managed to get these treasures:  " + player.getPlayerTreasure())
                .append("\n");
      } catch (IOException ex) {
        throw new IllegalArgumentException("Invalid values received");
      }
    }
  }

  private Appendable appendWithException(String playerInfo, String toString) {
    if (playerInfo == null || toString == null) {
      throw new IllegalArgumentException("Invalid input");
    }
    try {
      this.out.append(playerInfo).append(toString).append("\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid input");
    }
    return this.out;
  }
}
