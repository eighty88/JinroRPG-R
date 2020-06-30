package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Sword {
    @SuppressWarnings("deprecation")
    public static ItemStack getItemStack() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "スケルトンをフルチャージ攻撃でワンパン出来る剣。");
        lore.add(ChatColor.AQUA + "30回くらい使えると思う。");
        ItemStack result = new ItemStack(Material.WOODEN_SWORD, 1, (short) 29);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(ChatColor.RESET +"スケ狩りの剣(30回くらい使えると思う)");
        meta.setLore(lore);
        result.setItemMeta(meta);
        return result;
    }
}
