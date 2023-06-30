package com.beyazpoliss.emperor.koth.api;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//This class has stopped working, please use the async one!
@Deprecated
public class KothCapEvent extends KothEvent{

  @Nullable
  @Getter
  private final Player player;

  public KothCapEvent(@NotNull final Game game,@Nullable final Player player) {
    super(game);
    this.player = player;
  }
}
