package com.github.eighty88.jinrorpg.merchant.itemstacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JinroAxe {
    @SuppressWarnings("deprecation")
    public static ItemStack getItemStack() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "人狼専用の斧。プレイヤーを一撃で倒せる。");
        ItemStack result = new ItemStack(Material.STONE_AXE, 1, (short) 130);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "人狼の斧");
        meta.setLore(lore);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(),"generic.attackDamage", 1000.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        result.setItemMeta(meta);
        return result;
    }
}