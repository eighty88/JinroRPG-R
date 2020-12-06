package com.github.eighty88.jinrorpg.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SpawnEggMeta;

public class ShopKeeperCommand {
    public static boolean onCommand(CommandSender sender, String args) {
        try {
            if (!(sender instanceof Player)) {
                sender.sendMessage("このコマンドはゲーム内から実行してください。");
            } else {
                Player player = (Player) sender;
                ItemStack spawnegg = new ItemStack(Material.VILLAGER_SPAWN_EGG);
                SpawnEggMeta meta = (SpawnEggMeta) spawnegg.getItemMeta();
                assert meta != null;
                if (args.equals("weapon")) {
                    meta.setDisplayName(ChatColor.GREEN + "武器");
                } else if (args.equals("item")) {
                    meta.setDisplayName(ChatColor.GREEN + "アイテム");
                } else {
                    return false;
                }
                spawnegg.setItemMeta(meta);
                player.getInventory().addItem(spawnegg);
            }
        } catch (Exception ignored) {}
        return true;
    }
}
