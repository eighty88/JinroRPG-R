package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Mediumsheart {
    public static ItemStack getItemStack() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "名前が書かれている看板を右クリックすると、");
        lore.add(ChatColor.AQUA + "その人が死んでいるかどうかが分かる。");
        ItemStack result = new ItemStack(Material.SKELETON_SKULL, 1);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "霊媒師の心");
        meta.setLore(lore);
        result.setItemMeta(meta);
        return result;
    }
}
