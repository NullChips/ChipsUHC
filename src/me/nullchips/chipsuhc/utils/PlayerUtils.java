package me.nullchips.chipsuhc.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

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

    private boolean allFrozen;
    private boolean canUnfreezeAll;
    private String freezeAllSender;
    private ArrayList<String> frozenPlayers = new ArrayList<>();

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

    public void freeze(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0));
        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 9));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE,9));
    }

    public void unfreeze(Player p) {
        p.removePotionEffect(PotionEffectType.BLINDNESS);
        p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        p.removePotionEffect(PotionEffectType.SLOW_DIGGING);
    }

    public boolean isAllFrozen() {
        return allFrozen;
    }

    public void setAllFrozen(boolean allFrozen) {
        this.allFrozen = allFrozen;
    }

    public ArrayList<String> getFrozenPlayers() {
        return frozenPlayers;
    }

    public boolean isFrozen(Player p) {
        if(this.frozenPlayers.contains(p.getUniqueId().toString()) || this.allFrozen) {
            return true;
        }
        return false;
    }

    public boolean canUnfreezeAll() {
        return canUnfreezeAll;
    }

    public void setCanUnfreezeAll(boolean canUnfreezeAll) {
        this.canUnfreezeAll = canUnfreezeAll;
    }

    public String getFreezeAllSender() {
        return freezeAllSender;
    }

    public void setFreezeAllSender(String freezeAllSender) {
        this.freezeAllSender = freezeAllSender;
    }
}
