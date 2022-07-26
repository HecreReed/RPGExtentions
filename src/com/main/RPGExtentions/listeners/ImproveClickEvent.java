package com.main.RPGExtentions.listeners;

import com.main.RPGExtentions.RPGExtentions;
import com.main.RPGExtentions.values.ImproveValue;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

public class ImproveClickEvent implements Listener {
    private RPGExtentions instance = RPGExtentions.rpge;

    private void cal(Inventory inv, ItemStack item, ItemStack up, ImproveValue imv) {
        if (up == null) return;
        ItemMeta im = item.getItemMeta();
        String name = im.getDisplayName();
        int level = 0;
        int chance = 100;
        if (name.contains("+")) {
            String[] s = name.split("\\+");
            name = s[0];
            level = Integer.parseInt(s[1]);
        }
        imv.setLevel(level);
        imv.setName(name);
        imv.enterlore.clear();
        if (level <= 2) {
            imv.setChance(chance);
            String cha = "§e§l成功概率为" + chance + "%";
            imv.enterlore.add(cha);
            ItemMeta ims = imv.enter.getItemMeta();
            ims.setLore(imv.enterlore);
            imv.enter.setItemMeta(ims);
        } else {
            chance = 0;
            if (up.equals(instance.highup)) {
                chance = (int)(800 * (1 / (double) (level+5))+10);
            } else if (up.equals(instance.midup)) {
                chance = (int)(725 * (1 / (double) (level+5))+8);
            } else if (up.equals(instance.lowup)) {
                chance = (int)(600 * (1 / (double) (level+5))+4);
            }
            for (int i = 21; i <= 23; i++) {
                ItemStack items = inv.getItem(i);
                if (items.equals(instance.help)) continue;
                else {
                    if (items.equals(instance.highkeep)) {
                        chance += instance.highchance;
                    } else if (items.equals(instance.lowkeep)) {
                        chance += instance.lowchance;
                    }
                }
            }
            if (chance > 100) chance = 100;
            imv.setChance(chance);
            String cha = "§e§l成功概率为" + chance + "%";
            imv.enterlore.add(cha);
            ItemMeta ims = imv.enter.getItemMeta();
            ims.setLore(imv.enterlore);
            imv.enter.setItemMeta(ims);
        }
        inv.setItem(31, imv.enter);
    }

    private void levelUP(Inventory inv, Player p, ImproveValue imv) {
        int chance = imv.getChance();
        ItemStack weapon = inv.getItem(11);
        ItemStack upstone = inv.getItem(15);
        ItemMeta im = weapon.getItemMeta();
        String name = imv.getName();
        int level = imv.getLevel();
        if (Math.random() <= (double) chance / 100) {
            List<String> weaponlore = Objects.requireNonNull(im).getLore();
            if(weaponlore==null) return;
            int count = 0;
            for (int i = 0; i < weaponlore.size(); i++) {
                for (String att : instance.attribute) {
                    String lore = weaponlore.get(i);
                    if (lore.contains(att)) {
                        count++;
                        String[] s = lore.split(":");
                        String form = s[0];
                        s[1] = s[1].replaceAll(" ","");
                        String num = s[1];
                        boolean hasper = false;
                        if (num.contains("%")) {
                            hasper = true;
                            num = num.replaceAll("%", "");
                        }
                        if (num.contains("-")) {
                            s = num.split("-");
                            String color = s[0].substring(0, 2);
                            s[0] = s[0].substring(2);
                            double n1 = Double.parseDouble(s[0]);
                            double n2 = Double.parseDouble(s[1]);
                            if(!att.equalsIgnoreCase("物理防御")) {
                                n1 += instance.point;
                                n2 += instance.point;
                            }else{
                                n1 += instance.point/10;
                                n2 += instance.point/10;
                            }
                            String newlore = form + ": " + color + n1 + "-" + n2;
                            if (hasper) newlore = form + ": " + color + n1 + "%-" + n2 + "%";
                            weaponlore.set(i, newlore);
                        } else {
                            String color = num.substring(0, 2);
                            num = num.substring(2);
                            double n = Double.parseDouble(num);
                            if(!att.equalsIgnoreCase("物理防御")) {
                                n += instance.point;
                            }else n += instance.point/10;
                            String newlore = form + ": "+color + n;
                            if (hasper) newlore = form + ": " +color+ n + "%";
                            weaponlore.set(i, newlore);
                        }
                    }
                }
            }
            if (count == 0) {
                p.sendMessage("§e§l无法强化");
            } else {
                level += 1;
                im.setDisplayName(name + "+" + level);
                im.setLore(weaponlore);
                weapon.setItemMeta(im);
                inv.setItem(11, weapon);
                inv.setItem(15, instance.up);
                for (int i = 21; i <= 23; i++) {
                    inv.setItem(i,instance.help);
                }
                p.sendMessage("§e§l强化成功了捏");
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 0);
            }
        } else {
            inv.setItem(15, instance.up);
            for (int i = 21; i <= 23; i++) {
                inv.setItem(i,instance.help);
            }
            if (level <= 4) {
                p.sendMessage("§e§l呜呜~强化失败了，但没有掉级");
            } else {
                level -= 1;
                im.setDisplayName(name + "+" + level);
                List<String> weaponlore = Objects.requireNonNull(im).getLore();
                for (int i = 0; i < weaponlore.size(); i++) {
                    for (String att : instance.attribute) {
                        String lore = weaponlore.get(i);
                        if (lore.contains(att)) {
                            String[] s = lore.split(":");
                            String form = s[0];
                            s[1] = s[1].replaceAll(" ","");
                            String num = s[1];
                            boolean hasper = false;
                            if (num.contains("%")) {
                                hasper = true;
                                num = num.replaceAll("%", "");
                            }
                            if (num.contains("-")) {
                                s = num.split("-");
                                String color = s[0].substring(0, 2);
                                s[0] = s[0].substring(2);
                                double n1 = Double.parseDouble(s[0]);
                                double n2 = Double.parseDouble(s[1]);
                                n1 -= instance.point;
                                n2 -= instance.point;
                                String newlore = form + ": " + color + n1 + "-" + n2;
                                if (hasper) newlore = form + ": " + color + n1 + "%-" + n2 + "%";
                                weaponlore.set(i, newlore);
                            } else {
                                String color = num.substring(0, 2);
                                num = num.substring(2);
                                double n = Double.parseDouble(num);
                                n -= instance.point;
                                String newlore = form + ": "+color + n;
                                if (hasper) newlore = form + ": " +color+ n + "%";
                                weaponlore.set(i, newlore);
                            }
                        }
                    }
                }
                im.setLore(weaponlore);
                weapon.setItemMeta(im);
                inv.setItem(11, weapon);
                p.sendMessage("§e§l呜呜~强化失败了，还掉级咯");
            }
            p.playSound(p.getLocation(),Sound.ENTITY_GENERIC_EXPLODE,1,0);
        } //强化失败
        imv.setChance(0);
        imv.enterlore.clear();
        ItemMeta ime = imv.enter.getItemMeta();
        ime.setLore(imv.enterlore);
        imv.enter.setItemMeta(ime);
        inv.setItem(31,imv.enter);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        int id = e.getSlot();
        Inventory inv = e.getInventory();
        Inventory clinv = e.getClickedInventory();
        if (e.getView().getTitle().equalsIgnoreCase("§a强化界面")) {
            ItemStack cl = e.getCurrentItem();
            ItemStack cur = e.getCursor();
            Player p = (Player) e.getWhoClicked();
            ImproveValue imv = instance.map.get(p);
            if(imv==null){
                imv = new ImproveValue();
                imv.enter = instance.enter;
                instance.map.put(p,imv);
            }
            if (cl != null) {
                if (inv.equals(clinv)) {
                    if (id == 11) {
                        if (Objects.requireNonNull(inv.getItem(11)).equals(instance.weapon)) {
                            if (cur != null) {
                                if (!instance.materials.contains(cur.getType())) {
                                    p.sendMessage("§e§l请放入正确的装备");
                                    e.setCancelled(true);
                                } else {
                                    inv.setItem(11, new ItemStack(Material.AIR));
                                }
                            } else {
                                p.sendMessage("§e§l请放入正确的装备");
                                e.setCancelled(true);
                            }
                        } else {
                            if (Objects.requireNonNull(cur).getType().isAir()) {
                                ItemStack it = inv.getItem(11);
                                p.getInventory().addItem(it);
                                inv.setItem(11, instance.weapon);
                                e.setCancelled(true);
                            } else if (!instance.materials.contains(cur.getType())) {
                                p.sendMessage("§e§l请放入正确的装备");
                                e.setCancelled(true);
                            }
                        }
                    } else if (id == 15) {
                        if (Objects.requireNonNull(inv.getItem(id)).equals(instance.up)) {
                            if (cur != null) {
                                int amount = cur.getAmount();
                                if (!((cur.equals(instance.highup)) || (cur.equals(instance.midup)) || (cur.equals(instance.lowup)))) {
                                    p.sendMessage("§e§l请放入正确的强化石");
                                    e.setCancelled(true);
                                } else {
                                    if (Objects.requireNonNull(inv.getItem(11)).equals(instance.weapon)) {
                                        p.sendMessage("§e§l请先放入武器");
                                        e.setCancelled(true);
                                    } else {
                                        cur.setAmount(1);
                                        e.setCancelled(true);
                                        inv.setItem(15, cur);
                                        if (amount > 1) {
                                            cur.setAmount(amount - 1);
                                            p.setItemOnCursor(cur);
                                        } else {
                                            p.setItemOnCursor(new ItemStack(Material.AIR));
                                        }
                                        cal(inv, inv.getItem(11), Objects.requireNonNull(inv.getItem(15)), imv);
                                    }
                                }
                            } else {
                                p.sendMessage("§e§l请放入正确的强化石");
                                e.setCancelled(true);
                            }
                        } else {
                            if (Objects.requireNonNull(cur).getType().isAir()) {
                                ItemStack it = inv.getItem(id);
                                p.getInventory().addItem(it);
                                inv.setItem(id, instance.up);
                                e.setCancelled(true);
                            } else if (!((cur.equals(instance.highup)) || (cur.equals(instance.midup)) || (cur.equals(instance.lowup)))) {
                                p.sendMessage("§e§l请放入正确的强化石");
                                e.setCancelled(true);
                            }
                        }
                    } else if (id == 21 || id == 22 || id == 23) {
                        if (Objects.requireNonNull(inv.getItem(id)).equals(instance.help)) {
                            if (cur != null) {
                                int amount = cur.getAmount();
                                if (!(cur.equals(instance.highkeep) || cur.equals(instance.lowkeep))) {
                                    p.sendMessage("§e§l请放入正确的保护符");
                                    e.setCancelled(true);
                                } else {
                                    e.setCancelled(true);
                                    cur.setAmount(1);
                                    inv.setItem(id, cur);
                                    if (amount > 1) {
                                        cur.setAmount(amount - 1);
                                        p.setItemOnCursor(cur);
                                    } else {
                                        p.setItemOnCursor(new ItemStack(Material.AIR));
                                    }
                                    cal(inv,inv.getItem(11), inv.getItem(15), imv);
                                }
                            } else {
                                p.sendMessage("§e§l请放入正确的保护符");
                                e.setCancelled(true);
                            }
                        } else {
                            if (Objects.requireNonNull(cur).getType().isAir()) {
                                ItemStack it = inv.getItem(id);
                                p.getInventory().addItem(it);
                                inv.setItem(id, instance.help);
                                e.setCancelled(true);
                            } else if (!(cur.equals(instance.highkeep) || cur.equals(instance.lowkeep))) {
                                p.sendMessage("§e§l请放入正确的保护符");
                                e.setCancelled(true);
                            }
                        }
                    } else if (id == 31) {
                        if (Objects.requireNonNull(inv.getItem(11)).equals(instance.weapon) || Objects.requireNonNull(inv.getItem(15)).equals(instance.up)) {
                            p.sendMessage("§e§l请先放入武器或强化石");
                            e.setCancelled(true);
                        } else {
                            e.setCancelled(true);
                            levelUP(inv, p, imv);
                        }
                    } else if (cl.equals(instance.it)) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}

