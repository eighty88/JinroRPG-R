package com.github.eighty88.jinrorpg.merchant.itemstacks;

import com.github.eighty88.jinrorpg.JinroRPG;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class Sword {
    public static ItemStack getItemStack() {
        List<String> lore = new ArrayList<>();
        lore.add("30");
        ItemStack result = new ItemStack(Material.WOODEN_SWORD, 1);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(ChatColor.RESET +"スケ狩りの剣 (30/30)");
        meta.setLore(lore);
        meta.getPersistentDataContainer().set(new NamespacedKey(JinroRPG.getJinroPlugin(), "Durability"), PersistentDataType.INTEGER, 30);
        result.setItemMeta(meta);
        return result;
    }
}
