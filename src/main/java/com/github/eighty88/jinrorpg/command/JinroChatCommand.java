package com.github.eighty88.jinrorpg.command;

import com.github.eighty88.jinrorpg.controller.GameController;
import com.github.eighty88.jinrorpg.player.JinroPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JinroChatCommand {
    public static  boolean onCommand(CommandSender sender, String args) {
        if(sender instanceof Player) {
            JinroPlayer player = JinroPlayer.getJinroPlayer((Player) sender);
            if(player.isJinro()) {
                for(JinroPlayer jinro: GameController.Jinro) {
                    jinro.getPlayer().sendMessage(ChatColor.RED + "[人狼チャット]" + player.getName() + ": " + args);
                }
            }
            return true;
        }
        return false;
    }
}
