package viewnew;

import controller.GameInterface;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;


/**
 * This class represents the row of the dungeon panel. It takes the list of the images from the
 * main view class and pass it to the nodeview class to add the image of that node on the screen.
 */
public class NodeViewCollection extends JPanel {
  List<List<NodeView>> nodeViews;
  private GameInterface controller;

  /**
   * This method is used to set the controller of the game.
   *
   * @param controller Reference to the controller of the game.
   */
  public void setController(GameInterface controller) {
    this.controller = controller;
  }

  /**
   * This method is the constructor of the class. It takes the list of the images from the
   * main view class and pass it to the nodeView class to add the image of that node on the screen.
   *
   * @param paths List of the images of the nodes.
   * @throws IOException if any of the images are not found.
   */
  public NodeViewCollection(List<List<String>> paths) throws IOException {
    this.setLayout(new GridLayout(paths.size(), paths.get(0).size(), 5, 5));

    nodeViews = new java.util.ArrayList<>();
    int i = 0;
    for (List<String> path : paths) {
      List<NodeView> nodeRow = new ArrayList<>();
      for (String p : path) {
        NodeView nodeView = new NodeView(p);
        nodeRow.add(nodeView);
        int finalI = i;
        nodeView.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            try {
              controller.movePlayer(finalI + 1);
            } catch (IOException ex) {
              ex.printStackTrace();
            }
          }
        });
        this.add(nodeView);
        ++i;
      }
      nodeViews.add(nodeRow);
    }

  }

  /**
   * This method add the player to the screen by passing it position to the nodeView class.
   *
   * @param i Position of the player in the x axis.
   * @param j Position of the player in the y axis.
   */
  public void addPlayer(int i, int j) {
    nodeViews.get(i).get(j).addPlayer();
  }

  /**
   * This method is used to remove the player from the screen by passing the position to the
   * nodeView class.
   *
   * @param xPos Position of the player in the x axis.
   * @param yPos Position of the player in the y axis.
   */
  public void removePlayer(int xPos, int yPos) {
    nodeViews.get(xPos).get(yPos).removePlayer();
  }

  /**
   * This method is used to add the monster to the screen by passing the position to the
   * nodeView class.
   *
   * @param i Position of the monster in the x axis.
   * @param j Position of the monster in the y axis.
   */
  public void addMonster(int i, int j) {
    nodeViews.get(i).get(j).addMonster();
  }

  /**
   * This method is used to add the sapphires to the screen by passing the position to the
   * nodeView class.
   *
   * @param i Position of the sapphire in the x axis.
   * @param j Position of the sapphire in the y axis.
   */
  public void addTreasureSapphires(int i, int j) {
    nodeViews.get(i).get(j).addTreasureSapphires();
  }

  /**
   * This method is used to add the diamong to the screen by passing the position to the
   * nodeView class.
   *
   * @param i Position of the diamond in the x axis.
   * @param j Position of the diamond in the y axis.
   */
  public void addTreasureDiamond(int i, int j) {
    nodeViews.get(i).get(j).addTreasureDiamond();
  }

  /**
   * This method is used to add the images of the rubies to the screen by passing the position
   * to the nodeView class.
   *
   * @param i Position of the ruby in the x axis.
   * @param j Position of the ruby in the y axis.
   */
  public void addTreasureRubies(int i, int j) {
    nodeViews.get(i).get(j).addTreasureRubies();
  }

  /**
   * This method is used to add the image of the arrow on the screen by passing the position
   * to the nodeView class.
   *
   * @param i Position of the arrow in the x axis.
   * @param j Position of the arrow in the y axis.
   */
  public void addArrow(int i, int j) {
    nodeViews.get(i).get(j).addArrow();
  }

  /**
   * This method is used to remove the treasure image from the screen by passing the position
   * to the nodeView class.
   *
   * @param i Position of the treasure in the x axis.
   * @param j Position of the treasure in the y axis.
   */
  public void removeTreasure(int i, int j) {
    nodeViews.get(i).get(j).removeTreasure();
  }


  /**
   * This method is used to remove the arrow image from the screen by passing the position
   * to the nodeView class.
   *
   * @param i Position of the arrow in the x axis.
   * @param j Position of the arrow in the y axis.
   */
  public void removeArrow(int i, int j) {
    nodeViews.get(i).get(j).removeArrow();
  }

  /**
   * This method is used to add the less pungent smell to the screen by passing the position
   * to the nodeView class.
   *
   * @param i Position of the smell in the x axis.
   * @param j Position of the smell in the y axis.
   */
  public void addLessPungentSmell(int i, int j) {
    nodeViews.get(i).get(j).addLessPungentSmell();
  }

  /**
   * This method is used to add the more pungent smell to the screen by passing the position
   * to the nodeView class.
   * @param i Position of the smell in the x axis.
   * @param j Position of the smell in the y axis.
   */
  public void addMorePungentSmell(int i, int j) {
    nodeViews.get(i).get(j).addMorePungentSmell();
  }

  /**
   * This method is used to remove the less pungent smell from the screen by passing the position
   * to the nodeView class.
   *
   * @param i Position of the smell in the x axis.
   * @param j Position of the smell in the y axis.
   */
  public void removePungentSmell(int i, int j) {
    nodeViews.get(i).get(j).removeLessPungentSmell();
  }

  /**
   * This method is used to remove the more pungent smell from the screen by passing the position
   * to the nodeView class.
   *
   * @param i Position of the smell in the x axis.
   * @param j Position of the smell in the y axis.
   */
  public void removeMorePungentSmell(int i, int j) {
    nodeViews.get(i).get(j).removeMorePungentSmell();
  }

  /**
   * This method is used to add the arrow image to the screen by passing the position
   * to the nodeView class.
   *
   * @param i Position of the arrow in the x axis.
   * @param j Position of the arrow in the y axis.
   */
  public void addArrowInDungeon(int i, int j) {
    nodeViews.get(i).get(j).addArrowInDungeon();
  }

  /**
   * This method is used to remove the monster image from the screen by passing the position
   * to the nodeView class.
   *
   * @param i Position of the monster in the x axis.
   * @param j Position of the monster in the y axis.
   */
  public void removeMonster(int i, int j) {
    nodeViews.get(i).get(j).removeMonster();
  }

  /**
   * This method is used to add the theif image to the screen by passing the position
   * to the nodeView class.
   *
   * @param i Position of the thief in the x axis.
   * @param j Position of the thief in the y axis.
   */
  public void addThief(int i, int j) {
    nodeViews.get(i).get(j).addThief();
  }

  /**
   * This method is used to remove the theif image from the screen by passing the position
   * to the nodeView class.
   *
   * @param xPosThief Position of the thief in the x axis.
   * @param yPosThief Position of the thief in the y axis.
   */
  public void removeThief(int xPosThief, int yPosThief) {
    nodeViews.get(xPosThief).get(yPosThief).removeThief();
  }

  /**
   * This method is used to remove the moving monster image from the screen by passing the position
   * to the nodeView class.
   *
   * @param xPosMonster Position of the monster in the x axis.
   * @param yPosMonster Position of the monster in the y axis.
   */
  public void removeMovingMonster(int xPosMonster, int yPosMonster) {
    nodeViews.get(xPosMonster).get(yPosMonster).removeMovingMonster();
  }

  /**
   * This method is used to add the moving monster image to the screen by passing the position
   * to the nodeView class.
   *
   * @param i Position of the monster in the x axis.
   * @param j Position of the monster in the y axis.
   */
  public void addMovingMonster(int i, int j) {
    nodeViews.get(i).get(j).addMovingMonster();
  }

  /**
   * This method is used to add the pit image to the screen by passing the position
   * to the nodeView class.
   *
   * @param i Position of the pit in the x axis.
   * @param j Position of the pit in the y axis.
   */
  public void addPit(int i, int j) {
    nodeViews.get(i).get(j).addPit();
  }

  /**
   * This method is used to add the pit indicator image to the screen by passing the position
   * to the nodeView class.
   *
   * @param i Position of the pit in the x axis.
   * @param j Position of the pit in the y axis.
   */
  public void addPitIndicator(int i, int j) {
    nodeViews.get(i).get(j).addPitIndicator();
  }

  /**
   * This method is used to hide all the images of the node which in not travelled.
   *
   * @param i Position of the node in the x axis.
   * @param j Position of the node in the y axis.
   */
  public void addBlackImage(int i, int j) {
    nodeViews.get(i).get(j).addBlackImage();
  }

  /**
   * This method is used to show all the images of the node which in travelled.
   *
   * @param i Position of the node in the x axis.
   * @param j Position of the node in the y axis.
   */
  public void removeBlackImage(int i, int j) {
    nodeViews.get(i).get(j).removeBlackImage();
  }
}
