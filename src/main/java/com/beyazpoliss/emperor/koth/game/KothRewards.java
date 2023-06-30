package com.beyazpoliss.emperor.koth.game;

import com.beyazpoliss.emperor.koth.api.Rewards;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class KothRewards implements Rewards {

  private Set<String> rewards;

  public KothRewards(){
    this.rewards = new HashSet<>();
  }

  @Override
  public Collection<String> rewardCommands() {
    return rewards;
  }

  @Override
  public void setCommands(@NotNull final Set<String> collection) {
    this.rewards = collection;
  }
}
