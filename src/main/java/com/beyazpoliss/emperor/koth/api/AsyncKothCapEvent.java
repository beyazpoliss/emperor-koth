package com.beyazpoliss.emperor.koth.api;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class AsyncKothCapEvent extends KothEvent{

  @Nullable
  @Getter
  private final Player player;

  public AsyncKothCapEvent(Game game, @Nullable Player player) {
    super(game);
    this.player = player;
  }

  @Override
  public String getEventName() {
    return "AsyncKothCapEvent";
  }
}
