package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class HolyCross {
    public static ItemStack getItemStack() {
        ItemStack result = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta meta = result.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RESET + "聖なる十字架");
        meta.setLore(Arrays.asList(
                ChatColor.AQUA + "吸血鬼を殴ると一撃で倒せる。",
                ChatColor.AQUA + "一度使うと消滅する。"
        ));
        result.setItemMeta(meta);
        return result;
    }
}
