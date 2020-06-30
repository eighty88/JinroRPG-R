package com.github.eighty88.jinrorpg.controller;

import com.github.eighty88.jinrorpg.JinroRPG;
import com.github.eighty88.jinrorpg.player.JinroPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;

public class TimeController {
    private static int TimeScheduler;

    private static int SkeletonScheduler;

    public static boolean isDay;

    private static boolean TimerStarted;

    public static void StartTimer() {
        SkeletonScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(JinroRPG.getJinroPlugin(), () -> {
            if (!isDay) {
                Collections.shuffle(GameController.ArmorStands);
                ArmorStand armorStand = GameController.ArmorStands.get(0);
                Entity EntitySkeleton = armorStand.getWorld().spawnEntity(armorStand.getLocation(), EntityType.SKELETON);
                LivingEntity skeleton = (LivingEntity) EntitySkeleton;
                EntityEquipment equipment = skeleton.getEquipment();
                assert equipment != null;
                equipment.clear();
                equipment.getItemInMainHand().setType(Material.AIR);
                skeleton.setHealth(2.0);
                for(JinroPlayer player: JinroRPG.JinroPlayers.values()) {
                    if(player.isVampire() || player.isUsingKnights()) {
                        player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 127, true));
                    }
                }
            }
        }, 0L, 5L);
        isDay = true;
        TimerStarted = true;
        DayCycle();
    }

    public static void StopTimer() {
        Bukkit.getScheduler().cancelTask(SkeletonScheduler);
        Bukkit.getScheduler().cancelTask(TimeScheduler);
        TimerStarted = false;
    }

    public static void DayCycle() {
        if(TimerStarted) {
            if(isDay) {
                for(JinroPlayer player:JinroRPG.JinroPlayers.values()) {
                    player.onDay();
                }
                for(World world:Bukkit.getServer().getWorlds()) {
                    world.setTime(6000);
                    try {
                        for (Entity entity : world.getEntitiesByClass(Skeleton.class)) {
                            entity.remove();
                        }
                    } catch(Exception ignored) {}
                }
                TimeScheduler = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(JinroRPG.getJinroPlugin(), () -> {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle(ChatColor.BLUE + "夜になりました", "", 1, 25, 5);
                    }
                    BossBarController.ChangeBar(ChatColor.BLUE + "NIGHT TIME", BarColor.BLUE, null, null);
                    isDay = !isDay;
                    DayCycle();
                }, 2000L);
            } else {
                for(World world:Bukkit.getServer().getWorlds()) {
                    world.setTime(6000);
                }
                TimeScheduler = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(JinroRPG.getJinroPlugin(), () -> {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle(ChatColor.YELLOW + "昼になりました", "", 1, 25, 5);
                    }
                    BossBarController.ChangeBar(ChatColor.YELLOW + "DAY TIME", BarColor.YELLOW, null, null);
                    isDay = !isDay;
                    DayCycle();
                }, 2000L);
            }
        }
    }
}
