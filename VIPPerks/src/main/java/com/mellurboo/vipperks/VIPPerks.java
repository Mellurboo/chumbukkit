package com.mellurboo.vipperks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public final class VIPPerks extends JavaPlugin implements Listener {
    public int expMultipler;
    public int dropChance;
    public int expDroppedWhileFarming;

    public String vipbar;
    public String newVIPText;
    public String arrowsLeft;
    public String arrowsRight;


    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        expMultipler = config.getInt("expMultipler", 2);
        dropChance = config.getInt("expDropChance", 15);
        expDroppedWhileFarming = config.getInt("expDroppedWhileFarming", 1);

        vipbar = config.getString("newVIPBAR", "§l§m§x§F§F§B§0§7§9-§x§F§F§B§3§7§A-§x§F§F§B§7§7§B-§x§F§F§B§A§7§C-§x§F§F§B§D§7§D-§x§F§F§C§1§7§D-§x§F§F§C§4§7§E-§x§F§F§C§7§7§F-§x§F§F§C§B§8§0-§x§F§F§C§E§8§1-§x§F§F§D§1§8§2-§x§F§F§D§5§8§3-§x§F§F§D§8§8§4-§x§F§F§D§9§8§4-§x§F§F§D§8§8§5-§x§F§F§D§8§8§5-§x§F§F§D§8§8§5-§x§F§F§D§7§8§6-§x§F§F§D§7§8§6-§x§F§F§D§7§8§6-§x§F§F§D§7§8§6-§x§F§F§D§6§8§7-§x§F§F§D§6§8§7-§x§F§F§D§6§8§7-§x§F§F§D§5§8§8-§x§F§F§D§4§8§7-§x§F§F§D§3§8§5-§x§F§F§D§1§8§3-§x§F§F§D§0§8§1-§x§F§F§C§E§7§F-§x§F§F§C§C§7§D-§x§F§F§C§B§7§B-§x§F§F§C§9§7§9-§x§F§F§C§7§7§7-§x§F§F§C§6§7§5-§x§F§F§C§4§7§3-§x§F§F§C§3§7§1-§x§F§F§C§1§6§F-");
        newVIPText = config.getString("newVIP", "§x§F§F§9§4§4§AH§x§F§F§9§A§4§Ca§x§F§F§9§F§4§Ds §x§F§F§A§5§4§Fs§x§F§F§A§B§5§0u§x§F§F§B§0§5§2p§x§F§F§B§6§5§3p§x§F§F§B§C§5§5o§x§F§F§C§2§5§6r§x§F§F§C§7§5§8t§x§F§F§C§B§5§9e§x§F§F§C§A§5§9d §x§F§F§C§A§5§9t§x§F§F§C§9§5§Ah§x§F§F§C§8§5§Ae §x§F§F§C§8§5§As§x§F§F§C§7§5§Ae§x§F§F§C§6§5§Br§x§F§F§C§6§5§Bv§x§F§F§C§5§5§Be§x§F§D§C§2§5§8r §x§F§B§B§D§5§3w§x§F§8§B§8§4§Ei§x§F§6§B§4§4§At§x§F§3§A§F§4§5h §x§F§1§A§A§4§0[§x§E§E§A§5§3§BM§x§E§C§A§1§3§7V§x§E§9§9§C§3§2P§x§E§7§9§7§2§D]");
        arrowsLeft = config.getString("leftArrows", "§x§F§F§9§4§4§A<§x§F§F§C§8§5§A<§x§E§7§9§7§2§D<");
        arrowsRight = config.getString("rightArrows","§x§F§F§9§4§4§A>§x§F§F§C§8§5§A>§x§E§7§9§7§2§D> §n");



        Bukkit.getLogger().info("[]=======================================[]");
        Bukkit.getLogger().info("[VIPPERKS] started succesfully");
        Bukkit.getLogger().info("[]=======================================[]");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("CRITICAL [VIPPERKS] Has been dissabled.");
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        // this is not referencing players, this is about other entities. dont get them confused
        // I shouldn't be nesting if statements here but its to avoid annoying false error messages
        if (event.getEntity().getKiller() != null){
            Player killer = event.getEntity().getKiller();
            if (killer.hasPermission("VIPPerks.isVIP")){
                int finalExp = event.getDroppedExp() * expMultipler;
                killer.giveExp(finalExp);
            }
        }
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player Victim = event.getPlayer();

        // we have to check if there was a killer because players can die to many things
        if (event.getEntity().getKiller() != null){
            Player Killer = (Player) event.getPlayer().getKiller();
            if (Killer.hasPermission("VIPPerks.isVIP")){
                ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
                SkullMeta meta = (SkullMeta) head.getItemMeta();
                meta.setOwner(Victim.getName());
                head.setItemMeta(meta);

                event.getDrops().add(head);
                int finalExp = event.getDroppedExp() * expMultipler;
                event.getPlayer().getKiller().giveExp(finalExp);
            }
        }
    }
    @EventHandler
    public void onItemFarmed(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block.getData() == 7 && player.hasPermission("VIPPerks.isVIP")){
            if ((int) (Math.random() * 100) <= dropChance) {
                player.giveExp(expDroppedWhileFarming);
            }
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (label.equalsIgnoreCase("vipgrant")){
            if (args.length == 1){
                Bukkit.broadcastMessage(vipbar);
                Bukkit.broadcastMessage(arrowsRight + args[0] +"§r"+ newVIPText + " " + arrowsLeft);
                Bukkit.broadcastMessage(vipbar);
                return true;
            }else {
                Bukkit.getLogger().info("vipgrant only takes one parameter");
            }
        }else if (label.equalsIgnoreCase("vip.reload")) {
            reloadConfig();
            sender.sendMessage("§2VIP Config Reloaded!");
        }
        return false;
    }
}