package io.github.giganticminecraft.bungeehubcommand;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public final class BungeeHubCommand extends Command {
  private static final BaseComponent[] messageWhenExecutedByNonPlayer = new ComponentBuilder()
      .color(ChatColor.RED).append("/hub command can only be used by a player.")
      .create();

  private final TextComponent messageWhenPlayerIsAlreadyConnected;
  private final ServerInfo hubServer;

  public BungeeHubCommand(BungeeHubCommandConfiguration config, ProxyServer server) {
    super("hub", "bungeehubcommand.hub");

    this.messageWhenPlayerIsAlreadyConnected = new TextComponent(config.alreadyConnectedMessage());
    this.hubServer = server.getServerInfo(config.hubServerName());

    if (this.hubServer == null) {
      throw new IllegalArgumentException("Server " + config.hubServerName() + " not found.");
    }
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof ProxiedPlayer)) {
      sender.sendMessage(messageWhenExecutedByNonPlayer);
    }

    final var player = (ProxiedPlayer) sender;

    if (player.getServer().getInfo().getName().equals(this.hubServer.getName())) {
      player.sendMessage(this.messageWhenPlayerIsAlreadyConnected);
    } else {
      player.connect(this.hubServer);
    }
  }
}
