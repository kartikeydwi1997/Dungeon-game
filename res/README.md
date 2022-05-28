# About the project


This project is a game called Dungeon Game. To be build in a MVC pattern. In the previous project we achived to build the model of the project. In this project we have build the controller for this project which will interact with the user and take user input and passs it to the model to perform the desired operations. The game has solitary creatures Otyugh which are placed in cave and will kill the player when player will pass from their destination cave. In order to escape from monster, player has a skill to detect the smell of the monster (i.e if monster is near two distance, its a less pungent smell, if monster is at one distance, it's a more pungent smell, if their is no monster near the player than their is no smell.) Player is also provided will crooked arrow which is used to kill a monster. It requires two hits to kill a monster. Once a monster is killed player can travel through that cave. Player can also pick shot arrows from their location with the treasure which is present at that location.
Player will win the game if player reaches the end point of the dungeon. An arrow has a capability to travel freely inside the tunnel whereas it travel in same direction for cave.
# List of Features

1.)Create a dungeon using the number of rows and column provided.
2.)Assign random treasure such as diamond, sapphires and rubies to the caves based on the percentage provided by the user.
3.)Allow player to set dungeon as wrapping or non-wrapping.
4.)Display player description and name.
5.)Randomly find a start point, end point and distance should be greater than 5.
6.)Create a dungeon with a minimum possible path after implementing Krushkal Algorithm.
7.)Show possible path from one start point to neighbour node and print options.
8.)Print Dungeon and all possible path from one node to each other.
9.)Collect treasure from each neighbour visited and store it user storage.
10.)Player can shot arrow in the dungeon by specifying the distance and direction.
11.)Player can pick up the arrow in the dungeon and can also reuse the shot arrow.
12.)Moster called Otyugh is placed randomly in the dungeon which can kill player once player reaches that location.
13.)Monster exhibits a pungent smell which can be detected from atmost distance two.
14.)Player can slay the monster by shooting them with the arrow. 
15.)It requires two hits to kill a monster.
16.)If monster shot just once and player travel through that cave that their is 50% probability that the player will be alive.
17.)Add a moving monster whose location keeps updating when ever player moves.
18.)Add a moving thief which will loot the treasure from the player when the player encounter the thief.


# How to run

The project comes with a jar which can be used to run the program.

Considering you are in the main project folder, if you need to run the the CLI follow these steps to run the jar:-

```bash
user@programmer~:$ java -jar res/Project5.jar 4 4 NO 2 40 3 
Here, the first argument represents the number of rows in the dungeon.
The second argument represents the number of columns in the dungeon.
The third argument represents the wrapping of the dungeon.
The fourth argument represents the number of connectvity in the dungeon.
The fifth argument represents the percentage of treasure in the dungeon.
The sixth argument represents the number of monster in the dungeon.
```
Else, if you want to play with the game in the GUI mode, follow these steps to run the jar:-
Download the entire zip file on your system and unzip it. After unzipping, you will find a jar file in the res folder.
user@programmer~:$ java -jar project5Test.jar 
It will ask you to enter the number of rows, columns, wrapping, number of connectivity, percentage of treasure and number of monster.
Just keep entering all the values and press OK. After all the inputs the UI will open and user is allowed to play the game.

1. Player can choose the button below to move, pick and shoot.
2. Player can also use mouse clicks to travel to next position.
3. Player can also use arrow keys to travel to next position.
4. Player can also use mouse clicks to shoot the monster.
5. Player can also use arrow keys to shoot the monster.
6. Player can also use mouse clicks to pick up the treasure.
7. Player can also use arrow keys to pick up the treasure.
8. Player can also use mouse clicks to pick up the arrow.
9. Player can also use arrow keys to pick up the arrow.


If you are feeling pretty confident then you can even compile the code yourself using IntelliJ (or any other IDE or even using the terminal). If you are using IntelliJ then just build the project and run the `Driver.java` file.

# Description of examples

All the features describes above internally and the user can just visualize it based on the outputs messages shown on the GUI. The user will be asked to give multiple input after which the dungeon will be created and the player can move move from the start point and travel in order to find the end point.

If you need help in the options then you can checkout the examples in `res/images`. These are simple image files, so you don't need any special software to checkout those examples. Enjoy!

# Design/Model changes

- There are three primary changes to the design after the design meeting:-

    1. A new class called MainView is added implemented from the DungeonInterface. It shows the GUI of the game.
    2. A new controller GameController is added implemented from the DungeonController. It is the controller of the game for the GUI code.
    3. In order to draw the dungeon, two view files added in view package named as NodeView and NodeViewCollection which takes care of drawing the dungeon.
    4. Few methods have been added in model monster and Dungeon to support moving theif and monster as well as a pit is added in the model.
- All these changes can be seen clearly in the design document (UML diagrams) - `CLASS DIAGRAM.pdf` (old design document) and `UPDATED_CLASS DIAGRAM.pdf` (new design document).

# Assumptions

The following assumptions were made during creation of this project:-

    1. The player can pick shot arrow again from the cave.

    2. Arrow can move freely and change direction inside the tunnel whereas it travel in same direction inside a cave.

    3. Once monster is killed its body is removed from the dungeon and smell is deleted from the dungeon.

    4. If the arrow is shot in darkness than the arrow will be removed from the dungeon since it is expected that it goes outside the dungeon.

    5. Tunnel is considered while calculating distance between Otyugh and the player position.

    6. Player can not be with the Otyugh in the same cave at the start cave.

    7. Otyugh will always be their in the end cave.

    8. Player can play the game unless the player is not killed by the monster, moving monster or by falling into the pit.


# Limitations

The following limitations were found while creating this project:-

    1. Player will pick what ever is present inside the location without specifying what is present in the cave.

    2. Player can not kill the monster when their is no arrows left with the player.

    3. Player can only detect the smell of the Otyugh at most distance of two.

    4. Game will exist as soon as the player reaches the end point.

    5.One player is killed by the moving monster the game will end.

    6.Player can kill the moving monster by pressing hit btn on the screen or pressing H on the keyboard.

    7.Once a player encounter the thief, the thief will loot the treasure from the player.

    


# CITATIONS

The following citations were used to build this project:-

    1.Edpresso Team. (2021, October 16). How to generate random numbers in Java. Educative: Interactive Courses for Software Developers. Retrieved October 16, 2021, from https://www.educative.io/edpresso/how-to-generate-random-numbers-in-java

    2.Java Program to Create random strings. (n.d.). Random String Generator. Retrieved October 16, 2021, from https://www.programiz.com/java-programming/examples/generate-random-string

    3.GeeksforGeeks. (2020, December 16). Shortest path in an unweighted graph. Retrieved November 1, 2021, from https://www.geeksforgeeks.org/shortest-path-unweighted-graph/

