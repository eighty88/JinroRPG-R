package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Mine {
    public static ItemStack getItemStack() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "人狼陣営専用アイテム");
        lore.add(ChatColor.AQUA + "人狼陣営以外が拾うと爆死する。");
        ItemStack result = new ItemStack(Material.BEACON, 1);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "地雷");
        meta.setLore(lore);
        result.setItemMeta(meta);
        return result;
    }
}
