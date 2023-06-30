package com.beyazpoliss.emperor.koth;

import com.beyazpoliss.emperor.koth.api.EnableDisableManager;
import com.beyazpoliss.emperor.koth.loader.EnableDisableManagerImpl;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class EmperorKoth extends JavaPlugin {

  @Getter
  private EnableDisableManager manager;

  @Override
  public void onEnable() {
    Provider.instance(this);
    this.manager = new EnableDisableManagerImpl();
    this.manager.enable(this);
  }

  @Override
  public void onDisable() {
    this.manager.disable(this);
  }
}
