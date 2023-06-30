package com.beyazpoliss.emperor.koth.api;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public class ChangeKothStatusEvent extends KothEvent{

  @Getter
  private final Status oldStatus;

  @Getter
  private final Status newStatus;

  public ChangeKothStatusEvent(@NotNull final Game game,@NotNull final Status oldStatus, @NotNull final Status newStatus) {
    super(game);
    this.oldStatus = oldStatus;
    this.newStatus = newStatus;
  }
}
