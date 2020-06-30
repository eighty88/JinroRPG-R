package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class FTHeart {
    public static ItemStack getItemStack() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "名前が書かれている看板を右クリックすると、");
        lore.add(ChatColor.AQUA + "その人の役職が分かる。");
        lore.add(ChatColor.AQUA + "共犯者は村人と出る。");
        ItemStack result = new ItemStack(Material.STICK, 1);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "占い師の心");
        meta.setLore(lore);
        result.setItemMeta(meta);
        return result;
    }
}
