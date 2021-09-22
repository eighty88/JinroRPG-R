package com.github.eighty88.jinrorpg.playerlist;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class DefaultList {
    public static void OpenInv(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Joined Players");
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
            assert skullMeta != null;
            skullMeta.setOwningPlayer(p);
            skullMeta.setDisplayName(ChatColor.RESET.toString() + ChatColor.AQUA + p.getName());
            head.setItemMeta(skullMeta);
            inv.addItem(head);
        }
        player.openInventory(inv);
    }
}
