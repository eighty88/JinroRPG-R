package com.github.eighty88.jinrorpg.command;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class GameSignCommand {
    public static boolean onCommand(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("このコマンドはゲーム内から実行してください。");
        } else {
            Player player = (Player) sender;
            ItemStack signStack = new ItemStack(Material.OAK_SIGN);
            ItemMeta itemMeta = signStack.getItemMeta();
            if (itemMeta instanceof BlockStateMeta) {
                BlockStateMeta blockStateMeta = (BlockStateMeta) itemMeta;
                BlockState blockState = blockStateMeta.getBlockState();
                if (blockState instanceof Sign) {
                    Sign sign = (Sign) blockState;
                    sign.setLine(1, "右クリックで役職記録");
                    sign.update();
                    blockStateMeta.setBlockState(sign);
                    signStack.setItemMeta(blockStateMeta);
                }
            }
            player.getInventory().addItem(signStack);
        }
        return true;
    }
}
