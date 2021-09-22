package com.github.eighty88.jinrorpg.playerlist;

import com.github.eighty88.jinrorpg.JinroRPG;
import com.github.eighty88.jinrorpg.controller.GameController;
import com.github.eighty88.jinrorpg.player.JinroPlayer;
import com.github.eighty88.jinrorpg.roles.RoleType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class OperatorList {
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
            if(jinroPlayer.getRole().equals(RoleType.WATCHING) || jinroPlayer.getRole().equals(RoleType.NONE)) {
                lore.add(ChatColor.RESET.toString() + ChatColor.GRAY + "観戦");
            } else {
                lore.add(jinroPlayer.getRole().toString());
                lore.add(jinroPlayer.isDead() ? ChatColor.RESET.toString() + ChatColor.GRAY + "死亡" : ChatColor.RESET.toString() + ChatColor.YELLOW + "生存");
                lore.add(jinroPlayer.isUsingKnights() ? ChatColor.RESET.toString() + ChatColor.YELLOW + "騎士の祈り使用中" : ChatColor.RESET.toString() + ChatColor.GRAY + "騎士の祈りは使用していません");
                lore.add(ChatColor.RESET.toString());
                lore.add(player.getGameMode().equals(GameMode.SPECTATOR) ? ChatColor.YELLOW + "クリックでテレポート" : "");
            }
            skullMeta.setLore(lore);
            head.setItemMeta(skullMeta);
            inv.addItem(head);
        }
        player.openInventory(inv);
    }
}
