package com.github.eighty88.jinrorpg.player;

import com.github.eighty88.jinrorpg.JinroRPG;
import com.github.eighty88.jinrorpg.roles.RoleType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class JinroPlayer implements Comparable<JinroPlayer> {
    private Player player;

    private RoleType roleType = RoleType.NONE;

    private boolean isUseKnights = false;

    private boolean isUseSpell = false;

    private boolean isDeath = false;

    public String getName() {
        return player.getName();
    }

    public Player getPlayer() {
        return this.player;
    }

    public RoleType getRole() {
        return this.roleType;
    }

    public void setRole(RoleType Role) {
        this.roleType = Role;
    }

    public boolean isJinro() {
        return this.roleType.equals(RoleType.WEREWOLF);
    }

    public boolean isAccomplice() {
        return this.roleType.equals(RoleType.ACCOMPLICE);
    }

    public boolean isVampire() {
        return this.roleType.equals(RoleType.VAMPIRE);
    }

    public boolean isRobbery() {
        return this.roleType.equals(RoleType.ROBBERY);
    }

    public boolean isInnocent() {
        return this.roleType.equals(RoleType.INNOCENT);
    }

    public JinroPlayer(Player player) {
        this.player = player;
    }

    public void useKnights() {
        this.isUseKnights = true;
    }

    public void RemoveKnightsEffect() {
        this.isUseKnights = false;
    }

    public boolean isUsingKnights() {
        return this.isUseKnights;
    }

    public void useSpell() {
        this.isUseSpell = true;
    }

    public void RemoveSpellEffect() {
        this.isUseSpell = false;
    }

    public boolean isUsingSpell() {
        return this.isUseSpell;
    }

    public void deSpell() {
        this.isUseSpell = false;
    }

    public void death() {
        this.isDeath = true;
    }

    public boolean isDead() {
        return this.isDeath;
    }

    public void resetFlags() {
        isUseKnights = false;
        isUseSpell = false;
        isDeath = false;
    }

    public void onDay() {
        isUseKnights = false;
        isUseSpell = false;
    }

    public boolean equals(Object other) {
        return !(other instanceof JinroPlayer) ? false : this.toString().equals(other.toString());
    }

    @Override
    public int compareTo(JinroPlayer other) {
        return this.toString().compareTo(other.toString());
    }

    public static JinroPlayer getJinroPlayer(Player player) {
        return JinroRPG.JinroPlayers.get(player);
    }

    public static void RefreshPlayers() {
        JinroRPG.JinroPlayers.clear();
        for(Player player: Bukkit.getOnlinePlayers()) {
            JinroRPG.JinroPlayers.put(player, new JinroPlayer(player));
        }
    }
}
