package com.github.eighty88.jinrorpg.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ArmorStandCommand {
    public static boolean onCommand(CommandSender sender) {
        if(sender instanceof Player) {
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.AQUA + "人狼RPG用アイテム。");
            lore.add(ChatColor.AQUA + "右クリックでタグ付きのアーマースタンドを召喚できる。");
            lore.add(ChatColor.AQUA + "スケルトンのスポナー的な感じになる。");
            ItemStack result = new ItemStack(Material.STICK);
            ItemMeta meta = result.getItemMeta();
            assert meta != null;
            meta.setDisplayName("人狼RPG用防具立て");
            meta.setLore(lore);
            result.setItemMeta(meta);
            ((Player) sender).getInventory().addItem(result);
            return true;
        }
        return false;
    }
}
