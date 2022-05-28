package entry;

import java.util.List;

/**
 * WeaponInterface to assign weapons in the dungeon. It also contains other operations
 * such as update weapon list, check if the weapon is in the list, and get the weapon
 * from the list.
 */
public interface WeaponInterface {
  /**
   * This method is used to assign the weapon to the dungeon based on the random value
   * in both caves and tunnel.
   *
   * @return the weapon list of the dungeon.
   */
  List<List<String>> assignWeapon();

  /**
   * This method is used to update the weapon's stats to null.
   *
   * @param currentLocation the current location of the player.
   */
  void updateWeaponList(int currentLocation);

  /**
   * This method add the weapon to the weapon list using the current location of the arrow.
   *
   * @param currentLocation the current location of the player.
   */
  void addWeaponToList(int currentLocation);

  /**
   * This method is used to get the weapon list of the dungeon.
   *
   * @return the weapon list of the dungeon.
   */
  List<List<String>> getWeaponList();

  /**
   * This method check if the weapon is present in the current location.
   *
   * @param currentLocation the current location of the player.
   * @return true if the weapon is present in the current location.
   */
  boolean checkWeapon(int currentLocation);
}
