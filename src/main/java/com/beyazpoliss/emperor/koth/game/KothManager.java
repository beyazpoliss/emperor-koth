package com.beyazpoliss.emperor.koth.game;

import com.beyazpoliss.emperor.koth.Provider;
import com.beyazpoliss.emperor.koth.api.Game;
import com.beyazpoliss.emperor.koth.api.GameManager;
import com.beyazpoliss.emperor.koth.loader.ConfigOptions;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class KothManager implements GameManager {

  private final ConcurrentHashMap<String, Game> games;

  public KothManager(){
    this.games = new ConcurrentHashMap<>();
  }

  @Override
  public Collection<Game> games() {
    return games.values();
  }

  @Override
  public void loadTemplates(@NotNull final ConfigOptions options) {
    options.loadGames().forEach(game -> games.put(game.name(),game));
  }

  @Override
  public void create(@NotNull final String name, @NotNull final Game game) {
    games.put(name,game);
  }

  @Override
  public void remove(@NotNull final String name) {
    this.games.remove(name);
  }

  @Override
  public void startGame(@NotNull String name) {
    games.get(name).startGame();
  }

  @Override
  public Game game(@NotNull String name) {
    return games.get(name);
  }

  @Override
  public void schedule() {
    Bukkit.getScheduler().runTaskTimerAsynchronously(Provider.instance(), () -> games.forEach((s, game) -> {
      if (game.scheduler().scheduleGame()) {
        games.remove(s);
      }
    }),20,20);
  }
}
