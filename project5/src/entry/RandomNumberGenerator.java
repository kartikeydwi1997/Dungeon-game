package entry;

import java.util.Random;


/**
 * This class creates random name, random desc and random treasure for a player. It also finds
 * the probability of the player will be alive or dead in a cave with a slayed monster.
 */
public class RandomNumberGenerator {

  /**
   * Calculate the player name.
   *
   * @param random Represents a random object.
   * @return a string representing player name.
   */
  public static String getRandomName(Random random) {
    if (random == null) {
      throw new IllegalArgumentException("Random object cannot be null!");
    }
    // create random string builder
    String validCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    StringBuilder sb = new StringBuilder();
    int length = 6;
    for (int i = 1; i < length; i++) {
      int index = random.nextInt(validCharacters.length());
      char randomChar = validCharacters.charAt(index);
      sb.append(randomChar);
    }
    return sb.toString();

  }

  /**
   * Find the player desc from the string list.
   *
   * @param random Represents the random object.
   * @return the string as player description.
   */
  public static String getRandomCharacter(Random random) {
    if (random == null) {
      throw new IllegalArgumentException("Random object cannot be null!");
    }
    String[] characters = {"Be aware of him! he can kill in a flash!!", "The mighty king born "
            + " to be a leader", "Don't go on her face, she can kill you with her smile"};

    int index;
    index = random.nextInt(characters.length);
    return characters[index];

  }

  /**
   * Assign the random treasure to each player.
   *
   * @param random Represents a random object.
   * @return integer value that decide which treasure will be assigned to the player.
   */
  public static int getRandomTreasure(Random random) {
    if (random == null) {
      throw new IllegalArgumentException("Random object cannot be null!");
    }
    int min = 1;
    int max = 3;
    if (random == null) {
      throw new IllegalArgumentException("Random object cannot be null!");
    }
    return random.nextInt((max - min) + 1) + min;

  }


  /**
   * Assign the random treasure to each player.
   *
   * @param random Represents a random object.
   * @return integer value that decide which treasure will be assigned to the player.
   */
  public static int getRandomNumber(Random random) {
    if (random == null) {
      throw new IllegalArgumentException("Random object cannot be null!");
    }
    int min = 1;
    int max = 16;
    if (random == null) {
      throw new IllegalArgumentException("Random object cannot be null!");
    }
    return random.nextInt((max - min) + 1) + min;

  }

  /**
   * This method finds a random number between 1 and 2 which represents if the player is alive or
   * dead.
   *
   * @param random Represents a random object.
   * @return integer value that decide if the player is alive or dead.
   */
  public static int getPlayerProbability(Random random) {
    if (random == null) {
      throw new IllegalArgumentException("Random object cannot be null!");
    }
    int min = 0;
    int max = 1;
    return random.nextInt((max - min) + 1) + min;

  }

  /**
   * This method finds a random number between 1 and max location of the dungeon which
   * determine the thief next position.
   * @param maxLocation Represents the max location of the dungeon.
   * @param random Represents a random object.
   * @return integer value that decide the thief next position.
   */
  public static int getRandomThiefPosition(int maxLocation, Random random) {
    if (random == null) {
      throw new IllegalArgumentException("Random object cannot be null!");
    }
    int min = 1;
    int max = maxLocation;
    if (random == null) {
      throw new IllegalArgumentException("Random object cannot be null!");
    }
    return random.nextInt((max - min) + 1) + min;

  }
}

