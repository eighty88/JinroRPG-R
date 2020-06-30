package com.github.eighty88.jinrorpg.command;

import com.github.eighty88.jinrorpg.JinroRPG;
import com.github.eighty88.jinrorpg.controller.GameController;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class StartCommand {
    public static Location location;
    public static boolean onCommand(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            location = player.getLocation();
            for(Player p: Bukkit.getOnlinePlayers()) {
                p.teleport(location);
            }
        } else {
            location.setX(Integer.parseInt(args[0]));
            location.setY(Integer.parseInt(args[1]));
            location.setZ(Integer.parseInt(args[2]));
            location.setWorld((((List<Player>) Bukkit.getOnlinePlayers()).get(0).getWorld()));
            for(Player p: Bukkit.getOnlinePlayers()) {
                p.teleport(location);
            }
        }
        if(JinroRPG.isStarted) {
            GameController.End(null);
        }
        GameController.Start();
        return true;
    }
}
