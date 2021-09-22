package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class StunGrenade {
    public static ItemStack getItemStack() {
        ItemStack result = new ItemStack(Material.SNOWBALL, 1);
        ItemMeta meta = result.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RESET + "スタングレネード");
        meta.setLore(Collections.singletonList(ChatColor.AQUA + "当てた相手は五秒間動けなくなる。"));
        result.setItemMeta(meta);
        return result;
    }
}
