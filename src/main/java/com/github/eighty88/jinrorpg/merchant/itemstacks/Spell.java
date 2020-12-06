package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Spell {
    public static ItemStack getItemStack() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "右クリックで効果発動。");
        lore.add(ChatColor.AQUA + "使用した夜、いつ、誰に占われたかわかる。");
        lore.add(ChatColor.AQUA + "一回占われると効果が切れる。");
        ItemStack result = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = result.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RESET + "天恵の呪符");
        meta.setLore(lore);
        result.setItemMeta(meta);
        return result;
    }
}
