package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class Bow {
    public static ItemStack getItemStack() {
        ItemStack result = new ItemStack(Material.BOW);
        ItemMeta meta = result.getItemMeta();
        assert meta != null;
        meta.setLore(Collections.singletonList(ChatColor.AQUA + "当てた相手を一撃で倒せる。"));
        result.setItemMeta(meta);
        return result;
    }
}
