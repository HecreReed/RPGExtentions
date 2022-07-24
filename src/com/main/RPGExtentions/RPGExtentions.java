package com.main.RPGExtentions;

import com.main.RPGExtentions.commands.ImporcCommand;
import com.main.RPGExtentions.commands.RopenCommand;
import com.main.RPGExtentions.listeners.ImproveClickEvent;
import com.main.RPGExtentions.listeners.ImproveCloseEvent;
import com.main.RPGExtentions.listeners.OperatorClickEvent;
import com.main.RPGExtentions.values.ImproveValue;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RPGExtentions extends JavaPlugin {
    public static RPGExtentions rpge;
    public FileConfiguration config;

    public RPGExtentions() {
        rpge = this;
    }

    public ItemStack it;
    public ItemStack weapon;
    public ItemStack up;
    public ItemStack help;
    public ItemStack enter;
    public ItemStack lowup;
    public ItemStack midup;
    public ItemStack highup;
    public ItemStack highkeep;
    public ItemStack lowkeep;
    public double point;
    public int highchance;
    public int lowchance;
    public List<String> attribute = new ArrayList<String>();
    public List<Material> materials = new ArrayList<>();
    public List<String> materist = new ArrayList<>();
    public Map<Player, ImproveValue> map = new HashMap<>();
    private void onLoadInventory() {
        it = new ItemStack(Material.BARRIER);
        ItemMeta im = it.getItemMeta();
        im.setDisplayName(" ");
        it.setItemMeta(im);
        weapon = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta im2 = weapon.getItemMeta();
        im2.setDisplayName("§a§l此处放入武器");
        weapon.setItemMeta(im2);
        up = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta im3 = up.getItemMeta();
        im3.setDisplayName("§4§l此处放入强化石");
        up.setItemMeta(im3);
        help = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta im4 = help.getItemMeta();
        im4.setDisplayName("§e§l此处放入保护符");
        help.setItemMeta(im4);
        enter = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta im5 = enter.getItemMeta();
        im5.setDisplayName("§c§l点击强化");
        enter.setItemMeta(im5);
    }

    private void onLoadMaterials() {
        materials.clear();
        for (String s : materist) {
            materials.add(Material.valueOf(s.toUpperCase()));
        }
    }

    public void onLoadConfig() {
        attribute = config.getStringList("needimprove");
        materist = config.getStringList("canimprove");
        String highname = config.getString("highstonename.name", "§c§l高级强化石");
        String highid = config.getString("highstonename.id", "quartz").toUpperCase();
        List<String> highlore = config.getStringList("highstonename.lore");
        highup = new ItemStack(Material.valueOf(highid));
        ItemMeta highmeta = highup.getItemMeta();
        highmeta.setDisplayName(highname);
        highmeta.setLore(highlore);
        highup.setItemMeta(highmeta);
        String midname = config.getString("middlestonename.name", "§b§l中级强化石");
        String midid = config.getString("middlestonename.id", "amethyst_shard").toUpperCase();
        List<String> midlore = config.getStringList("middlestonename.lore");
        midup = new ItemStack(Material.valueOf(midid));
        ItemMeta midmeta = midup.getItemMeta();
        midmeta.setDisplayName(midname);
        midmeta.setLore(midlore);
        midup.setItemMeta(midmeta);
        String lowname = config.getString("lowstonename.name", "§a§l低级强化石");
        String lowid = config.getString("lowstonename.id", "coal").toUpperCase();
        List<String> lowlore = config.getStringList("lowstonename.lore");
        lowup = new ItemStack(Material.valueOf(lowid));
        ItemMeta lowmeta = lowup.getItemMeta();
        lowmeta.setDisplayName(lowname);
        lowmeta.setLore(lowlore);
        lowup.setItemMeta(lowmeta);
        String lcname = config.getString("lowkeep.name", "§a§l一级幸运草");
        String lcid = config.getString("lowkeep.id", "dandelion").toUpperCase();
        List<String> lclore = config.getStringList("lowkeep.lore");
        lowkeep = new ItemStack(Material.valueOf(lcid));
        ItemMeta lcmeta = lowkeep.getItemMeta();
        lcmeta.setDisplayName(lcname);
        lcmeta.setLore(lclore);
        lowkeep.setItemMeta(lcmeta);
        String hname = config.getString("highkeep.name", "§b§l二级幸运草");
        String hid = config.getString("highkeep.id", "blue_orchid").toUpperCase();
        List<String> hlore = config.getStringList("highkeep.lore");
        highkeep = new ItemStack(Material.valueOf(hid));
        ItemMeta hmeta = highkeep.getItemMeta();
        hmeta.setDisplayName(hname);
        hmeta.setLore(hlore);
        highkeep.setItemMeta(hmeta);
        lowchance = config.getInt("lowkeep.chance");
        highchance = config.getInt("highkeep.chance");
        point = config.getDouble("point");
    }
    @Override
    public void onLoad(){
        saveDefaultConfig();
    }
    @Override
    public void onEnable() {
        config = getConfig();
        onLoadInventory();
        onLoadConfig();
        onLoadMaterials();
        Bukkit.getPluginManager().registerEvents(new OperatorClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ImproveClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ImproveCloseEvent(), this);
        Bukkit.getPluginCommand("invopen").setExecutor(new RopenCommand());
        Bukkit.getPluginCommand("imporc").setExecutor(new ImporcCommand());
        getLogger().info("§cRPG拓展之Eroslon_dusk加载完成");
    }

    @Override
    public void onDisable() {
        saveConfig();
        getLogger().info("§cRPG拓展之Eroslon_dusk卸载完成");
    }
}
