package com.github.eighty88.jinrorpg.command;

import com.github.eighty88.jinrorpg.JinroRPG;
import com.github.eighty88.jinrorpg.player.JinroPlayer;
import com.github.eighty88.jinrorpg.roles.RoleType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WatchingModeCommand {
    public static boolean onCommand(CommandSender sender) {
        if(sender instanceof Player && !JinroRPG.isStarted) {
            JinroPlayer player = JinroPlayer.getJinroPlayer((Player) sender);
            if (player.getRole().equals(RoleType.WATCHING)) {
                player.setRole(RoleType.NONE);
                player.getPlayer().sendMessage(JinroRPG.GameMessage + "観戦モードがオフになりました");
            } else {
                player.setRole(RoleType.WATCHING);
                player.getPlayer().sendMessage(JinroRPG.GameMessage + "観戦モードがオンになりました");
            }
            return true;
        }
        return false;
    }
}
