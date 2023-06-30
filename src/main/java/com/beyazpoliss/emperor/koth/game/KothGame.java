package com.beyazpoliss.emperor.koth.game;

import com.beyazpoliss.emperor.koth.api.*;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class KothGame implements Game {

  @NotNull
  private final Zone zone;

  @NotNull
  private final String name;

  @NotNull
  private final King king;

  @Setter
  private GameScheduler scheduler;

  @NotNull
  private Rewards rewards;

  @Override
  @NotNull
  public String name() {
    return name;
  }

  @Override
  public @NotNull Zone zone() {
    return zone;
  }

  @Override
  public @NotNull GameScheduler scheduler() {
    return scheduler;
  }

  @Override
  public @NotNull King king() {
    return king;
  }

  @Override
  public CompletableFuture<Boolean> setNextKing() {
    final CompletableFuture<Boolean> returnState = new CompletableFuture<>();
    zone.getRandomPlayerInTheZone().thenAccept(optionalPlayer -> {
      if (optionalPlayer.isPresent()){
        king.setPlayer(optionalPlayer.get());
        returnState.complete(true);
      } else {
        returnState.complete(false);
      }
    });
    return returnState;
  }

  @Override
  public void rewards(@NotNull Rewards rewards) {
    this.rewards = rewards;
  }

  @Override
  public Rewards rewards() {
    return rewards;
  }
}
