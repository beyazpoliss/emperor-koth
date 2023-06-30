package com.beyazpoliss.emperor.koth.loader;

import com.beyazpoliss.emperor.koth.Provider;
import com.beyazpoliss.emperor.koth.api.EnableDisableManager;
import com.beyazpoliss.emperor.koth.api.GameManager;
import com.beyazpoliss.emperor.koth.config.KothConfig;
import com.beyazpoliss.emperor.koth.game.EmperorKothCommand;
import com.beyazpoliss.emperor.koth.game.KothManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class EnableDisableManagerImpl implements EnableDisableManager {

  private KothConfig config;

  private GameManager kothManager;

  private ConfigOptions configOptions;

  @Override
  public void enable(@NotNull final Plugin plugin) {
    this.config = new KothConfig(plugin,"config.yml");
    this.kothManager = new KothManager();
    CompletableFuture.runAsync(() -> {
      config.loadYML();
      this.configOptions = new ConfigOptions(config);
      this.configOptions.setDefaultOptions();
      config.saveYML();
    }).thenRun(() -> {
      this.kothManager.loadTemplates(configOptions);
    });
    this.kothManager.schedule();
    Provider.instance().getCommand("emperor-koth").setExecutor(new EmperorKothCommand());
  }

  @Override
  public void disable(@NotNull final Plugin plugin) {

  }

  @Override
  public KothConfig config() {
    return config;
  }

  @Override
  public GameManager gameManager() {
    return kothManager;
  }
}
