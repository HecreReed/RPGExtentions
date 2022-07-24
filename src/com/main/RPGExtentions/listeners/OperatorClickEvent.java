package com.main.RPGExtentions.listeners;

import com.main.RPGExtentions.RPGExtentions;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public class OperatorClickEvent implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e){
        boolean istestmode = RPGExtentions.rpge.config.getBoolean("op-mode");
        List<String> playersname = RPGExtentions.rpge.config.getStringList("test-name");
        HumanEntity p = e.getWhoClicked();
        String playername = p.getName();
        if(istestmode){
            if(playersname.contains(playername)){
                int i = e.getSlot();
                p.sendMessage("§a点击的格子为:"+String.valueOf(i));
                p.sendMessage("§a容器名为:"+e.getView().getTitle());
            }
        }
    }
}
