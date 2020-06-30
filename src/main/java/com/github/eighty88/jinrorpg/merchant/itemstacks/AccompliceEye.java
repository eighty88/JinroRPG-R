package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AccompliceEye {
    public static ItemStack getItemStack() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "共犯者専用のアイテム。");
        lore.add(ChatColor.AQUA + "右クリックで人狼が一人わかる。");
        lore.add(ChatColor.AQUA + "共犯者しか買えないため、見られるとばれるが、");
        lore.add(ChatColor.AQUA + "人狼にアピールするためにも使える。");
        ItemStack result = new ItemStack(Material.END_CRYSTAL);
        ItemMeta meta = result.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RESET + "共犯者の目");
        meta.setLore(lore);
        result.setItemMeta(meta);
        return result;
    }
}
