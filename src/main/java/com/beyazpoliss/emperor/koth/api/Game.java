package com.beyazpoliss.emperor.koth.api;

import com.beyazpoliss.emperor.koth.game.KothGame;
import com.beyazpoliss.emperor.koth.game.KothKing;
import com.beyazpoliss.emperor.koth.game.KothRewards;
import com.beyazpoliss.emperor.koth.game.KothScheduler;
import lombok.var;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * The Game interface represents a game instance.
 * It provides methods to retrieve and manipulate game-related information and actions.
 */
public interface Game {

  /**
   * Returns the name of the game.
   *
   * @return the name of the game
   */
  @NotNull
  String name();

  /**
   * Returns the Zone object associated with the game.
   *
   * @return the Zone object associated with the game
   */
  @NotNull
  Zone zone();

  /**
   * Returns the GameScheduler object associated with the game.
   *
   * @return the GameScheduler object associated with the game
   */
  @NotNull
  GameScheduler scheduler();

  /**
   * Sets the GameScheduler object for the game.
   *
   * @param scheduler the GameScheduler object to be set for the game
   */
  void setScheduler(@NotNull final GameScheduler scheduler);

  /**
   * Returns the King object representing the current king of the game.
   *
   * @return the King object representing the current king of the game
   */
  @NotNull
  King king();

  /**
   * Sets the next king for the game.
   *
   * @return a CompletableFuture representing the asynchronous result of setting the next king,
   * where the Boolean value indicates whether the operation was successful or not
   */
  CompletableFuture<Boolean> setNextKing();

  void rewards(@NotNull final Rewards rewards);

  Rewards rewards();

  /**
   * Returns the status of the game, obtained from the associated GameScheduler.
   *
   * @return the status of the game
   */
  default Status status(){
    return scheduler().status();
  }

  default void startGame(){
    this.status(Status.WAITING);
  }

  /**
   * Sets the status of the game using the associated GameScheduler.
   *
   * @param status the status to be set for the game
   */
  default void status(@NotNull final Status status){
    scheduler().status(status);
  }

  static Game createGame(@NotNull final String name,@NotNull final Zone zone){
    final var game = new KothGame(zone,name,new KothKing(),new KothRewards());
    final var scheduler = new KothScheduler(game,60,60);
    game.setScheduler(scheduler);
    return game;
  }
}
