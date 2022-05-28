package entry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * This class represents a weapon in the game and contains all the information about it. It is used
 * to create a weapon and to get information about it. It also updates the weapon's stats.
 */
public class Weapon implements WeaponInterface {
  private final double treasurePerct;
  public List<List<String>> weaponList;
  private final int maxLocations;
  private final Random random;

  /**
   * Constructor for the Weapon class. It assigns the values passed to the variables.
   *
   * @param maxLocations  The maximum number of locations in the game.
   * @param treasurePerct The percentage of treasure in the game.
   * @param random        The random object used to generate random numbers.
   */
  public Weapon(int maxLocations, double treasurePerct, Random random) {
    performCheck(maxLocations, treasurePerct, random);
    this.treasurePerct = treasurePerct;
    this.maxLocations = maxLocations;
    this.weaponList = new ArrayList<>();
    this.random = random;
  }

  private void performCheck(int maxLocations, double treasurePerct, Random random) {
    if (maxLocations < 0 || maxLocations <= 9) {
      throw new IllegalArgumentException("Illegal value passed for maxLocations");
    }
    if (treasurePerct < 0 || treasurePerct > 100) {
      throw new IllegalArgumentException("Illegal value passed for treasure percentage");
    }
    if (random == null) {
      throw new IllegalArgumentException("Random cannot be null!!");
    }
  }

  /**
   * This method is used to assign the weapon to the dungeon based on the random value in both caves
   * and tunnel.
   *
   * @return the weapon list of the dungeon.
   */
  @Override
  public List<List<String>> assignWeapon() {
    weaponList.clear();
    double finalVal = (float) treasurePerct * maxLocations;
    finalVal = finalVal / 100;
    finalVal = Math.floor(finalVal);
    for (int i = 0; i < maxLocations; i++) {
      weaponList.add(Collections.singletonList("NULL"));
    }
    while (finalVal != 0) {

      int num = RandomNumberGenerator.getRandomNumber(random);

      int i = 0;
      if (weaponList.get(num - 1).get(0).equals("NULL")) {
        i = 1;
      } else {
        i = weaponList.get(num - 1).size();
        i += 1;
      }
      weaponList.set(num - 1, Collections.nCopies(i, "Arrow"));
      finalVal -= 1;

    }
    List<List<String>> copy = weaponList.stream().collect(Collectors.toList());
    return copy;
  }

  /**
   * This method is used to update the weapon's stats to null.
   *
   * @param currentLocation the current location of the player.
   */
  @Override
  public void updateWeaponList(int currentLocation) {
    if (currentLocation < 0) {
      throw new IllegalArgumentException("Illegal value passed for currentLocation");
    }
    weaponList.set(currentLocation - 1, Collections.singletonList("NULL"));
  }

  /**
   * This method add the weapon to the weapon list using the current location of the arrow.
   *
   * @param currentLocation the current location of the player.
   */
  @Override
  public void addWeaponToList(int currentLocation) {
    if (currentLocation > 0 && currentLocation <= maxLocations) {
      if (weaponList.get(currentLocation - 1).get(0).equals("NULL")) {
        weaponList.set(currentLocation - 1, Collections.singletonList("Arrow"));
      } else {
        int count = weaponList.get(currentLocation - 1).size();
        String[] arr = new String[count + 1];
        for (int i = 0; i < count + 1; i++) {
          arr[i] = "Arrow";
        }
        weaponList.set(currentLocation - 1, Arrays.asList(arr));
      }
    } else {
      throw new IllegalArgumentException("Illegal value passed for currentLocation");
    }
  }

  /**
   * This method is used to get the weapon list of the dungeon.
   *
   * @return the weapon list of the dungeon.
   */
  @Override
  public List<List<String>> getWeaponList() {

    return new ArrayList<>(weaponList);
  }

  /**
   * This method check if the weapon is present in the current location.
   *
   * @param currentLocation the current location of the player.
   * @return true if the weapon is present in the current location.
   */
  @Override
  public boolean checkWeapon(int currentLocation) {
    if (currentLocation > 0 && currentLocation <= maxLocations) {
      return !weaponList.get(currentLocation - 1).get(0).equals("NULL");
    }
    return false;
  }
}
