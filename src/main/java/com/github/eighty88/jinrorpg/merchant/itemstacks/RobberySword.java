package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class RobberySword {
    public static ItemStack getItemStack() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "強盗専用アイテム");
        lore.add(ChatColor.AQUA + "一回だけ殴った相手の役職を盗める。");
        lore.add(ChatColor.AQUA + "盗んだ相手は死ぬ。");
        ItemStack result = new ItemStack(Material.GOLDEN_SWORD, 1);
        ItemMeta meta = result.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RESET + "強盗の剣");
        meta.setLore(lore);
        meta.setUnbreakable(true);
        result.setItemMeta(meta);
        return result;
    }
}
