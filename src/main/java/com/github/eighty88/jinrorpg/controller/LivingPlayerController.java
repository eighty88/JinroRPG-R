package com.github.eighty88.jinrorpg.controller;

import com.github.eighty88.jinrorpg.JinroRPG;
import com.github.eighty88.jinrorpg.player.JinroPlayer;
import com.github.eighty88.jinrorpg.roles.RoleType;
import org.bukkit.GameMode;


public class LivingPlayerController {
    public static boolean isVampireDead = false;

    public static void PlayerDeath(JinroPlayer player) {
        player.getPlayer().setGameMode(GameMode.SPECTATOR);
        if(player.isVampire()) {
            isVampireDead = true;
        }

        int jinro = 0;
        int innocent = 0;

        for(JinroPlayer p: JinroRPG.JinroPlayers.values()) {
            if(!p.isDead()) {
                if (p.getRole().equals(RoleType.WEREWOLF)) {
                    jinro++;
                } else if (p.getRole().equals(RoleType.ROBBERY)) {
                    innocent++;
                } else if (p.getRole().equals(RoleType.INNOCENT)) {
                    innocent++;
                }
            }
        }

        if(jinro == 0) {
            if(isVampireDead) {
                GameController.End(RoleType.INNOCENT);
            } else {
                GameController.End(RoleType.VAMPIRE);
            }
        }

        if(innocent == 0) {
            if(isVampireDead) {
                GameController.End(RoleType.WEREWOLF);
            } else {
                GameController.End(RoleType.VAMPIRE);
            }
        }
    }
}
