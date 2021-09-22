package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Objects;

public class FTHeart {
    public static ItemStack getItemStack() {
        ItemStack result = new ItemStack(Material.HEART_OF_THE_SEA, 1);
        ItemMeta meta = result.getItemMeta();
        Objects.requireNonNull(meta).setDisplayName(ChatColor.RESET + "占い師の心");
        meta.setLore(Arrays.asList(
                ChatColor.AQUA + "名前が書かれている看板を右クリックすると、",
                ChatColor.AQUA + "その人の役職が分かる。",
                ChatColor.AQUA + "共犯者は村人と出る。"
        ));
        result.setItemMeta(meta);
        return result;
    }
}
