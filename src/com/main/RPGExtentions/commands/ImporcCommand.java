package com.main.RPGExtentions.commands;

import com.main.RPGExtentions.RPGExtentions;
import com.main.RPGExtentions.listeners.ImproveClickEvent;
import com.main.RPGExtentions.values.ImproveValue;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


public class ImporcCommand implements CommandExecutor {
    private RPGExtentions instance = RPGExtentions.rpge;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("本命令仅允许玩家使用");
            return true;
        } else {
            Player p = (Player) commandSender;
            if (strings.length == 3) {
                if (strings[0].equalsIgnoreCase("give")) {
                    if (p.hasPermission("imporc.give")) {
                        if (instance.getServer().getPlayer(strings[1])!=null) {
                            Player target = instance.getServer().getPlayer(strings[1]);
                            if (strings[2].equals("0")) {
                                target.getInventory().addItem(instance.lowup);
                            } else if (strings[2].equals("1")) {
                                target.getInventory().addItem(instance.midup);
                            } else if (strings[2].equals("2")) {
                                target.getInventory().addItem(instance.highup);
                            } else if (strings[2].equals("3")) {
                                target.getInventory().addItem(instance.lowkeep);
                            } else if (strings[2].equals("4")) {
                                target.getInventory().addItem(instance.highkeep);
                            }
                        }
                    } else {
                        p.sendMessage("§b§l您没有权限这样做哦");
                    }
                }
                return true;
            } else if (strings.length == 2) {
                if (strings[0].equalsIgnoreCase("get")) {
                    if (p.hasPermission("imporc.get")) {
                        if (strings[1].equals("0")) {
                            p.getInventory().addItem(instance.lowup);
                        } else if (strings[1].equals("1")) {
                            p.getInventory().addItem(instance.midup);
                        } else if (strings[1].equals("2")) {
                            p.getInventory().addItem(instance.highup);
                        } else if (strings[1].equals("3")) {
                            p.getInventory().addItem(instance.lowkeep);
                        } else if (strings[1].equals("4")) {
                            p.getInventory().addItem(instance.highkeep);
                        }
                    } else {
                        p.sendMessage("§b§l您没有权限这样做哦");
                    }
                }
                return true;
            } else if (strings.length == 1) {
                if (strings[0].equalsIgnoreCase("reload")) {
                    if(p.hasPermission("imporc.reload")) {
                        instance.reloadConfig();
                        instance.onLoadConfig();
                        instance.config = instance.getConfig();
                        p.sendMessage("§b§l重载完成！");
                    }else p.sendMessage("§b§l您没有权限这样做哦");
                } else if (strings[0].equalsIgnoreCase("help")) {
                    p.sendMessage("§b§l---------------§a§l[§e§l强化の菜单§a§l]§b§l-----------------");
                    p.sendMessage("§b§l/imporc               §e§l打开强化窗口");
                    p.sendMessage("§b§l/imporc help          §e§l查看帮助指令");
                    p.sendMessage("§b§l/imporc reload        §e§l重载配置文件");
                    p.sendMessage("§b§l/imporc get id        §e§l获得强化石或幸运符(id为0-4，0,1,2为强化石，3,4为幸运符)");
                    p.sendMessage("§b§l/imporc give name id  §e§l给name的玩家id物品，同上");
                }
                return true;
            } else {
                if(p.hasPermission("imporc.im")) {
                    ImproveValue iv = new ImproveValue();
                    iv.enter = instance.enter;
                    instance.map.put(p, iv);
                    Inventory inv = Bukkit.createInventory(p, 4 * 9, "§a强化界面");
                    for (int i = 0; i < 36; i++) {
                        if (i == 11 || i == 15 || i == 22 || i == 21 || i == 23 || i == 31) continue;
                        inv.setItem(i, instance.it);
                    }
                    inv.setItem(11, instance.weapon);
                    inv.setItem(15, instance.up);
                    inv.setItem(21, instance.help);
                    inv.setItem(22, instance.help);
                    inv.setItem(23, instance.help);
                    inv.setItem(31, iv.enter);
                    p.openInventory(inv);
                    return true;
                }else{
                    p.sendMessage("§b§l您没有权限这样做哦");
                    return true;
                }
            }
        }
    }
}
