package com.beyazpoliss.emperor.koth.game;

import com.beyazpoliss.emperor.koth.Provider;
import com.beyazpoliss.emperor.koth.api.Corner;
import com.beyazpoliss.emperor.koth.api.Zone;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class KothZone implements Zone {

  @NotNull
  private final String world;

  @NotNull
  private Corner cornerOne;

  @NotNull
  private Corner cornerTwo;

  @Override
  @NotNull
  public String world() {
    return world;
  }

  @Override
  @NotNull
  public Corner cornerOne() {
    return cornerOne;
  }

  @Override
  public void setCornerOne(@NotNull final Corner cornerOne) {
    this.cornerOne = cornerOne;
  }

  @Override
  public void setCornerTwo(@NotNull final Corner cornerTwo) {
    this.cornerTwo = cornerTwo;
  }

  @Override
  @NotNull
  public Corner cornerTwo() {
    return cornerTwo;
  }

  @Override
  public CompletableFuture<Boolean> isPlayerInsideZoneAsync(@NotNull final Player player) {
    CompletableFuture<Boolean> future = new CompletableFuture<>();
    Bukkit.getScheduler().runTaskAsynchronously(Provider.instance(), () -> {
      if (isPlayerInZone(player)){
        future.complete(true);
        return;
      }
      future.complete(false);
    });
    return future;
  }

  @Override
  public CompletableFuture<Optional<Player>> getRandomPlayerInTheZone() {
    CompletableFuture<Optional<Player>> future = new CompletableFuture<>();
    Bukkit.getScheduler().runTaskAsynchronously(Provider.instance(), () -> {
      for (Player player : Bukkit.getOnlinePlayers()) {
        if (isPlayerInZone(player)){
          future.complete(Optional.of(player));
          return;
        }
      }
      future.complete(Optional.empty());
    });
    return future;
  }

  @Override
  public boolean isPlayerInZone(@NotNull final Player player) {
    int minX = (int) Math.min(cornerOne.x(), cornerTwo.x());
    int minY = (int) Math.min(cornerOne.y(), cornerTwo.y());
    int minZ = (int) Math.min(cornerOne.z(), cornerTwo.z());
    int maxX = (int) Math.max(cornerOne.x(), cornerTwo.x());
    int maxY = (int) Math.max(cornerOne.y(), cornerTwo.y());
    int maxZ = (int) Math.max(cornerOne.z(), cornerTwo.z());

    int playerX = player.getLocation().getBlockX();
    int playerY = player.getLocation().getBlockY();
    int playerZ = player.getLocation().getBlockZ();

    if (!player.getWorld().getName().equalsIgnoreCase(world)) return false;
    return playerX >= minX && playerX <= maxX
      && playerY >= minY && playerY <= maxY
      && playerZ >= minZ && playerZ <= maxZ;
  }
}
