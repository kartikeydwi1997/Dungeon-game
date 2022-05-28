package controllerwithargs;


import entry.DungeonInterface;
import entry.MonsterInterface;
import entry.Player;
import entry.TreasureInterface;
import entry.WeaponInterface;

import java.io.IOException;


/**
 * This is the interface of the controller class GameController which has on one method playGame
 * which allows player to play the game.
 */
public interface GameInterfaceWithArgs {

  /**
   * Execute a single game of Dungeon where player move in the dungeon and try to escape from the
   * monster. When the game is over, the playGame method ends.
   *
   * @param dungeon  the dungeon represents the entire implementation of dungeon and how the graph
   *                 is formed.
   * @param treasure the treasure represents the treasure that the player can get in the dungeon.
   * @param player   the player is the player who is playing the game.
   * @param monster  the monster is the monster that the player is trying to escape from.
   * @param weapon   the weapon is the weapon that the player is using to kill monster with.
   */
  void playGame(
          DungeonInterface dungeon,
          TreasureInterface treasure,
          MonsterInterface monster,
          Player player,
          WeaponInterface weapon)
          throws IOException;
}
