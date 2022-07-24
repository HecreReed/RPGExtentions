package com.main.RPGExtentions.values;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ImproveValue {
    private int level = 0;
    private String name = " ";
    private int chance = 0;
    public ItemStack enter;
    public List<String> enterlore = new ArrayList<>();
    public int getLevel(){
        return this.level;
    }
    public String getName(){
        return this.name;
    }
    public int getChance(){
        return this.chance;
    }
    public void setLevel(int level){
        this.level = level;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setChance(int chance){
        this.chance = chance;
    }
}
