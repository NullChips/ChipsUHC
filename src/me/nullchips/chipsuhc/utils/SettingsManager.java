package me.nullchips.chipsuhc.utils;

import me.nullchips.chipsuhc.features.Feature;
import me.nullchips.chipsuhc.features.FeatureManager;
import me.nullchips.chipsuhc.features.HealthList;
import me.nullchips.chipsuhc.game.GameCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by Tommy on 13/02/2017.
 */
public class SettingsManager {

    private SettingsManager() { }

    private static SettingsManager instance = new SettingsManager();

    public static SettingsManager getInstance() {
        return instance;
    }

    private ChatUtils cu = ChatUtils.getInstance();
    private FeatureManager fm = FeatureManager.getInstance();
    private GameCore gc = GameCore.getInstance();
    private SpreadPlayers sp = SpreadPlayers.getInstace();

    Plugin p;
    FileConfiguration config;
    File cfile;

    public void setup(Plugin p) {
        config = p.getConfig();
        config.options().copyDefaults(true);
        cfile = new File(p.getDataFolder(), "config.yml");
        saveConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(cfile);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Unable to create config.yml");
        }
    }

    public PluginDescriptionFile getDesc() {
        return p.getDescription();
    }

    public void loadConfigSettings() {

        if(this.getConfig().contains("settings.message-prefix") && this.getConfig().get("settings.message-prefix") instanceof String) {
            String prefix = this.getConfig().getString("settings.message-prefix");
            cu.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
        } else {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not find settings.message-prefix in config.yml. Shutting down server...");
            Bukkit.shutdown();
        }

        if(this.getConfig().contains("settings.uhc-world-name") && this.getConfig().get("settings.uhc-world-name") instanceof String) {
            String worldName = this.getConfig().getString("settings.message-prefix");
            gc.setGameWorldName(worldName);
        } else {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not find settings.uhc-world-name in config.yml. Match will be unable to be started.");
            gc.setGameWorldName(null);
        }

        if(this.getConfig().contains("settings.spread-chunk-delay") && this.getConfig().get("settings.spread-chunk-delay") instanceof Integer) {
            sp.setChunkLoadDelay(this.getConfig().getInt("settings.spread-chunk-delay"));
        } else {
            sp.setChunkLoadDelay(750);
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not find settings.spread-chunk-delay in config.yml. Using default value of 30.");
        }

        if(this.getConfig().contains("settings.spread-max-tries") && this.getConfig().get("settings.spread-max-tries") instanceof Integer) {
            sp.setMaxTires(this.getConfig().getInt("settings.spread-max-tries"));
        } else {
            sp.setMaxTires(750);
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not find settings.spread-max-tries in config.yml. Using default value of 750.");
        }

        registerFeatures();

        for(Feature feature : fm.getAllFeatures()) {
            String s = this.getConfig().getString("features." + feature.getConfigId());
            if(s.equalsIgnoreCase("true")) feature.setEnabled(true);
            else feature.setEnabled(false);
        }

    }

    private void registerFeatures() {
        fm.addFeature(new HealthList());
    }

}
