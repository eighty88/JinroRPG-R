package com.github.eighty88.jinrorpg.playerlist;

import com.github.eighty88.jinrorpg.JinroRPG;
import com.github.eighty88.jinrorpg.player.JinroPlayer;
import com.github.eighty88.jinrorpg.roles.RoleType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayerList {
    public static void OpenGUI(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Joined Players");
        for (Player p : JinroRPG.JinroPlayers.keySet()) {
            JinroPlayer jinroPlayer = JinroPlayer.getJinroPlayer(p);
            List<String> lore = new ArrayList<>();
            ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
            assert skullMeta != null;
            skullMeta.setOwningPlayer(p);
            skullMeta.setDisplayName(ChatColor.RESET.toString() + ChatColor.AQUA + p.getName());
            if(jinroPlayer.getRole().equals(RoleType.NONE) || jinroPlayer.getRole().equals(RoleType.WATCHING)) {
                lore.add(ChatColor.RESET.toString() + ChatColor.GRAY + "観戦");
                skullMeta.setLore(lore);
                head.setItemMeta(skullMeta);
                inv.addItem(head);
                continue;
            }
            if (JinroPlayer.getJinroPlayer(player).isJinro()) {
                if (jinroPlayer.isJinro()) {
                    lore.add(ChatColor.RESET.toString() + ChatColor.RED + "人狼");
                }
            } else if (p.getName().equals(player.getName())) {
                lore.add(ChatColor.RESET + jinroPlayer.getRole().toString());
            }
            skullMeta.setLore(lore);
            head.setItemMeta(skullMeta);
            inv.addItem(head);
        }
        player.openInventory(inv);
    }
}
