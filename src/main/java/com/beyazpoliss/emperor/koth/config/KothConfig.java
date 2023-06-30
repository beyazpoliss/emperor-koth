package com.beyazpoliss.emperor.koth.config;

import com.beyazpoliss.emperor.koth.Provider;
import lombok.var;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class KothConfig {

  private final Plugin plugin;

  private final File pluginFolder;
  private final File ymlFile;

  private FileConfiguration configuration;

  public KothConfig(final Plugin plugin, final String ymlName) {
    this.plugin = plugin;
    this.pluginFolder = new File(Provider.instance().getDataFolder() + File.separator);
    this.ymlFile = new File(this.plugin.getDataFolder().getPath(), ymlName);
  }

  public void createFile(){
    if (!pluginFolder.exists()){
      try {
        pluginFolder.mkdirs();
        pluginFolder.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void loadYML() {
    createFile();
    if (!ymlFile.exists()){
      try {
        ymlFile.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    this.configuration = YamlConfiguration.loadConfiguration(ymlFile);
  }

  public List<String> getKeys(final String path, final boolean deep){
    if (!isSet(path)) return new ArrayList<>();
    return new ArrayList<>(configuration.getConfigurationSection(path).getKeys(deep));
  }

  public CompletableFuture<Void> loadAsync() {
    return CompletableFuture.supplyAsync(() -> {
      this.createFile();
      if (!pluginFolder.exists()){
        try {
          pluginFolder.createNewFile();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      this.configuration = YamlConfiguration.loadConfiguration(pluginFolder);
      return null;
    });
  }

  public CompletableFuture<Void> saveAsync() {
    return CompletableFuture.supplyAsync(() -> {
      try {
        this.configuration.save(pluginFolder);
      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;
    });
  }

  public void saveYML(){
    try {
      this.configuration.save(ymlFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean isSet(final String path){
    return configuration.isSet(path);
  }

  public void set(final String path,final Object o){
    configuration.set(path,o);
  }

  public void setIfNotExists(final String path,final Object o){
    if (!isSet(path)){
      configuration.set(path, o);
    }
  }

  public FileConfiguration getConfiguration() {
    return configuration;
  }

  public Plugin getPlugin() {
    return plugin;
  }


  public Location getLocation(final @NotNull String path){
    return (Location) this.configuration.get(path);
  }

  public void setStringIfNotExists(final @NotNull String path, final String message) {
    if (isNotSet(path)){
      this.configuration.set(path, message);
    }
  }

  public boolean isNotSet(final @NotNull String path){
    return !this.configuration.isSet(path);
  }

  @NotNull
  public Object get(final @NotNull String path) {
    return Objects.requireNonNull(configuration.get(path));
  }

  public String getStringColored(@Nullable final String path) {
    assert path != null;
    final var text = configuration.getString(path);
    assert text != null;
    return ChatColor.translateAlternateColorCodes('&', text);
  }

  public List<String> getStringColoredList(@Nullable final String path){
    assert path != null;
    final var text = configuration.getStringList(path);
    final List<String> newList = new ArrayList<>();
    text.forEach(line -> newList.add(ChatColor.translateAlternateColorCodes('&',line)));
    return newList;
  }

  public int getInt(String path){
    return configuration.getInt(path);
  }
}
