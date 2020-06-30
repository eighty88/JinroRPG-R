package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Knights {
    public static ItemStack getItemStack() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "右クリックで効果発動。");
        lore.add(ChatColor.AQUA + "使用した夜、すべての攻撃を無効化できる。人狼使用不可。");
        ItemStack result = new ItemStack(Material.ARMOR_STAND, 1);
        ItemMeta meta = result.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RESET + "騎士の祈り");
        meta.setLore(lore);
        result.setItemMeta(meta);
        return result;
    }
}
