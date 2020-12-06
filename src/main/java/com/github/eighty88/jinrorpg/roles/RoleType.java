package com.github.eighty88.jinrorpg.roles;

import org.bukkit.ChatColor;

public enum RoleType {
    NONE("役職無し", "役職無し"),
    WATCHING(ChatColor.GRAY + "観戦", "観戦"),
    WEREWOLF(ChatColor.RED + "人狼", ChatColor.RED + "人狼"),
    ACCOMPLICE(ChatColor.GRAY + "共犯者", ChatColor.GREEN + "村人"),
    VAMPIRE(ChatColor.DARK_PURPLE + "吸血鬼", ChatColor.DARK_PURPLE + "吸血鬼"),
    INNOCENT(ChatColor.GREEN + "村人", ChatColor.GREEN + "村人");

    private String Name;

    private String FortuneTelling;

    RoleType(String Name, String FortuneTelling) {
        this.Name = Name;
        this.FortuneTelling = FortuneTelling;
    }

    public String toString() {
        return this.Name;
    }

    public String getFortuneTelling() {
        return this.FortuneTelling;
    }
}
