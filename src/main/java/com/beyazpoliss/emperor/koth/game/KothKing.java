package com.beyazpoliss.emperor.koth.game;

import com.beyazpoliss.emperor.koth.api.King;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class KothKing implements King {

  @Nullable
  private Player player;

  @Override
  public Player player() {
    return player;
  }

  @Override
  public void setPlayer(@NotNull Player player) {
    this.player = player;
  }
}
