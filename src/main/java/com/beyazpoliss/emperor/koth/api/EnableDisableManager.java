package com.beyazpoliss.emperor.koth.api;

import com.beyazpoliss.emperor.koth.config.KothConfig;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
/**
 * The EnableDisableManager interface provides methods to enable or disable plugins.
 */
public interface EnableDisableManager {
  /**
   * Tries to start the plugin when called.
   *
   * @param plugin The plugin to enable. Must not be null.
   */
  void enable(@NotNull final Plugin plugin);
  /**
   * Tries to disable the plugin when called.
   *
   * @param plugin The plugin to disable. Must not be null.
   */
  void disable(@NotNull final Plugin plugin);
  /**
   * @return the KothConfig object associated with this instance
   */
  KothConfig config();
  /**
   * @return the GameManager object associated with this instance
   */
  GameManager gameManager();
}
