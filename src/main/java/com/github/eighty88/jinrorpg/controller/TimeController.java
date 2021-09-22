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

    public static long period = 10;

    public static double health = 2.0;

    public static boolean equipment = false;

    public static boolean isDay = true;

    private static boolean TimerStarted;

    public static long daytime = 100;

    public static String dayTitle = "昼になりました";

    public static String dayBarName = "DAY TIME";

    public static long nighttime = 100;

    public static String nightTitle = "夜になりました";

    public static String nightBarName = "NIGHT TIME";

    public static boolean beacon = false;

    public static void StartTimer() {
        SkeletonScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(JinroRPG.getJinroPlugin(), () -> {
            if (!isDay) {
                Collections.shuffle(GameController.ArmorStands);
                ArmorStand armorStand = GameController.ArmorStands.get(0);
                Entity EntitySkeleton = armorStand.getWorld().spawnEntity(armorStand.getLocation(), EntityType.SKELETON);
                LivingEntity skeleton = (LivingEntity) EntitySkeleton;
                if(!equipment) {
                    EntityEquipment equipment = skeleton.getEquipment();
                    assert equipment != null;
                    equipment.clear();
                    equipment.getItemInMainHand().setType(Material.AIR);
                }
                skeleton.setHealth(health);
                for(JinroPlayer player: JinroRPG.JinroPlayers.values()) {
                    if(player.isVampire() || player.isUsingKnights()) {
                        player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 127, true));
                    }
                }
                if(beacon) {
                    for(JinroPlayer player: JinroRPG.JinroPlayers.values()) {
                        player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1, true));
                    }
                }
            }
        }, 0L, period);
        BossBarController.ChangeBar(ChatColor.YELLOW + dayBarName, BarColor.YELLOW, null, null);
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
                        player.sendTitle(ChatColor.BLUE + nightTitle, "", 1, 25, 5);
                    }
                    BossBarController.ChangeBar(ChatColor.BLUE + nightBarName, BarColor.BLUE, null, null);
                    isDay = !isDay;
                    DayCycle();
                }, daytime * 20);
            } else {
                for(World world:Bukkit.getServer().getWorlds()) {
                    world.setTime(18000);
                }
                TimeScheduler = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(JinroRPG.getJinroPlugin(), () -> {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.sendTitle(ChatColor.YELLOW + dayTitle, "", 1, 25, 5);
                    }
                    BossBarController.ChangeBar(ChatColor.YELLOW + dayBarName, BarColor.YELLOW, null, null);
                    beacon = false;
                    isDay = !isDay;
                    DayCycle();
                }, nighttime * 20);
            }
        }
    }
}
