package me.nullchips.chipsuhc.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Tommy on 14/02/2017.
 */
public class PlayerUtils {

    private PlayerUtils() { }

    private static PlayerUtils instance;

    public static PlayerUtils getInstance() {
        if(instance == null) {
            instance = new PlayerUtils();
        }
        return instance;
    }

    public void clearInventory(Player p) {
        p.getInventory().clear();
        p.getInventory().setHelmet(new ItemStack(Material.AIR));
        p.getInventory().setChestplate(new ItemStack(Material.AIR));
        p.getInventory().setLeggings(new ItemStack(Material.AIR));
        p.getInventory().setBoots(new ItemStack(Material.AIR));
    }

    public void heal(Player p) {
        p.setHealth(20);
    }

    public void feed(Player p) {
        p.setFoodLevel(20);
    }

    public void teleport(Location l, Player p) {
        p.teleport(l);
    }

}
