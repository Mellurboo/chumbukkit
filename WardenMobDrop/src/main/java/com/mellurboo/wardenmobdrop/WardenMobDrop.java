package com.mellurboo.wardenmobdrop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class WardenMobDrop extends JavaPlugin implements Listener {

    private Material[] wardenLootPool;
    private String wardenSlainMessage;
    private String wardenSpawnMessage;
    private int wardenExpMultipler;

    @Override
    public void onEnable() {
        loadConfig();
        getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getLogger().info("§4[§cWARDEN TWEAKS§4]§c STARTED SUCCESSFULLY");
        Bukkit.getLogger().info(wardenLootPool[0].name());
        getCommand("wardendrops.reloadconfig").setExecutor(this);
    }

    private void loadConfig() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        // Load loot pool from config
        wardenLootPool = config.getStringList("wardenLootPool").stream()
                .map(Material::getMaterial)
                .toArray(Material[]::new);

        // Load messages from config
        wardenSlainMessage = config.getString("wardenSlainMessage", "§d[§cWARDEN§d]§c It is Slain");
        wardenSpawnMessage = config.getString("wardenSpawnMessage", "§d[§cWARDEN§d]§c The air runs cold");
        wardenExpMultipler = config.getInt("wardenExpMultipler", 4);
    }

    @EventHandler
    public void wardenDeath(EntityDeathEvent event) {
        if (isWarden(event.getEntity())) {
            Bukkit.broadcastMessage(wardenSlainMessage);

            int wardenDropValue = (wardenLootPool.length - (wardenLootPool.length + 1)) + (int) (Math.random() * wardenLootPool.length);
            int wardenDropAmount = (1 + (int) (Math.random() * 5));
            event.getDrops().clear();
            event.setDroppedExp(event.getDroppedExp() * wardenExpMultipler);
            event.getDrops().add(new ItemStack(wardenLootPool[wardenDropValue], wardenDropAmount));
            event.getDrops().add(new ItemStack(Material.TOTEM_OF_UNDYING, wardenDropAmount));
        }
    }

    @EventHandler
    public void onWardenSpawn(CreatureSpawnEvent event) {
        if (isWarden(event.getEntity())) {
            Bukkit.broadcastMessage(wardenSpawnMessage);
        }
    }

    private boolean isWarden(Entity entity) {
        return entity.getName().equals("Warden");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("wardendrops.reloadconfig")) {
            if (sender.hasPermission("wardendrops.reloadconfig")) {
                reloadConfig();
                loadConfig(); // Reload your custom configuration loading logic
                sender.sendMessage("§aWardenMobDrop config reloaded.");
                return true;
            } else {
                sender.sendMessage("§cYou don't have permission to use this command.");
                return true;
            }
        }
        return false;
    }
}