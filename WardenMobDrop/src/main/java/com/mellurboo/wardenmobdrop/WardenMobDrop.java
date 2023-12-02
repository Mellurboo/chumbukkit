package com.mellurboo.wardenmobdrop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class WardenMobDrop extends JavaPlugin implements Listener {

    public Material[] wardenLootPool = {
            Material.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.NETHERITE_SCRAP,
            Material.ENCHANTED_GOLDEN_APPLE,
            Material.BAKED_POTATO,
            Material.DIAMOND_LEGGINGS,
            Material.AMETHYST_SHARD,
            Material.NAME_TAG,
            Material.RAW_GOLD,
            Material.GOLDEN_HELMET,
            Material.COAL_BLOCK
    };

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this,this);
        Bukkit.getLogger().info("WardenLootTable Ready");
    }

    @EventHandler
    public void WardenDeath(EntityDeathEvent event) {
        if(event.getEntity().getName().equals("Warden")) {
            Bukkit.broadcastMessage("§d[§cWARDEN§d]§c It is Slain");

            int warden_drop_value = (wardenLootPool.length - (wardenLootPool.length + 1)) + (int)(Math.random() * wardenLootPool.length);
            int warden_drop_ammount = (1 + (int)(Math.random() * 5));
            event.getDrops().clear();
            event.setDroppedExp(event.getDroppedExp() * (int)3f);
            event.getDrops().add(new ItemStack(wardenLootPool[warden_drop_value], warden_drop_ammount));
        }
    }

    @EventHandler
    public void onWardenSpawn(CreatureSpawnEvent event){
        if(event.getEntity().getName().equals("Warden")){
            Bukkit.broadcastMessage("§d[§cWARDEN§d]§c The air runs cold");
        }
    }
}
