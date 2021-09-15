package com.github.eighty88.jinrorpg.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArmorStandCommand {
    public static boolean onCommand(CommandSender sender) {
        if(sender instanceof Player) {
            ItemStack result = new ItemStack(Material.STICK);
            ItemMeta meta = result.getItemMeta();
            assert meta != null;
            meta.setDisplayName("人狼RPG用防具立て");
            meta.setLore(Arrays.asList(
                    ChatColor.AQUA + "人狼RPG用アイテム。",
                    ChatColor.AQUA + "右クリックでタグ付きのアーマースタンドを召喚できる。",
                    ChatColor.AQUA + "スケルトンのスポナー的な感じになる。"
            ));
            result.setItemMeta(meta);
            ((Player) sender).getInventory().addItem(result);
            return true;
        }
        return false;
    }
}
