package com.beyazpoliss.emperor.koth.api;

import org.jetbrains.annotations.NotNull;

public interface GameScheduler {

  @NotNull
  Status status();

  void status(@NotNull final Status status);

  void setCaptureTime(final int time);

  boolean scheduleGame();

  void scheduleWaiting();

  void scheduleFighting();

  void scheduleEnding();

}
