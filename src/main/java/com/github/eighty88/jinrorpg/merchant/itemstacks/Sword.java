package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class Sword {
    public static ItemStack getItemStack() {
        ItemStack result = new ItemStack(Material.WOODEN_SWORD, 1);
        ItemMeta meta = result.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RESET +"スケ狩りの剣 (30/30)");
        meta.setLore(Collections.singletonList("30"));
        result.setItemMeta(meta);
        return result;
    }
}
