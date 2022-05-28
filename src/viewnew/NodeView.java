package viewnew;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * This class is used to display the node in the graph. It extends a JPanel and use it
 * draw the rows of the dungeon.
 */
public class NodeView extends JPanel {
  private String imagePath;
  private boolean isPlayerAlive;
  private boolean isMonsterAlive;
  private boolean isTreasureSapphires;
  private boolean isTreasureDiamond;
  private boolean isTreasureRubies;
  private boolean isArrow;
  private boolean isLessPungentSmell;
  private boolean isMorePungentSmell;
  private boolean isThief;
  private boolean isMovingMonster;
  private boolean isPit;
  private boolean isPitIndicator;
  private boolean isBlackImage;

  /**
   * This class represents the constructor of the NodeView class. It takes in the image path
   * and used Graphics to draw the image.
   *
   * @param imagePath Represents the image path of the node.
   */
  public NodeView(String imagePath) {
    this.imagePath = imagePath;
    this.isPlayerAlive = false;
    this.isMonsterAlive = false;
    this.isTreasureSapphires = false;
    this.isTreasureDiamond = false;
    this.isTreasureRubies = false;
    isMorePungentSmell = false;
    isLessPungentSmell = false;
    this.isThief = false;
    this.isMovingMonster = false;
    this.isPitIndicator = false;
    this.isPit = false;
    this.isBlackImage = false;
    this.setPreferredSize(new Dimension(150, 150));
  }

  /**
   * This method is called when ever repaint method is called. It adds the image of the node,
   * player image,treasures, arrows , monsters, thief, moving monster, pit, pit indicator.
   *
   * @param g Represents the Graphics object.
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    BufferedImage image = null;
    if (!this.isBlackImage) {
      try {
        image = ImageIO.read(Objects.requireNonNull(getClass()
                .getResource(imagePath)));

      } catch (IOException e) {
        e.printStackTrace();
      }
      g.drawImage(image, 0, 0, 150, 150, this);

      if (this.isTreasureDiamond) {
        try {
          image = ImageIO.read(Objects.requireNonNull(getClass()
                  .getResource("/diamond.png")));
        } catch (IOException e) {
          e.printStackTrace();
        }
        g.translate((this.getWidth() / 2) - 70, (this.getHeight() / 3) - 40);
        g.drawImage(image, 0, 0, 40, 40, this);
      }

      if (this.isTreasureSapphires) {
        try {
          image = ImageIO.read(Objects.requireNonNull(getClass()
                  .getResource("/emerald.png")));
        } catch (IOException e) {
          e.printStackTrace();
        }
        g.translate((this.getWidth() / 2) - 70, (this.getHeight() / 3) - 40);
        g.drawImage(image, 0, 0, 40, 40, this);
      }


      if (this.isArrow) {

        try {
          image = ImageIO.read(Objects.requireNonNull(getClass()
                  .getResource("/arrow-white.png")));
        } catch (IOException e) {
          e.printStackTrace();
        }
        g.drawImage(image, 1, 120, 30, 10, this);
      }

      if (this.isTreasureRubies) {
        try {
          image = ImageIO.read(Objects.requireNonNull(getClass()
                  .getResource("/ruby.png")));
        } catch (IOException e) {
          e.printStackTrace();
        }
        g.translate((this.getWidth() / 2) - 70, (this.getHeight() / 3) - 40);
        g.drawImage(image, 0, 0, 40, 40, this);
      }
      if (this.isThief) {
        try {
          image = ImageIO.read(Objects.requireNonNull(getClass()
                  .getResource("/thief.png")));
        } catch (IOException e) {
          e.printStackTrace();
        }
        g.drawImage(image, 1, 80, 80, 80, this);
      }
      if (this.isMonsterAlive) {
        try {
          image = ImageIO.read(Objects.requireNonNull(getClass()
                  .getResource("/otyugh.png")));
        } catch (IOException e) {
          e.printStackTrace();
        }
        g.translate((this.getWidth() / 2 - 25), (this.getHeight() / 2) - 55);
        g.drawImage(image, 0, 0, 60, 60, this);
      }

      if (this.isMovingMonster) {
        try {
          image = ImageIO.read(Objects.requireNonNull(getClass()
                  .getResource("/monsterMoving.png")));
        } catch (IOException e) {
          e.printStackTrace();
        }
        g.drawImage(image, 70, 40, 100, 100, this);
      }
      if (this.isPit) {
        try {
          image = ImageIO.read(Objects.requireNonNull(getClass()
                  .getResource("/pit1.png")));
        } catch (IOException e) {
          e.printStackTrace();
        }
        g.drawImage(image, 30, 20, 100, 100, this);
      }
      if (this.isPitIndicator) {
        try {
          image = ImageIO.read(Objects.requireNonNull(getClass()
                  .getResource("/pitIndicator.png")));
        } catch (IOException e) {
          e.printStackTrace();
        }
        g.drawImage(image, 110, 10, 50, 50, this);
      }
      if (this.isPlayerAlive) {
        try {
          image = ImageIO.read(Objects.requireNonNull(getClass()
                  .getResource("/shootPlayer.png")));
          g.translate((this.getWidth() / 2) - 30, (this.getHeight() / 2) - 60);
          g.drawImage(image, 0, 0, 80, 100, this);
          if (this.isLessPungentSmell) {
            try {
              BufferedImage stench = ImageIO.read(Objects.requireNonNull(getClass()
                      .getResource("/stench01.png")));
              g.drawImage(stench, -10, 15, 80, 100, this);
            } catch (IOException e) {
              e.printStackTrace();
            }

          } else if (this.isMorePungentSmell) {
            try {
              BufferedImage stench = ImageIO.read(Objects.requireNonNull(getClass()
                      .getResource("/stench02.png")));
              g.drawImage(stench, -10, 15, 80, 100, this);
            } catch (IOException e) {
              e.printStackTrace();
            }

          }

        } catch (IOException ioException) {
          ioException.printStackTrace();
        }
      }


    }


  }

  /**
   * This method is used to add the image of the player on the desired node.
   */
  public void addPlayer() {
    isPlayerAlive = true;
    this.repaint();
  }

  /**
   * This method is used to remove the image of the player on the desired node.
   */
  public void removePlayer() {
    isPlayerAlive = false;
    this.repaint();
  }

  /**
   * This method is used to add the image of the monster on the desired node.
   */
  public void addMonster() {
    isMonsterAlive = true;
    this.repaint();
  }

  /**
   * This method is used to add the sapphire image on the desired node.
   */
  public void addTreasureSapphires() {
    isTreasureSapphires = true;
    this.repaint();
  }


  /**
   * This method is used to add the diamond image on the desired node.
   */
  public void addTreasureDiamond() {
    isTreasureDiamond = true;
    this.repaint();
  }

  /**
   * This method is used to add the ruby image on the desired node.
   */
  public void addTreasureRubies() {
    isTreasureRubies = true;
    this.repaint();
  }

  /**
   * This method is used to add the image of arrow on the desired node.
   */
  public void addArrow() {
    isArrow = true;
    this.repaint();
  }

  /**
   * This method is used to remove the treasure image on the desired node.
   */
  public void removeTreasure() {
    isTreasureSapphires = false;
    isTreasureDiamond = false;
    isTreasureRubies = false;
    this.repaint();
  }

  /**
   * This method is used to remove the arrow image on the desired node.
   */
  public void removeArrow() {
    isArrow = false;
    this.repaint();
  }

  /**
   * This method is used to add the image of less pungent smell on the desired node.
   */
  public void addLessPungentSmell() {
    isLessPungentSmell = true;
    isMorePungentSmell = false;
    this.repaint();
  }

  /**
   * This method is used to add the image of more pungent smell on the desired node.
   */
  public void addMorePungentSmell() {
    isLessPungentSmell = false;
    isMorePungentSmell = true;
    this.repaint();
  }

  /**
   * This method is used to remove the less pungent smell image on the desired node.
   */

  public void removeLessPungentSmell() {
    isLessPungentSmell = false;
    this.repaint();
  }

  /**
   * This method is used to remove the more pungent smell image on the desired node.
   */
  public void removeMorePungentSmell() {
    isMorePungentSmell = false;
    this.repaint();
  }

  /**
   * This method is used to add the arrow image on the desired node.
   */
  public void addArrowInDungeon() {
    isArrow = true;
    this.repaint();
  }

  /**
   * This method is used to remove the monster image on the desired node.
   */
  public void removeMonster() {
    isMonsterAlive = false;
    this.repaint();
  }

  /**
   * This method is used to add the image of the thief on the desired node.
   */
  public void addThief() {
    isThief = true;
    this.repaint();
  }

  /**
   * This method is used to remove the thief image on the desired node.
   */
  public void removeThief() {
    isThief = false;
    this.repaint();
  }

  /**
   * This method is used to remove the image of the moving monster on the desired node.
   */
  public void removeMovingMonster() {
    isMovingMonster = false;
    this.repaint();
  }

  /**
   * This method is used to add the image of the moving monster on the desired node.
   */
  public void addMovingMonster() {
    isMovingMonster = true;
    this.repaint();
  }

  /**
   * This method is used to add the image of the pit on the desired node.
   */
  public void addPit() {
    isPit = true;
    this.repaint();
  }

  /**
   * This method is used to add the image of pit indicator on the desired node.
   */
  public void addPitIndicator() {
    isPitIndicator = true;
    this.repaint();
  }

  /**
   * This method hides all the images of the node which is not travelled.
   */
  public void addBlackImage() {
    isBlackImage = true;
    this.repaint();
  }

  /**
   * This method is used to show the image of the node which is travelled.
   */
  public void removeBlackImage() {
    isBlackImage = false;
    this.repaint();
  }
}
