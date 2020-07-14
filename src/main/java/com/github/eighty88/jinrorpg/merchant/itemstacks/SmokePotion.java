package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SmokePotion {
    public static ItemStack getItemStack() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "投げると煙幕を張れる...?");
        ItemStack stack = new ItemStack(Material.LINGERING_POTION, 3);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "煙幕");
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }
}
