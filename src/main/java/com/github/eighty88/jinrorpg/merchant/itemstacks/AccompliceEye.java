package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class AccompliceEye {
    public static ItemStack getItemStack() {
        ItemStack result = new ItemStack(Material.END_CRYSTAL);
        ItemMeta meta = result.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RESET + "共犯者の目");
        meta.setLore(Arrays.asList(
                ChatColor.AQUA + "共犯者専用のアイテム。",
                ChatColor.AQUA + "右クリックで人狼が一人わかる。",
                ChatColor.AQUA + "共犯者しか買えないため、見られるとばれるが、",
                ChatColor.AQUA + "人狼にアピールするためにも使える。"
        ));
        result.setItemMeta(meta);
        return result;
    }
}
