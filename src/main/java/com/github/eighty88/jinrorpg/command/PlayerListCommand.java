package com.github.eighty88.jinrorpg.command;

import com.github.eighty88.jinrorpg.JinroRPG;
import com.github.eighty88.jinrorpg.playerlist.DefaultList;
import com.github.eighty88.jinrorpg.playerlist.OperatorList;
import com.github.eighty88.jinrorpg.playerlist.PlayerList;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerListCommand {
    public static boolean onCommand(CommandSender sender) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(JinroRPG.isStarted) {
                if(player.getGameMode().equals(GameMode.SPECTATOR)) {
                    OperatorList.OpenGUI(player);
                } else {
                    PlayerList.OpenGUI(player);
                }
            } else {
                DefaultList.OpenInv(player);
            }
        }
        return false;
    }
}
