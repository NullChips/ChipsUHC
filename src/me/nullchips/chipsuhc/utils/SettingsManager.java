package me.nullchips.chipsuhc.utils;

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
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Message prefix cannot be found in config.yml!");
        }

    }

}
