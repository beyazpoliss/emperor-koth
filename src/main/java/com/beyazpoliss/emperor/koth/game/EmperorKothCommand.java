package com.beyazpoliss.emperor.koth.game;

import com.beyazpoliss.emperor.koth.Provider;
import com.beyazpoliss.emperor.koth.api.Corner;
import com.beyazpoliss.emperor.koth.api.Game;
import com.beyazpoliss.emperor.koth.api.Zone;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.var;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class EmperorKothCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) return true;

    final var player = (Player) sender;

    final var config = Provider.instance().getManager().config();

    if (!player.isOp()) {
      player.sendMessage(config.getStringColored("messages.not-permission"));
      return true;
    }

    final var arg0 = args[0];

    if (args.length < 2) {
      if (arg0.equalsIgnoreCase("reload")){
        Bukkit.getScheduler().runTaskAsynchronously(Provider.instance(), () -> {
          Provider.instance().getManager().config().saveYML();
          Provider.instance().getManager().config().loadYML();
          player.sendMessage(config.getStringColored("messages.reload-message"));
        });
        return true;
      }
      this.sendHelpMessage(player);
      return true;
    }

    final var subCommand = args[1];

    switch (arg0){
      case "create":
        if (args.length < 3) {
          this.sendHelpMessage(player);
          return true;
        }
        final var gameName = args[1];
        final var worldName = args[2];

        final var corner = Corner.of(player.getLocation());
        Provider.instance().getManager().gameManager().create(gameName, Game.createGame(gameName, Zone.createZone(worldName,corner,corner)));
        player.sendMessage(config.getStringColored("messages.game-created"));
        return true;
      case "start":
        final var game = Provider.instance().getManager().gameManager().game(args[1]);
        if (game == null) {
          player.sendMessage(config.getStringColored("messages.game-not-found"));
          return true;
        }
        Provider.instance().getManager().gameManager().startGame(args[1]);
        player.sendMessage(config.getStringColored("messages.setting-changed"));
        return true;
    }

    final var game = Provider.instance().getManager().gameManager().game(arg0);

    if (game == null) {
      player.sendMessage(config.getStringColored("messages.game-not-found"));
      return true;
    }

    switch (subCommand.toLowerCase()) {
      case "setcornerone":
        game.zone().setCornerOne(Corner.of(player.getLocation()));
        player.sendMessage(config.getStringColored("messages.setting-changed"));
        return true;
      case "setcornertwo":
        game.zone().setCornerTwo(Corner.of(player.getLocation()));
        player.sendMessage(config.getStringColored("messages.setting-changed"));
        return true;
      case "setcapturetime":
        if (args.length < 3) {
          this.sendHelpMessage(player);
          return true;
        }
        int captureTime = Integer.parseInt(args[2]);
        game.scheduler().setCaptureTime(captureTime);
        player.sendMessage(config.getStringColored("messages.setting-changed"));
        return true;
      case "setcapturereward":
        ItemStack item = player.getInventory().getItemInHand();
        final var rewards = new KothRewards();
        if (item.getType() == Material.WRITTEN_BOOK) {
          BookMeta bookMeta = (BookMeta) item.getItemMeta();

          if (bookMeta != null) {
            List<String> pages = bookMeta.getPages();
            rewards.setCommands(new HashSet<>(pages));
            game.rewards(rewards);
            player.sendMessage(config.getStringColored("messages.setting-changed"));
          }
        }
        return true;
    }

    return true;
  }

  public void sendHelpMessage(@NotNull final Player player){
    final var config = Provider.instance().getManager().config();
    List<String> helpMessage = config.getConfiguration().getStringList("messages.koth-help-command");
    TextComponent[] components = new TextComponent[helpMessage.size()];

    for (int i = 0; i < helpMessage.size(); i++) {
      String line = helpMessage.get(i);
      TextComponent component = new TextComponent(ChatColor.translateAlternateColorCodes('&', line));
      components[i] = component;
      components[i].addExtra("\n");
    }

    player.spigot().sendMessage(components);
  }
}
