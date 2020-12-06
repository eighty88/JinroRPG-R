package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Potions {
    public static ItemStack getInvisiblePot() {
        ItemStack result = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) result.getItemMeta();
        assert meta != null;
        meta.addCustomEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 600, 1, true), true);
        meta.setColor(Color.WHITE);
        meta.setDisplayName(ChatColor.RESET + "透明化のポーション");
        result.setItemMeta(meta);
        return result;
    }

    public static ItemStack getSpeedPot() {
        ItemStack result = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) result.getItemMeta();
        assert meta != null;
        meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 20000000, 1, true), true);
        meta.setColor(Color.AQUA);
        meta.setDisplayName(ChatColor.RESET + "俊敏のポーション");
        result.setItemMeta(meta);
        return result;
    }
}
