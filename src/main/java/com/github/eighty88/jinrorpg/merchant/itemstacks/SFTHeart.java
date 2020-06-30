package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SFTHeart {
    public static ItemStack getItemStack() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "名前が書かれている看板を右クリックすると、");
        lore.add(ChatColor.AQUA + "その人の役職が分かる。");
        lore.add(ChatColor.AQUA + "かつ全員に通知される。");
        lore.add(ChatColor.AQUA + "昼でも夜でも使える。");
        lore.add(ChatColor.AQUA + "共犯者は村人と出る。");
        ItemStack result = new ItemStack(Material.QUARTZ, 1);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "大魔道士の心");
        meta.setLore(lore);
        result.setItemMeta(meta);
        return result;
    }
}
