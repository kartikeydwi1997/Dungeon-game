package entry;

/**
 * Enum class to assigned the treasure to the dungeon position randomly.
 */
public enum TreasureType {
  DIAMONDS("DIAMOND"),
  RUBIES("RUBIES"),
  SAPPHIRES("SAPPHIRES");
  private String constantVal;

  /**
   * Constructor to take constantVal of each enum and assigned it to the local variable.
   *
   * @param constantVal Represents the constant value of particular enum.
   */
  TreasureType(String constantVal) {

    this.constantVal = constantVal;
  }

  /**
   * Returns the constant value as an integer.
   *
   * @return the integer.
   */
  public String getConstantVal() {
    return constantVal;
  }
}
