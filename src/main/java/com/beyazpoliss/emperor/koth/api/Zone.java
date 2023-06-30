package com.beyazpoliss.emperor.koth.api;

import com.beyazpoliss.emperor.koth.game.KothZone;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface Zone {

  @NotNull
  String world();

  @NotNull
  Corner cornerOne();

  void setCornerOne(@NotNull final Corner cornerOne);

  void setCornerTwo(@NotNull final Corner cornerTwo);

  @NotNull
  Corner cornerTwo();

  CompletableFuture<Boolean> isPlayerInsideZoneAsync(@NotNull final Player player);

  CompletableFuture<Optional<Player>> getRandomPlayerInTheZone();

  boolean isPlayerInZone(@NotNull final Player player);

  static Zone createZone(@NotNull final String worldName,
                         @NotNull final Corner cornerOne,
                         @NotNull final Corner cornerTwo){
    return new KothZone(worldName,cornerOne,cornerTwo);
  }

}
