package com.github.eighty88.jinrorpg.command;

import com.github.eighty88.jinrorpg.JinroRPG;
import com.github.eighty88.jinrorpg.controller.GameController;
import com.github.eighty88.jinrorpg.roles.RoleType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand {
    public static Location location;
    public static boolean onCommand(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            location = player.getLocation();
            for(Player p: Bukkit.getOnlinePlayers()) {
                p.teleport(location);
            }
        }
        if(JinroRPG.isStarted) {
            GameController.End(RoleType.NONE);
        }
        GameController.Start();
        return true;
    }
}
