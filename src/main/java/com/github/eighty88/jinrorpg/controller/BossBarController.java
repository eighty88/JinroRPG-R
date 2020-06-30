package com.github.eighty88.jinrorpg.controller;

import com.github.eighty88.jinrorpg.JinroRPG;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class BossBarController {
    private static BossBar bar;

    public static void CreateBossBar(String title, BarColor color, BarStyle style) {
        bar = Bukkit.createBossBar(title, color, style);
        for(Player player:Bukkit.getOnlinePlayers()) {
            bar.addPlayer(player);
        }
    }

    public static void removeBossBar() {
        bar.removeAll();
    }

    public static void addPlayer(Player player) {
        if(!bar.getPlayers().contains(player))  {
            bar.addPlayer(player);
        }
    }

    public static void TickEvent() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(JinroRPG.getPlugin(JinroRPG.class), () -> {
            if(JinroRPG.isStarted) {
                bar.setProgress(bar.getProgress() - 0.005);
            }
        }, 0L, 10L);
    }

    public static void ChangeBar(String title, BarColor color, @Nullable BarStyle style, @Nullable Double progress) {
        bar.setTitle(title);
        bar.setColor(color);
        if(style!=null) {
            bar.setStyle(style);
        }
        if(progress != null) {
            bar.setProgress(progress);
        } else {
            bar.setProgress(1);
        }
    }
}
