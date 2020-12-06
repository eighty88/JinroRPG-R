package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Bow {
    public static ItemStack getItemStack() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "当てた相手を一撃で倒せる。");
        ItemStack result = new ItemStack(Material.BOW);
        ItemMeta meta = result.getItemMeta();
        assert meta != null;
        meta.setLore(lore);
        result.setItemMeta(meta);
        return result;
    }
}
