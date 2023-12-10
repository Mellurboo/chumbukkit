package com.mellurboo.mvpperks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class MVPPerks extends JavaPlugin implements Listener {

    public int expMultipler;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        expMultipler = config.getInt("expMultipler", 2);

        Bukkit.getLogger().info("[]=======================================[]");
        Bukkit.getLogger().info("Exp Multipler = " + expMultipler);
        Bukkit.getLogger().info("[MVPPERKS] started succesfully");
        Bukkit.getLogger().info("[]=======================================[]");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("[MVPPERKS] Did not start :/ ");
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        // I shouldnt be nesting if statements here but its to avoid annoying false error messages
        if (event.getEntity().getKiller() != null){
            Player killer = event.getEntity().getKiller();
            if (killer.hasPermission("MVPPerks.isMVP")){
                int finalExp = event.getDroppedExp() * expMultipler;
                event.setDroppedExp(finalExp);
            }
        }
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player Victim = (Player) event.getPlayer();

        if (event.getEntity().getKiller() != null){
            Player Killer = (Player) event.getPlayer().getKiller();

            if (Killer.hasPermission("MVPPerks.isMVP")){
                Bukkit.broadcastMessage("Player: " + Killer + "Killed: " + Victim);
                ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
                SkullMeta meta = (SkullMeta) head.getItemMeta();
                meta.setOwner(Victim.getName());
                head.setItemMeta(meta);

                event.getDrops().add(head);
            }
        }
    }

}