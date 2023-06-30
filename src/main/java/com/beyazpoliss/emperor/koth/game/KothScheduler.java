package com.beyazpoliss.emperor.koth.game;

import com.beyazpoliss.emperor.koth.Provider;
import com.beyazpoliss.emperor.koth.api.*;
import lombok.var;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class KothScheduler implements GameScheduler {

  @NotNull
  private Status status;

  @NotNull
  private final Game game;

  private int waitingTime;
  private int fightingTime;
  private int defaultCaptureTime;

  public KothScheduler(@NotNull final Game game,
                       int waitingTime,
                       int fightingTime){
    this.game = game;
    this.status = Status.NOT_STARTED;
    this.waitingTime = waitingTime;
    this.fightingTime = fightingTime;
    this.defaultCaptureTime = fightingTime;
  }

  @Override
  public @NotNull Status status() {
    return status;
  }

  @Override
  public void status(@NotNull Status status) {
    final var changeKothStatusEvent = new ChangeKothStatusEvent(game,this.status,status);
    this.status = status;
    Bukkit.getPluginManager().callEvent(changeKothStatusEvent);
  }

  @Override
  public void setCaptureTime(final int time) {
    this.defaultCaptureTime = time;
  }

  @Override
  public boolean scheduleGame() {
    switch (status){
      case NOT_STARTED:
        return false;
      case WAITING:
        this.scheduleWaiting();
        return false;
      case FIGHTING:
        this.scheduleFighting();
        return false;
      case ENDING:
        this.scheduleEnding();
        return true;
    }

    return false;
  }

  @Override
  public void scheduleWaiting() {
    final var config = Provider.instance().getManager().config();
    waitingTime--;
    if (waitingTime % 5 == 0 && waitingTime != 0){
      Bukkit.broadcastMessage(config.getStringColored("messages.koth-waiting")
        .replace("%koth_name%",game.name())
        .replace("%time%",waitingTime + ""));
    }
    if (waitingTime == 0){
      Bukkit.broadcastMessage(config.getStringColored("messages.koth-start")
        .replace("%koth_name%",game.name()));
      this.status(Status.FIGHTING);
    }
  }

  @Override
  public void scheduleFighting() {
    final var config = Provider.instance().getManager().config();

    fightingTime--;

    final var king = game.king().player();
    if (king == null){
      game.setNextKing().thenAccept(isKing -> {
        if (isKing){
          Bukkit.broadcastMessage(config.getStringColored("messages.koth-fighting")
            .replace("%koth_name%",game.name())
            .replace("%time%",fightingTime + "")
            .replace("%player%", Objects.requireNonNull(game.king().player()).getName())
          );
          this.fightingTime = defaultCaptureTime;
          final var asyncKothCapEvent = new AsyncKothCapEvent(game, game.king().player());
          Bukkit.getPluginManager().callEvent(asyncKothCapEvent);
        } else {
          if (fightingTime % 5 == 0 && fightingTime != 0){
            Bukkit.broadcastMessage(config.getStringColored("messages.koth-empty").replace("%koth_name%",game.name()));
          }
        }
      });
    } else {
      game.zone().isPlayerInsideZoneAsync(king).thenAccept(isInside -> {
        if (isInside){
          Bukkit.broadcastMessage(config.getStringColored("messages.koth-fighting")
            .replace("%koth_name%",game.name())
            .replace("%time%",fightingTime + "")
            .replace("%player%",king.getName())
          );
        } else {
          game.king().setPlayer(null);
          this.fightingTime = defaultCaptureTime;
          final var asyncKothCapEvent = new AsyncKothCapEvent(game, game.king().player());
          Bukkit.getPluginManager().callEvent(asyncKothCapEvent);
        }
      });
    }

    if (fightingTime == 0){
      this.status(Status.ENDING);
    }
  }

  @Override
  public void scheduleEnding() {
    final var config = Provider.instance().getManager().config();
    final var lastKing = game.king().player();
    if (lastKing == null) {
      Bukkit.broadcastMessage(config.getStringColored("messages.koth-no-winner")
        .replace("%koth_name%",game.name()));
      return;
    }
    if (!lastKing.isOnline()){
      Bukkit.broadcastMessage(config.getStringColored("messages.koth-winner-not-online")
        .replace("%koth_name%",game.name()));
    }

    Bukkit.broadcastMessage(config.getStringColored("messages.koth-ending")
      .replace("%koth_name%",game.name())
      .replace("%player%", lastKing.getName()));

    game.rewards().rewardCommands().forEach(command -> {
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(),command.replace("%player%", lastKing.getName()));
    });
  }
}
