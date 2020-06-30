package com.github.eighty88.jinrorpg;

import com.github.eighty88.jinrorpg.api.LunaChatTranslateAPI;
import com.github.eighty88.jinrorpg.api.TranslateType;
import com.github.eighty88.jinrorpg.command.StartCommand;
import com.github.eighty88.jinrorpg.controller.GameController;
import com.github.eighty88.jinrorpg.controller.LivingPlayerController;
import com.github.eighty88.jinrorpg.controller.TimeController;
import com.github.eighty88.jinrorpg.merchant.GameMerchant;
import com.github.eighty88.jinrorpg.merchant.itemstacks.*;
import com.github.eighty88.jinrorpg.player.JinroPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.Objects;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage(JinroRPG.GameMessage + e.getPlayer().getName() + "さんがログインしました");
        if(JinroRPG.JinroPlayers.containsKey(e.getPlayer())) {
            JinroPlayer player = new JinroPlayer(e.getPlayer());
            JinroRPG.JinroPlayers.put(e.getPlayer(), player);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        e.setQuitMessage(JinroRPG.GameMessage + e.getPlayer().getName() + "さんがログアウトしました");
        if(!JinroRPG.isStarted) {
            JinroRPG.JinroPlayers.remove(e.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if(JinroRPG.isStarted) {
            JinroPlayer.getJinroPlayer(e.getEntity()).death();
            LivingPlayerController.PlayerDeath(JinroPlayer.getJinroPlayer(e.getEntity()));
            Bukkit.getScheduler().scheduleSyncDelayedTask(JinroRPG.getJinroPlugin(), () -> e.getEntity().spigot().respawn(), 1);
            Bukkit.getScheduler().scheduleSyncDelayedTask(JinroRPG.getJinroPlugin(), () -> e.getEntity().teleport(StartCommand.location), 5);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if(e.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) {
            e.setCancelled(true);
            String Message = ChatColor.GRAY + "[霊界チャット]" + e.getPlayer().getName() + ": " + e.getMessage();
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
                        player.sendMessage(ChatColor.GRAY + "[霊界チャット]" + e.getPlayer().getName() + ": " + e.getMessage());
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
        Entity entity = e.getRightClicked();
        if (!(entity instanceof Villager))
            return;
        if (Objects.requireNonNull(entity.getCustomName()).equalsIgnoreCase(ChatColor.GREEN + "ShopKeeper")) {
            e.setCancelled(true);
            Player player = e.getPlayer();
            GameMerchant.openGUI(JinroPlayer.getJinroPlayer(player));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
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
                        if (TimeController.isDay) {
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
                    e.getPlayer().sendMessage(JinroRPG.GameMessage + "人狼の一人は" + GameController.Jinro.get(1) + "です。");
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
            }
        } catch (Exception ignored) {}
    }

    @EventHandler
    public void onPlayerPickUpArrow(PlayerPickupArrowEvent e) {
        try {
            e.getArrow().remove();
            e.setCancelled(true);
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
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent e) {
        Entity hitPlayer = e.getHitEntity();

        if ((e.getEntity() instanceof Snowball)) {
            if(hitPlayer instanceof Player) {
                Player player = (Player) hitPlayer;
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 127, true));
            }
        }
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
                    e.setCancelled(true);
                    e.getItem().remove();
                } else if(p.isInnocent()) {
                    if(TimeController.isDay) {
                        player.damage(1000);
                        player.getWorld().createExplosion(player.getLocation(), 0);
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
                                player.setGameMode(GameMode.SPECTATOR);
                            }
                            damager.getInventory().removeItem(HolyCross.getItemStack());
                        } else if (JinroPlayer.getJinroPlayer(damager).isRobbery() && damager.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.RESET + "強盗の剣")) {
                            if (jinroPlayer.isJinro()) {
                                damager.sendTitle("あなたの役職は" + ChatColor.RED + "人狼" + ChatColor.WHITE + "になりました", "", 1, 50, 5);
                                StringBuilder OtherJinro = new StringBuilder();
                                for (JinroPlayer pl : GameController.Jinro) {
                                    OtherJinro.append(pl.getName()).append(" ");
                                    pl.getPlayer().sendMessage(JinroRPG.GameMessage + damager.getName() + "の役職は" + ChatColor.RED + "人狼" + ChatColor.WHITE + "になりました");
                                }
                                damager.sendMessage(JinroRPG.GameMessage + "あなたの役職 : " + ChatColor.RED + "人狼 (" + OtherJinro + ")");
                                GameController.Jinro.add(JinroPlayer.getJinroPlayer(damager));
                                player.damage(1000);
                            } else if (jinroPlayer.isVampire()) {
                                if (TimeController.isDay) {
                                    damager.sendTitle("あなたの役職は" + ChatColor.DARK_PURPLE + "吸血鬼" + ChatColor.WHITE + "になりました", "", 1, 50, 5);
                                    damager.sendMessage(JinroRPG.GameMessage + "あなたの役職 : " + ChatColor.DARK_PURPLE + "吸血鬼");
                                    player.damage(1000);
                                }
                            } else {
                                if (!jinroPlayer.isUsingKnights()) {
                                    damager.sendTitle("あなたの役職は" + jinroPlayer.getRole().toString() + ChatColor.WHITE + "になりました", "", 1, 50, 5);
                                    damager.sendMessage(JinroRPG.GameMessage + "あなたの役職 : " + ChatColor.GRAY + "共犯者");
                                    player.damage(1000);
                                }
                            }
                        }
                        damager.getInventory().removeItem(RobberySword.getItemStack());
                    }
                }
            }
        } catch (Exception ignored) {}
    }
}
