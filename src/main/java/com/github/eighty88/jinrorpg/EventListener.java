package com.github.eighty88.jinrorpg;

import com.github.eighty88.jinrorpg.api.LunaChatTranslateAPI;
import com.github.eighty88.jinrorpg.api.TranslateType;
import com.github.eighty88.jinrorpg.command.StartCommand;
import com.github.eighty88.jinrorpg.controller.BossBarController;
import com.github.eighty88.jinrorpg.controller.GameController;
import com.github.eighty88.jinrorpg.controller.LivingPlayerController;
import com.github.eighty88.jinrorpg.controller.TimeController;
import com.github.eighty88.jinrorpg.merchant.GameMerchant;
import com.github.eighty88.jinrorpg.merchant.itemstacks.*;
import com.github.eighty88.jinrorpg.player.JinroPlayer;
import com.github.eighty88.jinrorpg.roles.RoleType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.Objects;

public class EventListener implements Listener {

    public static boolean allowBeacon = true;

    public static String name = "霊界チャット";

    public static boolean allowChat = true;
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage(JinroRPG.GameMessage + e.getPlayer().getName() + "さんがログインしました");
        if(!JinroRPG.JinroPlayers.containsKey(e.getPlayer())) {
            JinroPlayer player = new JinroPlayer(e.getPlayer());
            JinroRPG.JinroPlayers.put(e.getPlayer(), player);
            if(JinroRPG.isStarted) {
                BossBarController.addPlayer(player.getPlayer());
                e.getPlayer().setGameMode(GameMode.SPECTATOR);
                e.getPlayer().teleport(StartCommand.location);
                e.getPlayer().sendMessage(JinroRPG.GameMessage + "すでにゲームが開始していたため、スペクテイターになりました。");
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        e.setQuitMessage(JinroRPG.GameMessage + e.getPlayer().getName() + "さんがログアウトしました");
        if(!JinroRPG.isStarted) {
            JinroRPG.JinroPlayers.remove(e.getPlayer());
        } else if(JinroPlayer.getJinroPlayer(e.getPlayer()).getRole().equals(RoleType.NONE) || JinroPlayer.getJinroPlayer(e.getPlayer()).getRole().equals(RoleType.WATCHING)) {
            JinroRPG.JinroPlayers.remove(e.getPlayer());
        } else {
            JinroPlayer jinroPlayer = JinroPlayer.getJinroPlayer(e.getPlayer());
            jinroPlayer.death();
            LivingPlayerController.PlayerDeath(jinroPlayer);
            jinroPlayer.getPlayer().setGameMode(GameMode.SPECTATOR);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if(JinroRPG.isStarted) {
            e.getDrops().clear();
            JinroPlayer.getJinroPlayer(e.getEntity()).death();
            LivingPlayerController.PlayerDeath(JinroPlayer.getJinroPlayer(e.getEntity()));
            Bukkit.getScheduler().scheduleSyncDelayedTask(JinroRPG.getJinroPlugin(), () -> e.getEntity().spigot().respawn(), 1);
            Bukkit.getScheduler().scheduleSyncDelayedTask(JinroRPG.getJinroPlugin(), () -> e.getEntity().teleport(StartCommand.location), 5);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if(!TimeController.isDay && JinroRPG.isStarted) {
            e.setCancelled(true);
        }
        if(e.getPlayer().getGameMode().equals(GameMode.SPECTATOR) && allowChat) {
            e.setCancelled(true);
            String Message = ChatColor.GRAY + "["+ name + "]" + e.getPlayer().getName() + ": " + e.getMessage();
            try {
                Message += ChatColor.GOLD + " (" +  LunaChatTranslateAPI.Translate(e.getMessage(), TranslateType.GOOGLE_IME) + ")";
                for(Player player:Bukkit.getOnlinePlayers()) {
                    if(player.getGameMode().equals(GameMode.SPECTATOR)) {
                        player.sendMessage(Message);
                    }
                }
            } catch (Exception ignored) {
                for(Player player:Bukkit.getOnlinePlayers()) {
                    if(player.getGameMode().equals(GameMode.SPECTATOR)) {
                        player.sendMessage(ChatColor.GRAY + "["+name+"]" + e.getPlayer().getName() + ": " + e.getMessage());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent e) {
        Entity entity = e.getEntity();
        e.setCancelled(true);
        entity.getWorld().createExplosion(entity.getLocation(), 0);
    }

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent e) {
        try {
            Entity entity = e.getRightClicked();
            if (!(entity instanceof Villager))
                return;
            if (Objects.requireNonNull(entity.getCustomName()).equalsIgnoreCase(ChatColor.GREEN + "ShopKeeper")) {
                e.setCancelled(true);
                Player player = e.getPlayer();
                GameMerchant.openGUI(JinroPlayer.getJinroPlayer(player));
            }
        } catch (Exception ignored) {}
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        try {
            Block block = e.getClickedBlock();
            Player player = e.getPlayer();
            assert block != null;
            if(block.getState() instanceof Sign) {
                Sign sign = (Sign) block.getState();
                String[] lines = sign.getLines();
                String l2 = lines[1];
                if (l2.equalsIgnoreCase("右クリックで役職記録")) {
                    if (JinroRPG.isStarted) {
                        sign.setLine(1, player.getName());
                        GameController.Signs.add(sign);
                        sign.update();
                    } else {
                        player.sendMessage(JinroRPG.GameMessage + "まだゲームはスタートしていません!");
                    }
                } else {
                    if(JinroRPG.isStarted) {
                        Player p = Bukkit.getServer().getPlayerExact(l2);
                        if (player.getInventory().getItemInMainHand().getType().equals(Material.QUARTZ)) {
                            JinroPlayer jinroPlayer = JinroPlayer.getJinroPlayer(p);
                            for(Player pl:Bukkit.getServer().getOnlinePlayers()) {
                                pl.sendMessage(JinroRPG.GameMessage + player.getName() + "さんが大魔導士の心を使用しました。");
                                assert p != null;
                                pl.sendMessage(JinroRPG.GameMessage + p.getName() + "は" + jinroPlayer.getRole().getFortuneTelling() + ChatColor.AQUA + "です");
                                pl.sendTitle(p.getName() + "の役職 : " + jinroPlayer.getRole().getFortuneTelling(), "", 1, 50, 5);
                            }
                            JinroPlayer.getJinroPlayer(p).deSpell();
                            player.getInventory().removeItem(SFTHeart.getItemStack());
                        }
                        if (!TimeController.isDay) {
                            if (player.getInventory().getItemInMainHand().getType().equals(Material.STICK)) {
                                JinroPlayer jinroPlayer = JinroPlayer.getJinroPlayer(p);
                                assert p != null;
                                player.sendMessage(JinroRPG.GameMessage + p.getName() + "は" + jinroPlayer.getRole().getFortuneTelling() + ChatColor.AQUA + "です");
                                player.sendTitle(p.getName() + "の役職 : " + jinroPlayer.getRole().getFortuneTelling(), "", 1, 50, 5);
                                if(jinroPlayer.isUsingSpell()) {
                                    p.sendMessage(JinroRPG.GameMessage + player.getName() + "に占われました!");
                                    jinroPlayer.deSpell();
                                }
                                player.getInventory().removeItem(FTHeart.getItemStack());
                            } else if (player.getInventory().getItemInMainHand().getType().equals(Material.SKELETON_SKULL)) {
                                JinroPlayer jinroPlayer = JinroPlayer.getJinroPlayer(p);
                                assert p != null;
                                if (jinroPlayer.isDead()) {
                                    player.sendMessage(JinroRPG.GameMessage + p.getName() + "は死亡しています。");
                                    player.sendTitle(p.getName() + " : " + ChatColor.GRAY + "死亡", "", 1, 50, 5);
                                } else {
                                    player.sendMessage(JinroRPG.GameMessage + p.getName() + "は生存しています。");
                                    player.sendTitle(p.getName() + " : " + ChatColor.YELLOW + "生存", "", 1, 50, 5);
                                }
                                player.getInventory().removeItem(Mediumsheart.getItemStack());
                            }
                        }
                    }
                }
            } else if (e.getClickedBlock().getType() == Material.CHEST && JinroRPG.isStarted) {
                e.setCancelled(true);
                GameMerchant.openGUI(JinroPlayer.getJinroPlayer(e.getPlayer()));
            } else if (Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta()).getDisplayName().equals(ChatColor.RESET + "プロビデンスの眼光") && ( e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK )) {
                for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                    if (e.getPlayer().getName().equals(pl.getName())) {
                        pl.sendMessage(JinroRPG.GameMessage + "プロビデンスの眼光を使用しました");
                    } else {
                        pl.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 600, 1, true));
                    }
                }
                player.getInventory().removeItem(ProvidenceEyes.getItemStack());
            } else if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.RESET + "共犯者の目") && ( e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK )) {
                if (JinroRPG.isStarted) {
                    e.getPlayer().sendMessage(JinroRPG.GameMessage + "人狼の一人は" + GameController.Jinro.get(0).getName() + "です。");
                    Collections.shuffle(GameController.Jinro);
                }
                e.getPlayer().getInventory().removeItem(AccompliceEye.getItemStack());
            } else if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.RESET + "騎士の祈り") && ( e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK )) {
                if (JinroRPG.isStarted) {
                    if (!TimeController.isDay) {
                        if(JinroPlayer.getJinroPlayer(e.getPlayer()).isJinro()) {
                            e.getPlayer().sendMessage(JinroRPG.GameMessage + "人狼は騎士の祈りを使用することはできません!");
                            return;
                        }
                        JinroPlayer.getJinroPlayer(e.getPlayer()).useKnights();
                        e.getPlayer().getInventory().removeItem(Knights.getItemStack());
                        e.getPlayer().sendMessage(JinroRPG.GameMessage + "騎士の祈りを使用しました。");
                    } else {
                        e.getPlayer().sendMessage(JinroRPG.GameMessage + "昼の間は使用することができません!");
                    }
                }
            } else if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.RESET + "天恵の呪符") && ( e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK )) {
                if (JinroRPG.isStarted) {
                    if (!TimeController.isDay) {
                        JinroPlayer.getJinroPlayer(e.getPlayer()).useSpell();
                        e.getPlayer().getInventory().removeItem(Spell.getItemStack());
                        e.getPlayer().sendMessage(JinroRPG.GameMessage + "天恵の呪符を使用しました。");
                    } else {
                        e.getPlayer().sendMessage(JinroRPG.GameMessage + "昼の間は使用することができません!");
                    }
                }
            } else if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.RESET + "オンラインショップ") && ( e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK )) {
                GameMerchant.openGUI(JinroPlayer.getJinroPlayer(e.getPlayer()));
                e.getPlayer().getInventory().removeItem(OnlineShop.getItemStack());
            }
        } catch (Exception ignored) {}
    }

    @EventHandler
    public void InventoryOnClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        if(e.getView().getTitle().equals(ChatColor.GOLD + "Joined Players")) {
            if(item == null || !item.hasItemMeta()) {
                return;
            } else if(player.getGameMode().equals(GameMode.SPECTATOR) && item.getType().equals(Material.PLAYER_HEAD)) {
                SkullMeta meta = (SkullMeta) item.getItemMeta();
                try {
                    assert meta != null;
                    Player p = Bukkit.getPlayerExact(Objects.requireNonNull(Objects.requireNonNull(meta.getOwningPlayer()).getName()));
                    assert p != null;
                    player.teleport(p);
                } catch (Exception ignored) {}
            }
            e.setCancelled(true);
        } else if(e.getView().getTopInventory().getType().equals(InventoryType.DROPPER) || e.getView().getTopInventory().getType().equals(InventoryType.DISPENSER)) {
            if(!player.getGameMode().equals(GameMode.CREATIVE)) {
                if(!item.getItemMeta().getDisplayName().equals(ChatColor.RESET + "地雷")) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent e) {
        Entity hitPlayer = e.getHitEntity();

        try {
            if (e.getEntity() instanceof Snowball) {
                if (hitPlayer instanceof Player) {
                    Player player = (Player) hitPlayer;
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1, true));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 127, true));
                    Location location = player.getLocation();
                    player.getWorld().playSound(location, Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST_FAR, 10, 1);
                    player.getWorld().spawnParticle(Particle.FLASH, location, 10, 0.1, 0.1, 0.1, 0.1);
                }
            } else if (e.getEntity() instanceof Arrow) {
                e.getEntity().remove();
                ((LivingEntity) Objects.requireNonNull(e.getHitEntity())).damage(1000);
            }
        } catch (Exception ignored) {}
    }

    @EventHandler
    public void onShot(ProjectileLaunchEvent e) {
        if(e.getEntity() instanceof Snowball) {
            e.getEntity().setVelocity(e.getEntity().getVelocity().multiply(2));
        }
    }

    @EventHandler
    public void onPlayerPickUpItem(EntityPickupItemEvent e) {
        if(e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if(e.getItem().getItemStack().equals(Mine.getItemStack())) {
                JinroPlayer p = JinroPlayer.getJinroPlayer(player);
                if(p.isInnocent()) {
                    player.damage(1000);
                    player.getWorld().createExplosion(player.getLocation(), 0);
                    player.getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, player.getLocation(), 500);
                    e.setCancelled(true);
                    e.getItem().remove();
                } else if(p.isVampire()) {
                    if(TimeController.isDay) {
                        player.damage(1000);
                        player.getWorld().createExplosion(player.getLocation(), 0);
                        player.getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, player.getLocation(), 500);
                        player.getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, player.getLocation(), 500, 1, 1, 1);
                        e.setCancelled(true);
                        e.getItem().remove();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerAttacked(EntityDamageByEntityEvent e) {
        try {
            if (e.getDamager() instanceof Player) {
                if (e.getEntity() instanceof Player) {
                    Player damager = (Player) e.getDamager();
                    Player player = (Player) e.getEntity();
                    JinroPlayer jinroPlayer = JinroPlayer.getJinroPlayer(player);
                    if (JinroRPG.isStarted) {
                        if (Objects.requireNonNull(damager.getInventory().getItemInMainHand().getItemMeta()).getDisplayName().equals(ChatColor.RESET + "聖なる十字架")) {
                            if (jinroPlayer.isVampire()) {
                                jinroPlayer.death();
                                LivingPlayerController.PlayerDeath(jinroPlayer);
                                player.setGameMode(GameMode.SPECTATOR);
                                Location location = player.getLocation();
                                location.setY(location.getY() + 1);
                                player.getWorld().spawnParticle(Particle.END_ROD, location, 100, 0.1, 0.1, 0.1, 0.1);
                            }
                            damager.getInventory().removeItem(HolyCross.getItemStack());
                        } else if (JinroPlayer.getJinroPlayer(damager).isRobbery() && damager.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.RESET + "強盗の剣")) {
                            if (jinroPlayer.isJinro()) {
                                damager.sendTitle("あなたの役職は" + ChatColor.RED + "人狼" + ChatColor.WHITE + "になりました", "", 1, 50, 5);
                                StringBuilder OtherJinro = new StringBuilder();
                                JinroPlayer.getJinroPlayer(damager).setRole(RoleType.WEREWOLF);
                                for (JinroPlayer pl : GameController.Jinro) {
                                    OtherJinro.append(pl.getName()).append(" ");
                                    pl.getPlayer().sendMessage(JinroRPG.GameMessage + damager.getName() + "の役職は" + ChatColor.RED + "人狼" + ChatColor.WHITE + "になりました");
                                }
                                damager.sendMessage(JinroRPG.GameMessage + "あなたの役職 : " + ChatColor.RED + "人狼 (" + OtherJinro + ")");
                                GameController.Jinro.add(JinroPlayer.getJinroPlayer(damager));
                                JinroPlayer.getJinroPlayer(damager).setRole(RoleType.WEREWOLF);
                                player.damage(1000);
                            } else if (jinroPlayer.isVampire()) {
                                if (TimeController.isDay) {
                                    JinroPlayer.getJinroPlayer(damager).setRole(RoleType.VAMPIRE);
                                    damager.sendTitle("あなたの役職は" + ChatColor.DARK_PURPLE + "吸血鬼" + ChatColor.WHITE + "になりました", "", 1, 50, 5);
                                    damager.sendMessage(JinroRPG.GameMessage + "あなたの役職 : " + ChatColor.DARK_PURPLE + "吸血鬼");
                                    player.damage(1000);
                                }
                            } else {
                                if (!jinroPlayer.isUsingKnights()) {
                                    damager.sendTitle("あなたの役職は" + jinroPlayer.getRole().toString() + ChatColor.WHITE + "になりました", "", 1, 50, 5);
                                    damager.sendMessage(JinroRPG.GameMessage + "あなたの役職 : " + jinroPlayer.getRole().toString());
                                    JinroPlayer.getJinroPlayer(damager).setRole(jinroPlayer.getRole());
                                    player.damage(1000);
                                }
                            }
                            damager.getInventory().removeItem(RobberySword.getItemStack());
                        } else if(damager.getInventory().getItemInMainHand().getType().equals(Material.STONE_AXE)) {
                            if(jinroPlayer.isJinro()) {
                                damager.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1, true));
                                damager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 127, true));
                            } else {
                                player.damage(1000);
                                Location location = player.getLocation();
                                location.setY(location.getY() + 1);
                                player.getWorld().spawnParticle(Particle.BLOCK_CRACK, location, 100, 0.1, 0.1, 0.1, 0.1, Material.REDSTONE_BLOCK.createBlockData());
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) {}
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if(e.getEntity() instanceof Skeleton) {
            if(e.getEntity().getKiller() != null) {
                e.setDroppedExp(0);
                e.getDrops().clear();
                int en = (int) Math.ceil(Math.random() * 2);
                if(en == 1) {
                    e.getEntity().getKiller().getInventory().addItem(new ItemStack(Material.EMERALD, 1));
                }
            }
        }
    }

    @EventHandler
    public void onOpenInventory(InventoryOpenEvent e) {
        if(e.getInventory().getType().equals(InventoryType.BEACON)) {
            if (e.getPlayer().getInventory().contains(new ItemStack(Material.EMERALD, 10)) && !TimeController.isDay && allowBeacon) {
                e.getPlayer().getInventory().removeItem(new ItemStack(Material.EMERALD, 10));
                TimeController.beacon = true;
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onSplashPotion(LingeringPotionSplashEvent e) {
        if(Objects.requireNonNull(e.getEntity().getItem().getItemMeta()).getDisplayName().equals(ChatColor.RESET + "煙幕")) {
            e.getEntity().getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, e.getEntity().getLocation(), 1000, 0, 0,0 , 0.005);
            e.getEntity().getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, e.getEntity().getLocation(), 1000, 0, 0,0 , 0.01);
            e.getEntity().getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, e.getEntity().getLocation(), 1000, 0, 0,0 , 0.015);
            e.getEntity().getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, e.getEntity().getLocation(), 1000, 0, 0,0 , 0.02);
            e.getEntity().getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, e.getEntity().getLocation(), 1000, 0, 0,0 , 0.025);
            e.getEntity().getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, e.getEntity().getLocation(), 1000, 0, 0,0 , 0.03);
            e.getEntity().getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, e.getEntity().getLocation(), 1000, 0, 0,0 , 0.035);
            e.getEntity().getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, e.getEntity().getLocation(), 1000, 0, 0,0 , 0.04);
            e.getEntity().getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, e.getEntity().getLocation(), 1000, 0, 0,0 , 0.045);
            e.getEntity().getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, e.getEntity().getLocation(), 1000, 0, 0,0 , 0.05);
            e.setCancelled(true);
        }
    }
}
