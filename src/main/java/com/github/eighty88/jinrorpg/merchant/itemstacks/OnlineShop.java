package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class OnlineShop {
    public static ItemStack getItemStack() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "右クリックでSHOPのGUIが開ける。");
        lore.add(ChatColor.AQUA + "1度使うと消える。");
        ItemStack result = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = result.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RESET + "オンラインショップ");
        meta.setLore(lore);
        result.setItemMeta(meta);
        return result;
    }
}
