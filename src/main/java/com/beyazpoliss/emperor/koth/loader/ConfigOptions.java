package com.beyazpoliss.emperor.koth.loader;

import com.beyazpoliss.emperor.koth.api.Game;
import com.beyazpoliss.emperor.koth.config.KothConfig;
import com.beyazpoliss.emperor.koth.game.*;
import lombok.AllArgsConstructor;
import lombok.var;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@AllArgsConstructor
public class ConfigOptions {

  private final KothConfig config;

  public void setDefaultOptions(){
    this.config.setIfNotExists("messages.koth-help-command", Arrays.asList(
      "&aEmperor Koth -> This command was not found!",
      "",
      "&7To create a game:",
      "&e/emperor-koth create gameName.",
      "",
      "&7Here are the available sub-commands for Emperor Koth:",
      "",
      "&e/emperor-koth gameName setCornerOne.",
      "&e/emperor-koth gameName setCornerTwo.",
      "&e/emperor-koth gameName setCaptureTime <captureTime>.",
      "&e/emperor-koth gameName setCaptureReward <captureReward>.",
      "&e/emperor-koth start gameName.",
      "",
      "&e/emperor-koth reload.",
      "",
      "&7Please make and refresh all settings before creating a game."
    ));
    this.config.setStringIfNotExists("messages.koth-waiting","&aEmperor Koth -> Fighting in Zone %koth_name% will start in %time% seconds.");
    this.config.setStringIfNotExists("messages.koth-no-winner","&aEmperor Koth -> The %koth_name% zone is over but there was no winner!");
    this.config.setStringIfNotExists("messages.koth-winner-not-online","&aEmperor Koth -> The %koth_name% zone is over but there was no winner! %player% not online!");
    this.config.setStringIfNotExists("messages.koth-empty","&aEmperor Koth -> The hill in %koth_name% is empty! The event ends in %time% seconds!");
    this.config.setStringIfNotExists("messages.koth-fighting","&aEmperor Koth -> The fight is still going on in %koth_name%! %player% is now king of the hill and the game will end in %time% seconds!");
    this.config.setStringIfNotExists("messages.koth-start","&aEmperor Koth -> All warriors fight at %koth_name%, King of the hill event started!");
    this.config.setStringIfNotExists("messages.koth-ending","&cEmperor Koth -> The battle on %koth_name% is over, %player% is king of the hill!");
    this.config.setStringIfNotExists("messages.reload-message","&aEmperor Koth -> Plugin successfully reloaded!");
    this.config.setStringIfNotExists("messages.not-permission","&cEmperor Koth -> You Can't have a permission!");
    this.config.setStringIfNotExists("messages.game-not-found","&cEmperor Koth -> This game is not found!");
    this.config.setStringIfNotExists("messages.setting-changed","&cEmperor Koth -> Setting Changed!");
    this.config.setStringIfNotExists("messages.game-created","&cEmperor Koth -> Game created!");
    this.config.setStringIfNotExists("koth-regions.koth-one.world-name","koth");
    this.config.setStringIfNotExists("koth-regions.koth-one.capture-name","koth1");
    this.config.setIfNotExists("koth-regions.koth-one.capture-fighting-time",60);
    this.config.setIfNotExists("koth-regions.koth-one.capture-waiting-time",60);
    this.config.setIfNotExists("koth-regions.koth-one.capture-rewards", Arrays
      .asList("/give %player% diamond_sword","/give %player% coal_block","/give %player% iron_block"));

    this.config.setIfNotExists("koth-regions.koth-one.capture-region.corner-one.x",1);
    this.config.setIfNotExists("koth-regions.koth-one.capture-region.corner-one.y",1);
    this.config.setIfNotExists("koth-regions.koth-one.capture-region.corner-one.z",1);
    this.config.setIfNotExists("koth-regions.koth-one.capture-region.corner-two.x",1);
    this.config.setIfNotExists("koth-regions.koth-one.capture-region.corner-two.y",1);
    this.config.setIfNotExists("koth-regions.koth-one.capture-region.corner-two.z",1);
  }

  public Collection<Game> loadGames(){
    final var games = new ArrayList<Game>();

    this.config.getKeys("koth-regions",false).forEach(arena ->{
      final String worldName = (String) this.config.get("koth-regions." + arena + ".world-name");
      final var world = Bukkit.getWorld(worldName);
      if (world == null) throw new RuntimeException("This world is null! " + arena + " not loaded!");
      final var captureName = (String) this.config.get("koth-regions." + arena + ".capture-name");

      final var cornerOneX = this.config.getInt("koth-regions." + arena + ".capture-region.corner-one.x");
      final var cornerOneY = this.config.getInt("koth-regions." + arena + ".capture-region.corner-one.y");
      final var cornerOneZ = this.config.getInt("koth-regions." + arena + ".capture-region.corner-one.z");

      final var cornerTwoX = this.config.getInt("koth-regions." + arena + ".capture-region.corner-one.x");
      final var cornerTwoY = this.config.getInt("koth-regions." + arena + ".capture-region.corner-one.y");
      final var cornerTwoZ = this.config.getInt("koth-regions." + arena + ".capture-region.corner-one.z");

      final var cornerOne = new ZoneCorner(cornerOneX,cornerOneY,cornerOneZ);
      final var cornerTwo = new ZoneCorner(cornerTwoX,cornerTwoY,cornerTwoZ);

      final var zone = new KothZone(worldName,cornerOne,cornerTwo);
      final var kothGame = new KothGame(zone,captureName,new KothKing(),new KothRewards());

      final var waitingTime = this.config.getInt("koth-regions." + arena + ".capture-waiting-time");
      final var fightingTime = this.config.getInt("koth-regions." + arena + ".capture-fighting-time");

      kothGame.setScheduler(new KothScheduler(kothGame,waitingTime,fightingTime));

      games.add(kothGame);
    });

    return games;
  }

}
