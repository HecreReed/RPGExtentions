package com.main.RPGExtentions.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RopenCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("本命令仅允许玩家使用");
            return true;
        }else{
            Player p = (Player) commandSender;
            Inventory inv = Bukkit.createInventory(p,4*9,"冰狼存储箱");
            ItemStack it = new ItemStack(Material.BARRIER);
            inv.setItem(0,it);
            p.openInventory(inv);
            return true;
        }
    }
}
