package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Knights {
    public static ItemStack getItemStack() {
        ItemStack result = new ItemStack(Material.ARMOR_STAND, 1);
        ItemMeta meta = result.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RESET + "騎士の祈り");
        meta.setLore(Arrays.asList(
                ChatColor.AQUA + "右クリックで効果発動。",
                ChatColor.AQUA + "使用した夜、すべての攻撃を無効化できる。人狼使用不可。"
        ));
        result.setItemMeta(meta);
        return result;
    }
}
