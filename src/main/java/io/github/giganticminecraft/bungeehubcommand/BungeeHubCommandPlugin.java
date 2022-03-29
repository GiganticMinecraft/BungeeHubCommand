package io.github.giganticminecraft.bungeehubcommand;

import java.io.IOException;

import net.md_5.bungee.api.plugin.Plugin;

class BungeeHubCommandPlugin extends Plugin {
  public void onEnable() {
    try {
      final var config = new ConfigurationLoader(this.getDataFolder()).loadUpToDateConfig();
      final var command = new BungeeHubCommand(config, this.getProxy());

      this.getProxy().getPluginManager().registerCommand(this, command);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
