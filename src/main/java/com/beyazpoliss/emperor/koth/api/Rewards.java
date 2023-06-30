package com.beyazpoliss.emperor.koth.api;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Set;

public interface Rewards {

  Collection<String> rewardCommands();

  void setCommands(@NotNull final Set<String> collection);

}
