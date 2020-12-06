package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MediumsAshes {
    public static ItemStack getItemStack() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "右クリックすると、死んでいる人が全員わかる。");
        ItemStack result = new ItemStack(Material.GUNPOWDER, 1);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "霊媒師の遺灰");
        meta.setLore(lore);
        result.setItemMeta(meta);
        return result;
    }
}
