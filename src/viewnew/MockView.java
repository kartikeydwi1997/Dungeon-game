package viewnew;


import controller.GameInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

/**
 * This class the main view of the game. It displays the game board and the
 * game panel with a scroll pane. It has methods which update the view and redraw it again.
 */
public class MockView implements DungeonView {
  JMenuBar menuBar;
  JMenu menu;
  JMenuItem menuItem;
  private NodeViewCollection nodeCollection;
  private int xPos;
  private int yPos;
  private int xPosThief;
  private int yPosThief;
  private int xPosMonster;
  private int yPosMonster;
  private GameInterface listener;
  private JLabel labelGameState;
  private JLabel labelPlayerPosition;
  private JLabel labelPlayerHealth;
  private JLabel labelMonsterHealth;
  private JLabel labelPlayerArrows;
  private JLabel labelPlayerRubies;
  private JLabel labelPlayerDiamonds;
  private JLabel labelPlayerSapphires;
  private JButton btnLeft;
  private JButton btnRight;
  private JButton btnUp;
  private JButton btnDown;
  private JButton btnMovePlayer;
  private JButton btnHitMonster;
  private JButton btnMoveCancel;
  private JButton btnPick;
  private JPanel btnPickCancel;
  private JButton btnShoot;
  private JComboBox shootDistance;
  private JButton btnShootCancel;
  private JButton btnShootArrow;
  private boolean gameBtnClicked;
  private boolean setShoot;
  private String shootDirection;
  private JPanel content;
  private boolean isGameRestart;

  /**
   * This method is the constructor of the class. It creates the main view of the game.
   * and adds the buttons to the panel.
   *
   * @throws IOException when any method gets any invalid value.
   */
  public MockView() throws IOException {
    content = new JPanel();
    isGameRestart = false;
    xPos = -1;
    yPos = -1;
    xPosThief = -1;
    yPosThief = -1;
    xPosMonster = -1;
    yPosMonster = -1;
    gameBtnClicked = false;
    addMenuBar();
    addBtn();
  }

  private void addBtn() {
    this.setShoot = false;
    this.shootDirection = "";
    btnLeft = new JButton(" LEFT  ");
    btnRight = new JButton("RIGHT");
    btnUp = new JButton("   UP   ");
    btnDown = new JButton("DOWN");
    btnMovePlayer = new JButton("MOVE");
    btnMovePlayer.setFont(new Font("Verdana", Font.BOLD, 13));
    btnMoveCancel = new JButton("CANCEL");
    btnMoveCancel.setFont(new Font("Verdana", Font.BOLD, 13));
    btnPick = new JButton("PICK");
    btnPick.setFont(new Font("Verdana", Font.BOLD, 13));
    btnPickCancel = new JPanel();
    btnPickCancel.setBackground(Color.pink);
    btnShoot = new JButton("SHOOT");
    btnShoot.setFont(new Font("Verdana", Font.BOLD, 13));
    btnShootCancel = new JButton("CANCEL");
    btnHitMonster = new JButton("HIT");
    btnHitMonster.setFont(new Font("Verdana", Font.BOLD, 13));
    btnShootCancel.setFont(new Font("Verdana", Font.BOLD, 13));
    Integer[] options = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    shootDistance = new JComboBox(options);
    shootDistance.setFont(new Font("Verdana", Font.BOLD, 13));
    btnShootArrow = new JButton("FIRE");
    btnShootArrow.setFont(new Font("Verdana", Font.BOLD, 13));


  }

  private void addMenuBar() {

    menuBar = new JMenuBar();
    menu = new JMenu("Game Menu");
    menu.setMnemonic(KeyEvent.VK_A);
    menuBar.add(menu);

    menuItem = new JMenuItem("Game Settings",
            KeyEvent.VK_T);
    menu.add(menuItem);
    menuItem.addMouseListener(new MouseAdapter() {

      /**
       * This method is called when the mouse is clicked.
       * @param evt the mouse event.
       */
      @Override
      public void mouseReleased(java.awt.event.MouseEvent evt) {
        try {
          callSettings();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      private void callSettings() throws IOException {
        JTextField rows = new JTextField(5);
        rows.setText("4");
        JTextField cols = new JTextField(5);
        cols.setText("4");
        JTextField connectivity = new JTextField(5);
        connectivity.setText("3");
        JTextField treasurePerct = new JTextField(5);
        treasurePerct.setText("50");
        JTextField monsterCount = new JTextField(5);
        monsterCount.setText("3");
        JPanel myPanel = new JPanel(new GridLayout(3, 1, 1, 1));
        myPanel.add(new JLabel("Rows:"));
        myPanel.add(rows);
        myPanel.add(new JLabel("Columns:"));
        myPanel.add(cols);
        String[] choices = {"NO", "YES"};
        JLabel lbl = new JLabel("Wrapping");
        myPanel.add(lbl);
        final JComboBox<String> cb = new JComboBox<String>(choices);
        myPanel.add(cb);
        myPanel.add(new JLabel("Connectivity:"));
        myPanel.add(connectivity);
        myPanel.add(new JLabel("Treasure Percentage:"));
        myPanel.add(treasurePerct);
        myPanel.add(new JLabel("Monster Count:"));
        myPanel.add(monsterCount);


        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Edit Configurations", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
          listener.resetGame(rows.getText(), cols.getText(), cb.getSelectedItem().toString(),
                  connectivity.getText(), treasurePerct.getText(), monsterCount.getText());
        }
      }

    });

    menuItem = new JMenuItem("Restart Game",
            KeyEvent.VK_T);
    menu.add(menuItem);
    menuItem.addMouseListener(new MouseAdapter() {
      /**
       * This method is called when the mouse is clicked.
       * @param evt the mouse event.
       */
      @Override
      public void mouseReleased(java.awt.event.MouseEvent evt) {

        try {
          listener.restartGame();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
    menuItem = new JMenuItem("Quit Game",
            KeyEvent.VK_T);
    menu.add(menuItem);
    menuItem.addMouseListener(new MouseAdapter() {

      /**
       * This method is called when the mouse is clicked.
       * @param evt the mouse event.
       */
      public void mouseReleased(java.awt.event.MouseEvent evt) {
        System.exit(0);
      }
    });


  }


  /**
   * This method is used to plot caves and tunnel images on the screen.
   *
   * @param imageName Represents the image name.
   * @param rows      Represents the number of rows.
   * @param cols      Represents the number of columns.
   * @throws IOException when the model gets any invalid input.
   */
  public void plotImage(List<String> imageName, int rows, int cols) throws IOException {
    int c = 0;

    List<List<String>> paths = new ArrayList<>();
    List<String> path = new ArrayList<>();
    for (int i = 0; i < rows; i++) {
      path = new ArrayList<>();
      for (int j = c; j < cols + c; j++) {
        path.add("images/" + imageName.get(j) + ".png");
      }
      paths.add(path);
      c += cols;
    }
    NodeViewCollection nodeViewCollection = new NodeViewCollection(paths);
    nodeViewCollection.setController(listener);
    this.nodeCollection = nodeViewCollection;


    content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
    Box[] boxes = new Box[3];
    boxes[0] = Box.createHorizontalBox();
    boxes[1] = Box.createHorizontalBox();
    boxes[2] = Box.createVerticalBox();
    boxes[0].createGlue();
    boxes[1].createGlue();
    boxes[2].createGlue();

    content.add(boxes[0]);
    content.add(boxes[1]);
    content.add(boxes[2]);

    JPanel panelDungeon = new JPanel();
    panelDungeon.add(nodeViewCollection);

    JScrollPane scrollPane = new JScrollPane(panelDungeon);
    scrollPane.setPreferredSize(new Dimension(100, 100));
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);


    JPanel panel2 = new JPanel();
    panel2.setBackground(Color.cyan);
    panel2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
    labelGameState = new JLabel("Welcome to the dungeon Game");
    labelGameState.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 18));
    panel2.add(labelGameState);

    JPanel panel3 = new JPanel();
    panel3 = new JPanel();
    panel3.setBackground(Color.YELLOW);
    panel3.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
    JPanel aA = new JPanel();
    aA.setBackground(Color.lightGray);
    aA.setBorder(new EmptyBorder(20, 20, 20, 20));
    aA.setLayout(new BoxLayout(aA, BoxLayout.Y_AXIS));
    aA.add(new JLabel("Player Stats:")).setFont(new Font("Verdana", Font.BOLD, 15));
    ImageIcon iconPlayerHealth = new ImageIcon("images/heart1.png");
    labelPlayerHealth = new JLabel("Player Health: ", iconPlayerHealth, JLabel.CENTER);
    labelPlayerHealth.setFont(new Font("Tahoma", Font.ITALIC, 13));
    aA.add(labelPlayerHealth);
    ImageIcon iconMonsterHealth = new ImageIcon("images/heart1.png");
    labelMonsterHealth = new JLabel("Monster Health: ", iconMonsterHealth, JLabel.CENTER);
    labelMonsterHealth.setFont(new Font("Tahoma", Font.ITALIC, 13));
    aA.add(labelMonsterHealth);
    ImageIcon iconPlayer = new ImageIcon("images/Hnet.com-image (4).png");
    labelPlayerPosition = new JLabel("Player Position: ", iconPlayer, JLabel.CENTER);
    labelPlayerPosition.setFont(new Font("Tahoma", Font.ITALIC, 13));
    aA.add(labelPlayerPosition);
    ImageIcon iconArrow = new ImageIcon("images/Hnet.com-image (2).png");
    labelPlayerArrows = new JLabel("Player Arrows: ", iconArrow, JLabel.CENTER);
    labelPlayerArrows.setFont(new Font("Tahoma", Font.ITALIC, 13));
    aA.add(labelPlayerArrows);
    ImageIcon iconRuby = new ImageIcon("images/ruby.png");
    labelPlayerRubies = new JLabel("Rubies: ", iconRuby, JLabel.CENTER);
    labelPlayerRubies.setFont(new Font("Tahoma", Font.ITALIC, 13));
    aA.add(labelPlayerRubies);
    ImageIcon iconDiamond = new ImageIcon("images/diamond.png");
    labelPlayerDiamonds = new JLabel("Diamonds: ", iconDiamond, JLabel.CENTER);
    labelPlayerDiamonds.setFont(new Font("Tahoma", Font.ITALIC, 13));
    aA.add(labelPlayerDiamonds);
    ImageIcon iconSapphires = new ImageIcon("images/emerald.png");
    labelPlayerSapphires = new JLabel("Sapphires: ", iconSapphires, JLabel.CENTER);
    labelPlayerSapphires.setFont(new Font("Tahoma", Font.ITALIC, 13));
    aA.add(labelPlayerSapphires);
    JPanel mainPanel = new JPanel();
    mainPanel.add(aA);
    panel3.add(mainPanel);

    JPanel bB = new JPanel();
    bB.setBorder(new EmptyBorder(20, 20, 20, 20));
    bB.setSize(new Dimension(150, 500));
    bB.setPreferredSize(new Dimension(130, 275));
    bB.setBorder(new EmptyBorder(20, 20, 20, 20));
    bB.setBackground(Color.orange);
    bB.setLayout(new BoxLayout(bB, BoxLayout.Y_AXIS));
    btnLeft.setFocusable(false);
    btnLeft.setFont(new Font("Verdana", Font.BOLD, 12));
    btnRight.setFocusable(false);
    btnRight.setFont(new Font("Verdana", Font.BOLD, 12));
    btnUp.setFocusable(false);
    btnUp.setFont(new Font("Verdana", Font.BOLD, 12));
    btnDown.setFocusable(false);
    btnDown.setFont(new Font("Verdana", Font.BOLD, 12));
    bB.add(btnLeft);
    bB.add(btnRight);
    bB.add(btnUp);
    bB.add(btnDown);
    panel3.add(bB);
    JPanel cC = new JPanel();
    cC.setBorder(new EmptyBorder(20, 20, 20, 20));
    cC.setSize(new Dimension(150, 500));
    cC.setPreferredSize(new Dimension(690, 275));
    cC.setBackground(Color.PINK);
    cC.setLayout(new GridLayout(3, 5));
    JLabel movePlayer = new JLabel("Move Player:");
    cC.add(movePlayer);
    btnMovePlayer.setFocusable(false);
    btnMoveCancel.setFocusable(false);
    JPanel moveDummy = new JPanel();
    moveDummy.setBackground(Color.pink);
    JPanel moveDummy2 = new JPanel();
    moveDummy2.setBackground(Color.pink);
    cC.add(btnMovePlayer);
    cC.add(btnMoveCancel);
    cC.add(moveDummy);
    cC.add(moveDummy2);
    JLabel pickUp = new JLabel("Pick/Hit Monster:");
    cC.add(pickUp);
    btnPick.setFocusable(false);
    btnHitMonster.setFocusable(false);
    btnPickCancel.setFocusable(false);
    JPanel pickDummy = new JPanel();
    pickDummy.setBackground(Color.pink);
    cC.add(btnPick);
    cC.add(btnHitMonster);
    cC.add(btnPickCancel);
    cC.add(pickDummy);
    JLabel shoot = new JLabel("Shoot:");
    cC.add(shoot);
    btnShoot.setFocusable(false);
    shootDistance.setFocusable(false);
    btnShootCancel.setFocusable(false);
    btnShootArrow.setFocusable(false);
    cC.add(btnShoot);
    cC.add(shootDistance);
    cC.add(btnShootArrow);
    cC.add(btnShootCancel);
    JPanel dD = new JPanel();
    dD.add(cC);
    panel3.add(dD);

    scrollPane.setPreferredSize(new Dimension(500, 500));
    boxes[0].add(scrollPane);
    boxes[1].add(panel2);
    boxes[2].add(panel3);

  }

  /**
   * This method is used to display the dungeon on the screen.
   */
  @Override
  public void makeVisible() {
    //
  }

  /**
   * This method is used to set the click listener on the controller
   * and listen for any click events.
   *
   * @param controller Represents the controller interface.
   */
  public void addClickListener(GameInterface controller) {
    this.listener = controller;
  }

  /**
   * This method is used to refresh the view after each event called.
   */
  @Override
  public void refresh() {
    //
  }

  /**
   * This method is used to remove all the components from the screen and re- render it.
   */
  @Override
  public void remove() {
    //
  }

  /**
   * This method is used to restart a new game with the same values provided earlier.
   *
   * @param b Represents the boolean value as true or false.
   */
  @Override
  public void gameRestart(boolean b) {
    isGameRestart = true;
    xPos = -1;
    yPos = -1;
    xPosThief = -1;
    yPosThief = -1;
    xPosMonster = -1;
    yPosMonster = -1;
  }

  /**
   * This method is used to set the click listener on the controller
   * and listen for any click events.
   *
   * @param controller Represents the controller interface.
   */
  public void addKeyListener(GameInterface controller) {
    btnLeft.setEnabled(false);
    btnRight.setEnabled(false);
    btnUp.setEnabled(false);
    btnDown.setEnabled(false);
    btnShootArrow.setEnabled(false);
    btnLeft.addActionListener(l -> {
      try {
        if (setShoot) {
          shootDirection = "left";
          btnShootArrow.setEnabled(true);
        } else {
          controller.move("left");
          btnMovePlayer.setEnabled(true);
          btnMovePlayer.setForeground(Color.BLACK);
          btnPick.setEnabled(true);
          btnShoot.setEnabled(true);
        }
        btnLeft.setEnabled(false);
        btnRight.setEnabled(false);
        btnUp.setEnabled(false);
        btnDown.setEnabled(false);

      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    btnRight.addActionListener(l -> {
      try {
        if (setShoot) {
          shootDirection = "right";
          btnShootArrow.setEnabled(true);
        } else {
          controller.move("right");
          btnMovePlayer.setEnabled(true);
          btnMovePlayer.setForeground(Color.BLACK);
          btnPick.setEnabled(true);
          btnShoot.setEnabled(true);
        }
        btnLeft.setEnabled(false);
        btnRight.setEnabled(false);
        btnUp.setEnabled(false);
        btnDown.setEnabled(false);

      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    btnUp.addActionListener(l -> {
      try {
        if (setShoot) {
          shootDirection = "up";
          btnShootArrow.setEnabled(true);
        } else {
          controller.move("up");
          btnMovePlayer.setEnabled(true);
          btnMovePlayer.setForeground(Color.BLACK);
          btnPick.setEnabled(true);
          btnShoot.setEnabled(true);
        }
        btnLeft.setEnabled(false);
        btnRight.setEnabled(false);
        btnUp.setEnabled(false);
        btnDown.setEnabled(false);

      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    btnDown.addActionListener(l -> {
      try {
        if (setShoot) {
          shootDirection = "down";
          btnShootArrow.setEnabled(true);
        } else {
          controller.move("down");
          btnMovePlayer.setEnabled(true);
          btnMovePlayer.setForeground(Color.BLACK);
          btnPick.setEnabled(true);
          btnShoot.setEnabled(true);
        }
        btnLeft.setEnabled(false);
        btnRight.setEnabled(false);
        btnUp.setEnabled(false);
        btnDown.setEnabled(false);

      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    btnMovePlayer.addActionListener(l -> {
      gameBtnClicked = true;
      btnMovePlayer.setEnabled(false);
      btnHitMonster.setEnabled(false);
      btnLeft.setEnabled(true);
      btnRight.setEnabled(true);
      btnUp.setEnabled(true);
      btnDown.setEnabled(true);
      btnShoot.setEnabled(false);
      btnPick.setEnabled(false);

    });
    btnMoveCancel.addActionListener(l -> {
      btnHitMonster.setEnabled(true);
      gameBtnClicked = false;
      btnMovePlayer.setEnabled(true);
      btnLeft.setEnabled(false);
      btnRight.setEnabled(false);
      btnUp.setEnabled(false);
      btnDown.setEnabled(false);
      btnShoot.setEnabled(true);
      btnPick.setEnabled(true);
    });
    btnPick.addActionListener(l -> {
      controller.pickup();
    });
    btnShoot.addActionListener(l -> {
      this.setShoot = true;
      btnHitMonster.setEnabled(false);
      btnShoot.setEnabled(false);
      btnMovePlayer.setEnabled(false);
      btnPick.setEnabled(false);
      btnShootArrow.setEnabled(false);
      btnLeft.setEnabled(true);
      btnRight.setEnabled(true);
      btnUp.setEnabled(true);
      btnDown.setEnabled(true);
    });
    btnShootArrow.addActionListener(l -> {
      this.setShoot = false;
      btnHitMonster.setEnabled(false);
      controller.shoot(shootDirection, (Integer) shootDistance.getSelectedItem());
      btnMovePlayer.setEnabled(true);
      btnPick.setEnabled(true);
      btnShoot.setEnabled(true);
      this.shootDirection = "";
      btnShootArrow.setEnabled(false);
    });
    btnHitMonster.addActionListener(l -> {
      controller.hitMonster();

    });

    btnShootCancel.addActionListener(l -> {
      this.setShoot = false;
      btnHitMonster.setEnabled(true);
      btnShootArrow.setEnabled(false);
      btnShoot.setEnabled(true);
      btnMovePlayer.setEnabled(true);
      btnPick.setEnabled(true);
      btnLeft.setEnabled(false);
      btnRight.setEnabled(false);
      btnUp.setEnabled(false);
      btnDown.setEnabled(false);
    });
  }

  /**
   * This method is used to plot the player on the screen.
   *
   * @param startPoint Represents the starting point of the player.
   * @param rows       Represents the number of rows.
   * @param cols       Represents the number of columns.
   * @throws IOException when the model gets any invalid input.
   */
  @Override
  public void plotPlayer(int startPoint, int rows, int cols) throws IOException {
    int add = 1;
    int[][] arr = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        arr[i][j] = add;
        add += 1;
      }
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (arr[i][j] == startPoint) {
          if (xPos == -1 && yPos == -1) {
            this.nodeCollection.addPlayer(i, j);
          } else {
            if (!isGameRestart || xPos != -1 && yPos != -1) {
              this.nodeCollection.removePlayer(xPos, yPos);
              this.nodeCollection.addPlayer(i, j);
            }
          }
          xPos = i;
          yPos = j;
        }
      }
    }

  }

  /**
   * This method is used to display game information on the screen.
   *
   * @param s string to be displayed.
   */
  @Override
  public void displayText(String s) {
    labelGameState.setText(s);
  }

  /**
   * This method is used to plot the monster on the screen based on the monster location provided.
   *
   * @param monsterLocation Represents the monster location.
   * @param rows            Represents the number of rows.
   * @param cols            Represents the number of columns.
   * @throws IOException when the model gets any invalid input.
   */
  @Override
  public void plotMonster(List<String> monsterLocation, int rows, int cols) throws IOException {
    int add = 0;
    String[][] arr = new String[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        arr[i][j] = monsterLocation.get(add);
        add += 1;
      }
    }

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (Objects.equals(arr[i][j], "MONSTER")
                || Objects.equals(arr[i][j], "MONSTER DAMAGED")) {
          this.nodeCollection.addMonster(i, j);
        }
      }
    }
  }

  /**
   * This method is used to plot the thief on the screen and keep updating the thief location.
   *
   * @param thiefPosition Represents the location of the thief.
   * @param rows          Represents the number of rows.
   * @param cols          Represents the number of columns.
   */
  @Override
  public void plotThief(int thiefPosition, int rows, int cols) {
    int add = 1;
    int[][] arr = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        arr[i][j] = add;
        add += 1;
      }
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (arr[i][j] == thiefPosition) {
          if (xPosThief == -1 && yPosThief == -1) {
            this.nodeCollection.addThief(i, j);
          } else {
            if (!isGameRestart || xPosThief != -1 && yPosThief != -1) {
              this.nodeCollection.removeThief(xPosThief, yPosThief);
              this.nodeCollection.addThief(i, j);
            }
          }
          xPosThief = i;
          yPosThief = j;
        }
      }
    }
  }

  /**
   * This method is used to plot the monster on the screen and keep updating the monster location.
   *
   * @param movingMonsterPosition Represents the location of the monster.
   * @param rows                  Represents the number of rows.
   * @param cols                  Represents the number of columns.
   */
  @Override
  public void plotMovingMonster(int movingMonsterPosition, int rows, int cols) {
    int add = 1;
    int[][] arr = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        arr[i][j] = add;
        add += 1;
      }
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (arr[i][j] == movingMonsterPosition) {
          if (xPosMonster == -1 && yPosMonster == -1) {
            this.nodeCollection.addMovingMonster(i, j);
          } else {
            if (!isGameRestart || xPosMonster != -1 && yPosMonster != -1) {
              this.nodeCollection.removeMovingMonster(xPosMonster, yPosMonster);
              this.nodeCollection.addMovingMonster(i, j);
            }
          }
          xPosMonster = i;
          yPosMonster = j;
        }
      }
    }
  }

  /**
   * This method is used to get the monster health all the time monster being hit by the player.
   *
   * @param movingMonsterHealth Represents the health of the monster.
   */

  @Override
  public void showMonsterHealth(int movingMonsterHealth) {
    labelMonsterHealth.setText("Monster Health: " + movingMonsterHealth);
  }

  /**
   * This method is used to get the player health on the screen and keep updating the player
   * health.
   *
   * @param playerHealth Represents the health of the player.
   */
  @Override
  public void showPlayerHealth(int playerHealth) {
    labelPlayerHealth.setText("Player Health: " + playerHealth);
  }

  /**
   * This method is used to update the monster position by removing the monster from its previous
   * position and adding it to the new position.
   *
   * @param movingMonsterPosition Represents the location of the monster.
   * @param rows                  Represents the number of rows.
   * @param cols                  Represents the number of columns.
   */
  @Override
  public void removeMovingMonster(int movingMonsterPosition, int rows, int cols) {
    int add = 1;
    int[][] arr = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        arr[i][j] = add;
        add += 1;
      }
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (arr[i][j] == movingMonsterPosition) {
          this.nodeCollection.removeMovingMonster(i, j);
        }
      }
    }
  }

  /**
   * This method adds a pit in the dungeon where the player might fall and player health reduces
   * to half.
   *
   * @param pitPosition Represents the location of the pit.
   * @param rows        Represents the number of rows.
   * @param cols        Represents the number of columns.
   */
  @Override
  public void plotPit(int pitPosition, int rows, int cols) {
    int add = 1;
    int[][] arr = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        arr[i][j] = add;
        add += 1;
      }
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (arr[i][j] == pitPosition) {
          this.nodeCollection.addPit(i, j);
        }
      }
    }
  }

  /**
   * This method is used to indicate the player about the pit location and aware the player
   * that the pit is nearby.
   *
   * @param currentPoint Represents the location of the player.
   * @param rows         Represents the number of rows.
   * @param cols         Represents the number of columns.
   */
  @Override
  public void plotPitIndicator(int currentPoint, int rows, int cols) {
    int add = 1;
    int[][] arr = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        arr[i][j] = add;
        add += 1;
      }
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (arr[i][j] == currentPoint) {
          this.nodeCollection.addPitIndicator(i, j);
        }
      }
    }
  }

  /**
   * This method plot the black screen at the start of the game to hide the game and create more
   * suspense.
   *
   * @param startPoint Represents the location of the player.
   * @param rows       Represents the number of rows.
   * @param cols       Represents the number of columns.
   */
  @Override
  public void plotBlackImage(int startPoint, int rows, int cols) {
    int add = 1;
    int[][] arr = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        arr[i][j] = add;
        add += 1;
      }
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (arr[i][j] != startPoint) {
          this.nodeCollection.addBlackImage(i, j);
        }
      }
    }
  }

  /**
   * This method is used to remove the image from the screen when the player travel through that
   * cell.
   *
   * @param currentPoint Represents the location of the player.
   * @param rows         Represents the number of rows.
   * @param cols         Represents the number of columns.
   */
  @Override
  public void removeBlackImage(int currentPoint, int rows, int cols) {
    int add = 1;
    int[][] arr = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        arr[i][j] = add;
        add += 1;
      }
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (arr[i][j] == currentPoint) {
          this.nodeCollection.removeBlackImage(i, j);
        }
      }
    }
  }

  /**
   * This method is used to plot the treasure on the screen based on the treasure location provided.
   *
   * @param treasureList Represents the treasure location.
   * @param rows         Represents the number of rows.
   * @param cols         Represents the number of columns.
   */
  @Override
  public void plotTreasure(List<String> treasureList, int rows, int cols) {
    int add = 0;
    String[][] arr = new String[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        arr[i][j] = treasureList.get(add);
        add += 1;
      }
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (Objects.equals(arr[i][j], "SAPPHIRES")) {
          this.nodeCollection.addTreasureSapphires(i, j);
        }
        if (Objects.equals(arr[i][j], "DIAMOND")) {
          this.nodeCollection.addTreasureDiamond(i, j);
        }
        if (Objects.equals(arr[i][j], "RUBIES")) {
          this.nodeCollection.addTreasureRubies(i, j);
        }
      }
    }
  }

  /**
   * This method is used to plot the arrow on the screen based on the arrow location provided.
   *
   * @param weaponList Represents the arrow location.
   * @param rows       Represents the number of rows.
   * @param cols       Represents the number of columns.
   */
  @Override
  public void plotArrows(List<List<String>> weaponList, int rows, int cols) {
    int add = 0;
    String[][] arr = new String[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        arr[i][j] = String.valueOf(weaponList.get(add));
        add += 1;
      }
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (arr[i][j].length() > 6) {
          int count = arr[i][j].length() / 7;
          while (count > 0) {
            this.nodeCollection.addArrow(i, j);
            count--;
          }
        }
      }

    }
  }

  /**
   * This method update the player position after each move.
   *
   * @param currentPoint Represents the current position of the player.
   */
  @Override
  public void showPlayerLocation(int currentPoint) {
    labelPlayerPosition.setText("Player Location: " + currentPoint);


  }

  /**
   * This method is used to update the player arrow count on the screen.
   *
   * @param numArrows Represents the number of arrows.
   */
  @Override
  public void showPlayerArrows(int numArrows) {
    labelPlayerArrows.setText("Player Arrows: " + numArrows);
  }

  /**
   * This method is used to update the player treasure count on the screen.
   *
   * @param playerTreasure Represents the number of treasures.
   */
  @Override
  public void showPlayerTreasure(List<String> playerTreasure) {
    int numSapphires = 0;
    int numRubies = 0;
    int numDiamonds = 0;
    for (String s : playerTreasure) {
      if (s.equals("SAPPHIRES")) {
        numSapphires++;
      }
      if (s.equals("RUBIES")) {
        numRubies++;
      }
      if (s.equals("DIAMOND")) {
        numDiamonds++;
      }
    }
    labelPlayerRubies.setText("Rubies: " + numRubies);
    labelPlayerDiamonds.setText("Diamonds:" + numDiamonds);
    labelPlayerSapphires.setText("Sapphires: " + numSapphires);
  }

  /**
   * This method is used to remove the treasure from the screen when the player
   * collects that treasure.
   *
   * @param currentLocation Represents the current location of the player.
   * @param rows            Represents the number of rows.
   * @param cols            Represents the number of columns.
   */
  @Override
  public void removeTreasure(int currentLocation, int rows, int cols) {
    int add = 1;
    int[][] arr = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        arr[i][j] = add;
        add += 1;
      }
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (arr[i][j] == currentLocation) {
          this.nodeCollection.removeTreasure(i, j);
        }
      }
    }
  }

  /**
   * This method is used to remove arrow from any position on the screen when the player
   * collects that arrow.
   *
   * @param currentLocation Represents the current location of the player.
   * @param rows            Represents the number of rows.
   * @param cols            Represents the number of columns.
   */
  @Override
  public void removeArrow(int currentLocation, int rows, int cols) {
    int add = 1;
    int[][] arr = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        arr[i][j] = add;
        add += 1;
      }
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (arr[i][j] == currentLocation) {
          this.nodeCollection.removeArrow(i, j);
        }
      }
    }
  }

  /**
   * This method is used to show the less pungent smell of the monster when the player is two
   * distance away from the monster.
   *
   * @param currentPoint Represents the current location of the player.
   * @param rows         Represents the number of rows.
   * @param cols         Represents the number of columns.
   */
  @Override
  public void showLessPungentSmell(int currentPoint, int rows, int cols) {
    int add = 1;
    int[][] arr = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        arr[i][j] = add;
        add += 1;
      }
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (arr[i][j] == currentPoint) {
          this.nodeCollection.addLessPungentSmell(i, j);
        }
      }
    }

  }

  /**
   * This method is used to show the more pungent smell of the monster when the player is one
   * distance away from the monster.
   *
   * @param currentPoint Represents the current location of the player.
   * @param rows         Represents the number of rows.
   * @param cols         Represents the number of columns.
   */
  @Override
  public void showMorePungentSmell(int currentPoint, int rows, int cols) {
    int add = 1;
    int[][] arr = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        arr[i][j] = add;
        add += 1;
      }
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (arr[i][j] == currentPoint) {

          this.nodeCollection.addMorePungentSmell(i, j);
        }
      }
    }
  }

  /**
   * This method is used to remove the less pungent smell of the monster from the previous position
   * where the player was.
   *
   * @param previousLocation Represents the previous location of the player.
   * @param rows             Represents the number of rows.
   * @param cols             Represents the number of columns.
   */
  @Override
  public void removeLessPungentSmell(int previousLocation, int rows, int cols) {
    int add = 1;
    int[][] arr = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        arr[i][j] = add;
        add += 1;
      }
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (arr[i][j] == previousLocation) {
          this.nodeCollection.removePungentSmell(i, j);
        }
      }
    }
  }

  /**
   * This method is used to remove the more pungent smell of the monster from the previous position
   * where the player was.
   *
   * @param previousLocation Represents the previous location of the player.
   * @param rows             Represents the number of rows.
   * @param cols             Represents the number of columns.
   */
  @Override
  public void removeMorePungentSmell(int previousLocation, int rows, int cols) {
    int add = 1;
    int[][] arr = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        arr[i][j] = add;
        add += 1;
      }
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (arr[i][j] == previousLocation) {
          this.nodeCollection.removeMorePungentSmell(i, j);
        }
      }
    }
  }

  /**
   * This method is used to add an arrow when player shot an arrow in any cave.
   *
   * @param arrowLocation Represents the location of the arrow.
   * @param rows          Represents the number of rows.
   * @param cols          Represents the number of columns.
   */
  @Override
  public void addArrow(int arrowLocation, int rows, int cols) {
    int add = 1;
    int[][] arr = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        arr[i][j] = add;
        add += 1;
      }
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (arr[i][j] == arrowLocation) {

          this.nodeCollection.addArrowInDungeon(i, j);
        }
      }
    }
  }

  /**
   * This method is used to remove the monster from the screen when the player has killed that
   * monster.
   *
   * @param arrowLocation Represents the location of the arrow.
   * @param rows          Represents the number of rows.
   * @param cols          Represents the number of columns.
   */
  @Override
  public void removeMonster(int arrowLocation, int rows, int cols) {
    int add = 1;
    int[][] arr = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        arr[i][j] = add;
        add += 1;
      }
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (arr[i][j] == arrowLocation) {
          this.nodeCollection.removeMonster(i, j);
        }
      }
    }
  }

  private class MyKeyListener implements KeyListener {
    private final GameInterface controller;

    private String shootDirection;

    /**
     * This is the constructor of the class. It sets the controller.
     *
     * @param controller Represents the controller.
     */
    public MyKeyListener(GameInterface controller) {
      this.controller = controller;
    }


    /**
     * This method is called when a key is pressed.
     *
     * @param e Represents the key event.
     */
    @Override
    public void keyTyped(KeyEvent e) {
      //keyTyped
    }

    /**
     * This method is called when a key is pressed.
     *
     * @param e Represents the key event.
     */
    @Override
    public void keyPressed(KeyEvent e) {

      if (setShoot) {
        if (e.getKeyCode() == KeyEvent.VK_1) {
          controller.shoot(shootDirection, 1);
          setShoot = false;
        }
      }

      if (setShoot) {
        if (e.getKeyCode() == KeyEvent.VK_2) {
          controller.shoot(shootDirection, 2);
          setShoot = false;
        }
      }

      if (setShoot) {
        if (e.getKeyCode() == KeyEvent.VK_3) {
          controller.shoot(shootDirection, 3);
          setShoot = false;
        }
      }

      if (setShoot) {
        if (e.getKeyCode() == KeyEvent.VK_4) {
          controller.shoot(shootDirection, 4);
          setShoot = false;
        }
      }

      if (setShoot) {
        if (e.getKeyCode() == KeyEvent.VK_5) {
          controller.shoot(shootDirection, 5);
          setShoot = false;
        }
      }
      if (e.getKeyCode() == KeyEvent.VK_UP) {
        if (setShoot) {
          shootDirection = "up";
        } else {
          try {
            controller.move("up");
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }
      }
      if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        if (setShoot) {
          shootDirection = "down";
        } else {
          try {
            controller.move("down");
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }
      }
      if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        if (setShoot) {
          shootDirection = "left";
        } else {
          try {
            controller.move("left");
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }
      }
      if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        if (setShoot) {
          shootDirection = "right";
        } else {
          try {
            controller.move("right");
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }
      }
      if (e.getKeyCode() == KeyEvent.VK_P) {

        controller.pickup();
      }
      if (e.getKeyCode() == KeyEvent.VK_H) {

        controller.hitMonster();
      }
      if (e.getKeyCode() == KeyEvent.VK_S) {
        setShoot = true;
      }
      if (e.getKeyCode() == KeyEvent.VK_C) {
        setShoot = false;
      }
    }

    /**
     * This method is called when a key is released.
     *
     * @param e Represents the key event.
     */
    @Override
    public void keyReleased(KeyEvent e) {
      //keyReleased
    }


  }
}

