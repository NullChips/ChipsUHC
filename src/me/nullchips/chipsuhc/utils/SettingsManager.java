package me.nullchips.chipsuhc.utils;

import me.nullchips.chipsuhc.features.Feature;
import me.nullchips.chipsuhc.features.FeatureManager;
import me.nullchips.chipsuhc.features.HealthList;
import me.nullchips.chipsuhc.game.BorderShrink;
import me.nullchips.chipsuhc.game.BorderShrinkManager;
import me.nullchips.chipsuhc.game.GameCore;
import me.nullchips.chipsuhc.game.GameTimeManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import javax.swing.border.Border;
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
    private SpreadPlayers sp = SpreadPlayers.getInstance();
    private BorderUtils bu = BorderUtils.getInstance();
    private GameTimeManager gtm = GameTimeManager.getInstance();
    private BorderShrinkManager bsm = BorderShrinkManager.getInstance();

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
            String worldName = this.getConfig().getString("settings.uhc-world-name");
            gc.setGameWorldName(worldName);
        } else {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not find settings.uhc-world-name in config.yml. Match will be unable to be started.");
            gc.setGameWorldName(null);
        }

        if(this.getConfig().contains("settings.spread-chunk-delay") && this.getConfig().get("settings.spread-chunk-delay") instanceof Integer) {
            sp.setChunkLoadDelay(this.getConfig().getInt("settings.spread-chunk-delay"));
            if(sp.getChunkLoadDelay() <= 0) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "settings.spread-chunk-delay in config.yml cannot be less than 1, using default value of 750");
                sp.setChunkLoadDelay(30);
            }
        } else {
            sp.setChunkLoadDelay(30);
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not find settings.spread-chunk-delay in config.yml. Using default value of 30.");
        }

        if(this.getConfig().contains("settings.spread-max-tries") && this.getConfig().get("settings.spread-max-tries") instanceof Integer) {
            sp.setMaxTries(this.getConfig().getInt("settings.spread-max-tries"));
            if(sp.getMaxTries() <= 0) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "settings.spread-max-tries in config.yml cannot be less than 1. Using default value of 750.");
                sp.setMaxTries(750);
            }
        } else {
            sp.setMaxTries(750);
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not find settings.spread-max-tries in config.yml. Using default value of 750.");
        }

        if(this.getConfig().contains("settings.spread-min-distance") && this.getConfig().get("settings.spread-min-distance") instanceof Integer) {
            sp.setMinDistance(this.getConfig().getInt("settings.spread-min-distance"));
            if(sp.getMinDistance() <= 0) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "settings.spread-min-distance in config.yml cannot be less than 1. Using default value of 100.");
                sp.setMinDistance(100);
            }
        } else {
            sp.setMinDistance(100);
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not find settings.spread-min-distance in config.yml. Using default value of 100.");
        }



        if(this.getConfig().contains("settings.borders.start-radius") && this.getConfig().get("settings.borders.start-radius") instanceof Integer) {
            bu.setStartRadius(this.getConfig().getInt("settings.borders.start-radius"));
            if(bu.getStartRadius() < 0) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "settings.borders.start-radius in config.yml cannot be less than 1, using default value of 1000.");
                bu.setStartRadius(1000);
            }
        } else {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not find settings.borders.start-radius in config.yml. Using default value of 1000.");
            bu.setStartRadius(1000);
        }

        if(this.getConfig().contains("settings.starter-food-amount") && this.getConfig().get("settings.starter-food-amount") instanceof Integer) {
            gc.setStarterFoodAmount(this.getConfig().getInt("settings.starter-food-amount"));
            if(bu.getStartRadius() < 0) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "settings.starter-food-amount in config.yml cannot be less than 0, using default value of 10.");
                gc.setStarterFoodAmount(10);
            }
        } else {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not find settings.starter-food-amount in config.yml. Using default value of 10.");
            gc.setStarterFoodAmount(10);
        }

        if(this.getConfig().contains("settings.mins-until-final-heal") && this.getConfig().get("settings.mins-until-final-heal") instanceof Integer) {
            gtm.setMinsTillFinalHeal(this.getConfig().getInt("settings.mins-until-final-heal"));
            if(bu.getStartRadius() < 0) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "settings.mins-until-final-heal in config.yml cannot be less than 0, using default value of 10.");
                gtm.setMinsTillFinalHeal(10);
            }
        } else {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not find settings.mins-until-final-heal in config.yml. Using default value of 10.");
            gtm.setMinsTillFinalHeal(10);
        }

        registerFeatures();
        registerBorderShrinks();

        for(Feature feature : fm.getAllFeatures()) {
            String s = this.getConfig().getString("features." + feature.getConfigId());
            if(s.equalsIgnoreCase("true")) feature.setEnabled(true);
            else feature.setEnabled(false);
        }

    }

    private void registerBorderShrinks() {
        int id = 1;
        for(;;) {
            if(this.borderShrinkUsable(id)) {
                bsm.addBorderShrink(new BorderShrink(this.getConfig().getInt("settings.border-shrink-" + id + ".border-size"), this.getConfig().getInt("settings.border-shrink-" + id + ".time-in-game")));
                Bukkit.getServer().getLogger().info("Added border shrink with size of " + this.getConfig().getInt("settings.border-shrink-" + id + ".border-size")
                + ". Border shrink takes effect " + this.getConfig().getInt("settings.border-shrink-" + id + ".time-in-game") + " mins into the game.");
                id++;
            } else {
                break;
            }
        }
    }

    private boolean borderShrinkUsable(int id) {
        if (this.getConfig().contains("settings.border-shrink-" + id + ".border-size") && this.getConfig().get("settings.border-shrink-" + id + ".border-size") instanceof Integer
                && this.getConfig().contains("settings.border-shrink-" + id + ".time-in-game") && this.getConfig().get("settings.border-shrink-" + id + ".time-in-game") instanceof Integer) {
            return true;
        } else {
            return false;
        }
    }

    private void registerFeatures() {
        fm.addFeature(new HealthList());
    }

}
