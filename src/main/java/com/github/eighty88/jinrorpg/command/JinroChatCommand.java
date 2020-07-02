package com.github.eighty88.jinrorpg.command;

import com.github.eighty88.jinrorpg.api.LunaChatTranslateAPI;
import com.github.eighty88.jinrorpg.api.TranslateType;
import com.github.eighty88.jinrorpg.controller.GameController;
import com.github.eighty88.jinrorpg.player.JinroPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JinroChatCommand {
    public static  boolean onCommand(CommandSender sender, String args) {
        if(sender instanceof Player) {
            JinroPlayer player = JinroPlayer.getJinroPlayer((Player) sender);
            if(player.isJinro() && !player.isDead()) {
                for(JinroPlayer jinro: GameController.Jinro) {
                    jinro.getPlayer().sendMessage(ChatColor.RED + "[人狼チャット]" + player.getName() + ": " + args + ChatColor.GOLD + " (" + LunaChatTranslateAPI.Translate(args, TranslateType.GOOGLE_IME) + ")");
                }
            }
            return true;
        }
        return false;
    }
}
