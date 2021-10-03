package com.github.eighty88.jinrorpg;

import com.github.eighty88.jinrorpg.command.*;
import com.github.eighty88.jinrorpg.controller.BossBarController;
import com.github.eighty88.jinrorpg.controller.GameController;
import com.github.eighty88.jinrorpg.controller.TimeController;
import com.github.eighty88.jinrorpg.merchant.GameMerchant;
import com.github.eighty88.jinrorpg.player.JinroPlayer;
import com.github.eighty88.jinrorpg.roles.RoleType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class JinroRPG extends JavaPlugin {
    public static boolean isStarted;

    public static HashMap<Player, JinroPlayer> JinroPlayers = new HashMap<>();

    public static String GameMessage = ChatColor.RED + "[人狼RPG]" + ChatColor.GREEN + ": " + ChatColor.AQUA;

    private static JinroRPG instance;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();
        String confFilePath=getDataFolder() + File.separator + "config.yml";
        try(Reader reader=new InputStreamReader(new FileInputStream(confFilePath), StandardCharsets.UTF_8)){
            FileConfiguration conf=new YamlConfiguration();
            conf.load(reader);
            GameMessage = ChatColor.RED + "[" + conf.getString("plugin.name") + "]" + ChatColor.GREEN + ": " + ChatColor.AQUA;

            GameMerchant.name = conf.getString("shop.name");
            EventListener.name = conf.getString("chat.spiritworld.name");
            EventListener.allowChat = conf.getBoolean("chat.spiritworld.allow");
            EventListener.Chance = conf.getInt("skeleton.dropchance");
            TimeController.period = conf.getLong("skeleton.spawnticks");
            TimeController.health = conf.getDouble("skeleton.health");
            TimeController.equipment = conf.getBoolean("skeleton.equipment");
            TimeController.dayTitle = conf.getString("times.day.message");
            TimeController.dayBarName = conf.getString("times.day.name");
            TimeController.daytime = conf.getLong("times.day.seconds");
            TimeController.nightTitle = conf.getString("times.night.message");
            TimeController.nightBarName = conf.getString("times.night.name");
            TimeController.nighttime = conf.getLong("times.night.seconds");
        }catch(Exception e){
            System.out.println(e.getMessage());
            onDisable();
        }
        JinroPlayers.clear();
        for(Player player: Bukkit.getOnlinePlayers()) {
            JinroPlayers.put(player, new JinroPlayer(player));
        }
        Bukkit.getPluginManager().registerEvents(new EventListener(), this);
        isStarted = false;
        BossBarController.TickEvent();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
                if(isStarted) {
                    for(JinroPlayer player:JinroPlayers.values()) {
                        player.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.BLUE + "占い可能回数: " + player.getFT()));
                    }
                }
        }, 1, 1);
    }

    @Override
    public void onDisable() {
        if(isStarted) {
            GameController.End(RoleType.NONE);
        }
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
                return ShopKeeperCommand.onCommand(sender, args[0]);
            case "armorstand":
                return ArmorStandCommand.onCommand(sender);
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(sender instanceof Player && command.getName().equals("shopkeeper")) {
            List<String> result = new ArrayList<>();
            result.add("item");
            result.add("weapon");
            return result;
        }
        return null;
    }

    public static JinroRPG getInstance() {
        return instance;
    }
}
