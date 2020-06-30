package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Bow {
    @SuppressWarnings("deprecation")
    public static ItemStack getItemStack() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "当てた相手を一撃で倒せる。");
        ItemStack result = new ItemStack(Material.BOW, 1, (short) 384);
        ItemMeta meta = result.getItemMeta();
        assert meta != null;
        meta.addEnchant(Enchantment.ARROW_DAMAGE, 32767, true);
        meta.setLore(lore);
        result.setItemMeta(meta);
        return result;
    }
}
