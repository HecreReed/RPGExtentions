package com.main.RPGExtentions.listeners;

import com.main.RPGExtentions.RPGExtentions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class ImproveCloseEvent implements Listener {
    private RPGExtentions instance = RPGExtentions.rpge;
    @EventHandler
    public void onClose(InventoryCloseEvent e){
        Inventory inv = e.getInventory();
        Player p = (Player) e.getPlayer();
        if(e.getView().getTitle().equalsIgnoreCase("§a强化界面")){
            if(!inv.getItem(11).equals(instance.weapon)){
                p.getInventory().addItem(inv.getItem(11));
            }
            if(!inv.getItem(15).equals(instance.up)){
                p.getInventory().addItem(inv.getItem(15));
            }
            for(int i =21;i<=23;i++){
                if(!inv.getItem(i).equals(instance.help)){
                    p.getInventory().addItem(inv.getItem(i));
                }
            }
        }
    }
}
