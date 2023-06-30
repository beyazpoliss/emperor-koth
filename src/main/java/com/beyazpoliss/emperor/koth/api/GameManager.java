package com.beyazpoliss.emperor.koth.api;

import com.beyazpoliss.emperor.koth.loader.ConfigOptions;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface GameManager {

  /**
   * Returns a collection of all registered games.
   *
   * @return a collection of all registered games
   */
  Collection<Game> games();

  /**
   * Loads game templates with the provided configuration options.
   *
   * @param options the ConfigOptions object containing the configuration options for loading templates
   */
  void loadTemplates(@NotNull final ConfigOptions options);

  /**
   * Creates a new game with the specified name and Game object.
   *
   * @param name the name of the new game
   * @param game the Game object representing the new game
   */
  void create(@NotNull final String name, @NotNull final Game game);

  /**
   * Removes the game with the specified name.
   *
   * @param name the name of the game to be removed
   */
  void remove(@NotNull final String name);

  void startGame(@NotNull final String name);

  Game game(@NotNull final String name);

  /**
   * Schedules the games based on the predefined scheduling rules.
   */
  void schedule();

}
