package com.beyazpoliss.emperor.koth.api;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public interface King {

  @Nullable
  Player player();

  void setPlayer(@Nullable final Player player);

}
