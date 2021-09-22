package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ProvidenceEyes {
    public static ItemStack getItemStack() {
        ItemStack result = new ItemStack(Material.SUNFLOWER, 1);
        ItemMeta meta = result.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RESET + "プロビデンスの眼光");
        meta.setLore(Arrays.asList(
                ChatColor.AQUA + "右クリックで効果発動。",
                ChatColor.AQUA + "自分以外の全員を光らせることができる。"
        ));
        result.setItemMeta(meta);
        return result;
    }
}
