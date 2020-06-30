package com.github.eighty88.jinrorpg.controller;

import com.github.eighty88.jinrorpg.JinroRPG;
import com.github.eighty88.jinrorpg.JinroRPGAPI;
import com.github.eighty88.jinrorpg.player.JinroPlayer;
import com.github.eighty88.jinrorpg.roles.RoleType;
import org.bukkit.*;
import org.bukkit.block.Sign;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController {
    public static List<ArmorStand> ArmorStands = new ArrayList<>();

    public static List<JinroPlayer> Jinro = new ArrayList<>();

    public static List<JinroPlayer> Innocent = new ArrayList<>();

    public static List<Sign> Signs = new ArrayList<>();

    public static JinroPlayer Vampire;

    public static JinroPlayer Accomplice;

    public static JinroPlayer Robbery;

    public static void Start() {
        if(Bukkit.getOnlinePlayers().size() <= 4) {
            Bukkit.broadcastMessage(JinroRPG.GameMessage + "人数が不足していたためスタートできませんでした");
        }
        if(!LotteryRoles()) {
            return;
        }
        for(World world:Bukkit.getServer().getWorlds()) {
            try {
                for (Entity entity : world.getEntitiesByClass(ArmorStand.class)) {
                    ArmorStand stand = (ArmorStand) entity;
                    ArmorStands.add(stand);
                    stand.setVisible(false);
                    stand.setSmall(true);
                    stand.setGravity(false);
                }
            } catch(Exception ignored) {}
        }

        for(JinroPlayer p: JinroRPG.JinroPlayers.values()) {
            Player player = p.getPlayer();
            if(p.getRole().equals(RoleType.WATCHING)) {
                player.setGameMode(GameMode.SPECTATOR);
                continue;
            } else {
                player.setGameMode(GameMode.ADVENTURE);
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 128000, 4, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 127, true));
            player.getInventory().clear();
            player.setPlayerListName("    ");
            player.getWorld().setDifficulty(Difficulty.EASY);
            player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 3));
            if(p.isJinro()) {
                player.sendTitle("あなたの役職 : " + ChatColor.RED + "人狼", ChatColor.GOLD.toString() + ChatColor.UNDERLINE + "GAME START", 1, 50, 5);
                StringBuilder OtherJinro = new StringBuilder();
                for(JinroPlayer jinro: Jinro) {
                    OtherJinro.append(jinro.getName()).append(" ");
                }
                player.sendMessage(JinroRPG.GameMessage + "あなたの役職 : " + ChatColor.RED + "人狼 (" + OtherJinro + ")");
            } else {
                player.sendTitle("あなたの役職 : " + p.getRole().toString() , ChatColor.GOLD.toString() + ChatColor.UNDERLINE + "GAME START", 1, 50, 5);
                player.sendMessage(JinroRPG.GameMessage + "あなたの役職 : " + p.getRole().toString());
            }
        }
        JinroRPG.isStarted = true;
        BossBarController.CreateBossBar(ChatColor.RED + "人狼RPG", BarColor.GREEN, BarStyle.SOLID);
        TimeController.StartTimer();
        JinroRPGAPI.onStart();
    }

    public static void End(RoleType winner) {
        for (JinroPlayer player : JinroRPG.JinroPlayers.values()) {
            if (winner != null) {
                player.getPlayer().sendTitle(winner.toString() + "の勝利", ChatColor.GOLD.toString() + ChatColor.UNDERLINE + "GAME END", 1, 50, 5);
            }
            player.resetFlags();
        }
        StringBuilder JinroName = new StringBuilder();
        for(JinroPlayer p:Jinro) {
            JinroName.append(p.getName()).append(" ");
        }
        StringBuilder InnocentName = new StringBuilder();
        for(JinroPlayer p:Innocent) {
            InnocentName.append(p.getName()).append(" ");
        }
        Bukkit.broadcastMessage(ChatColor.GREEN + "-----=====" + ChatColor.UNDERLINE + "今回の役職" + ChatColor.RESET.toString() + ChatColor.GREEN + "=====-----");
        Bukkit.broadcastMessage(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "人狼");
        Bukkit.broadcastMessage(ChatColor.DARK_RED.toString() + JinroName);
        Bukkit.broadcastMessage(ChatColor.GRAY.toString() + ChatColor.BOLD + "共犯者");
        Bukkit.broadcastMessage(ChatColor.GRAY.toString() + Accomplice.getName());
        Bukkit.broadcastMessage(ChatColor.RED.toString() + ChatColor.BOLD + "吸血鬼");
        Bukkit.broadcastMessage(ChatColor.RED.toString() + Vampire.getName());
        Bukkit.broadcastMessage(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "村人");
        Bukkit.broadcastMessage(ChatColor.DARK_GREEN.toString() + InnocentName);
        Bukkit.broadcastMessage(ChatColor.BLUE.toString() + ChatColor.BOLD + "強盗");
        Bukkit.broadcastMessage(ChatColor.BLUE.toString() + Robbery.getName());
        Bukkit.broadcastMessage(ChatColor.GREEN.toString() + ChatColor.STRIKETHROUGH + "==============================");

        JinroRPGAPI.onEnd();
        ResetRoles();
        TimeController.StopTimer();
        BossBarController.removeBossBar();
        LivingPlayerController.isVampireDead = false;
        ArmorStands.clear();
        Jinro.clear();
        JinroRPG.isStarted = false;
        JinroPlayer.RefreshPlayers();
        for(Sign sign: Signs) {
            sign.setLine(1, "右クリックで役職記録");
            sign.update();
        }
    }

    private static boolean LotteryRoles() {
        List<JinroPlayer> PlayerList = new ArrayList<>(JinroRPG.JinroPlayers.values());

        double jinrosize = PlayerList.size();
        int All = PlayerList.size();
        int Watching = 0;
        for(JinroPlayer player: PlayerList) {
            if(player.getRole().equals(RoleType.WATCHING)) {
                PlayerList.remove(player);
                Watching++;
            }
        }
        if(All - Watching <= 4) {
            Bukkit.broadcastMessage(JinroRPG.GameMessage + "人数が不足していたためスタートできませんでした");
            return false;
        }
        int jinrocount = (int)Math.ceil(jinrosize / 5);
        for (int count = 0; count < jinrocount; count++){
            Collections.shuffle(PlayerList);
            PlayerList.get(1).setRole(RoleType.WEREWOLF);
            Jinro.add(PlayerList.get(1));
            PlayerList.remove(count);
        }
        Collections.shuffle(PlayerList);
        PlayerList.get(1).setRole(RoleType.VAMPIRE);
        Vampire = PlayerList.get(1);
        PlayerList.remove(1);
        Collections.shuffle(PlayerList);
        PlayerList.get(1).setRole(RoleType.ACCOMPLICE);
        Accomplice = PlayerList.get(1);
        PlayerList.remove(1);
        PlayerList.get(1).setRole(RoleType.ROBBERY);
        Robbery = PlayerList.get(1);
        PlayerList.remove(1);
        Collections.shuffle(PlayerList);
        for(JinroPlayer player: PlayerList) {
            Innocent.add(player);
            player.setRole(RoleType.INNOCENT);
        }
        return true;
    }

    private static void ResetRoles() {
        List<JinroPlayer> PlayerList = new ArrayList<>(JinroRPG.JinroPlayers.values());
        for(JinroPlayer player: PlayerList) {
            if(!player.getRole().equals(RoleType.WATCHING)) {
                player.setRole(RoleType.NONE);
            }
        }
    }
}
