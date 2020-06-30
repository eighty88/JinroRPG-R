package com.github.eighty88.jinrorpg;

import com.github.eighty88.jinrorpg.command.*;
import com.github.eighty88.jinrorpg.controller.BossBarController;
import com.github.eighty88.jinrorpg.player.JinroPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class JinroRPG extends JavaPlugin {
    public static boolean isStarted;

    public static HashMap<Player, JinroPlayer> JinroPlayers = new HashMap<>();

    public static String GameMessage = ChatColor.RED + "[人狼RPG]" + ChatColor.GREEN + ": " + ChatColor.AQUA.toString();

    @Override
    public void onEnable() {
        JinroPlayers.clear();
        for(Player player: Bukkit.getOnlinePlayers()) {
            JinroPlayers.put(player, new JinroPlayer(player));
        }
        Bukkit.getPluginManager().registerEvents(new EventListener(), this);
        isStarted = false;
        BossBarController.TickEvent();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (command.getName()) {
            case "list":
                return PlayerListCommand.onCommand(sender);
            case "start":
                return StartCommand.onCommand(sender, args);
            case "gamesign":
                return GameSignCommand.onCommand(sender);
            case "end":
                return EndCommand.onCommand();
            case "shopkeeper":
                return ShopKeeperCommand.onCommand(sender);
            case "watching":
                return WatchingModeCommand.onCommand(sender);
        }
        return false;
    }

    public static Plugin getJinroPlugin() {
        return JavaPlugin.getPlugin(JinroRPG.class);
    }
}
