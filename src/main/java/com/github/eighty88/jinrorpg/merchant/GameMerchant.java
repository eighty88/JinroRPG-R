package com.github.eighty88.jinrorpg.merchant;

import com.github.eighty88.jinrorpg.merchant.itemstacks.*;
import com.github.eighty88.jinrorpg.player.JinroPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;

public class GameMerchant {

    public static String name = "店員";

    public static void openGUI(JinroPlayer player, GameMerchant.MerchantType merchantType) {
        Merchant merchant = Bukkit.createMerchant(ChatColor.RESET + name);

        List<MerchantRecipe> recipes = new ArrayList<>();

        if(merchantType == MerchantType.WEAPON) {
            recipes.add(Recipe(Bow.getItemStack(), 2));
            recipes.add(Recipe(new ItemStack(Material.ARROW), 2));
            recipes.add(Recipe(new ItemStack(Material.COOKED_BEEF, 5), 1));
            recipes.add(Recipe(Sword.getItemStack(), 4));
            recipes.add(Recipe(StunGrenade.getItemStack(), 2));
            if(player.isJinro()) {
                recipes.add(Recipe(JinroAxe.getItemStack(),3));
            }
        } else {
            recipes.add(Recipe(Potions.getInvisiblePot(), 4));
            recipes.add(Recipe(Potions.getSpeedPot(), 4));
            recipes.add(Recipe(FTHeart.getItemStack(), 5));
            recipes.add(Recipe(HolyCross.getItemStack(), 2));
            recipes.add(Recipe(ProvidenceEyes.getItemStack(), 2));
            recipes.add(Recipe(Knights.getItemStack(), 4));
            recipes.add(Recipe(Spell.getItemStack(), 3));
            recipes.add(Recipe(MediumsAshes.getItemStack(), 12));
            if(player.isAccomplice()) {
                recipes.add(Recipe(AccompliceEye.getItemStack(), 5));
            }
        }

        merchant.setRecipes(recipes);

        player.getPlayer().openMerchant(merchant, true);
    }

    private static MerchantRecipe Recipe(ItemStack itemStack, int emerald) {
        MerchantRecipe recipe = new MerchantRecipe(itemStack, 64);
        recipe.addIngredient(new ItemStack(Material.EMERALD, emerald));
        return recipe;
    }

    public enum MerchantType {
        WEAPON(),ITEMS()
    }
}
