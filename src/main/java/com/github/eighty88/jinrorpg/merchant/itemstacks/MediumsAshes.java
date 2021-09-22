package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class MediumsAshes {
    public static ItemStack getItemStack() {
        ItemStack result = new ItemStack(Material.GUNPOWDER, 1);
        ItemMeta meta = result.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RESET + "霊媒師の遺灰");
        meta.setLore(Collections.singletonList(ChatColor.AQUA + "右クリックすると、死んでいる人が全員わかる。"));
        result.setItemMeta(meta);
        return result;
    }
}
