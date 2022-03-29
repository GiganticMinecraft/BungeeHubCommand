package io.github.giganticminecraft.bungeehubcommand;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public record ConfigurationLoader(File dataFolder) {
  private static final String configFileName = "config.yml";

  private void writeDefaultConfigIfConfigNotExists() throws IOException {
    if (!dataFolder.exists()) {
      dataFolder.mkdirs();
    }

    final var configFile = new File(dataFolder, configFileName);
    if (!configFile.exists()) {
      try (final var stream = this.getClass().getClassLoader().getResourceAsStream(configFileName)) {
        Files.copy(stream, configFile.toPath());
      }
    }
  }

  public BungeeHubCommandConfiguration loadUpToDateConfig() throws IOException {
    writeDefaultConfigIfConfigNotExists();

    final var configFile = new File(dataFolder, configFileName);

    final var cProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
    final var loadedConfig = cProvider.load(configFile);

    return new BungeeHubCommandConfiguration(
        loadedConfig.getString("hub-server-name"),
        loadedConfig.getString("already-connected-message"));
  }
}
