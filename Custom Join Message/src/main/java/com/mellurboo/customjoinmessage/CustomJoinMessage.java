package com.mellurboo.customjoinmessage;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomJoinMessage extends JavaPlugin implements Listener {

    public String prefix = "[CustomJoinMessage] ";
    public String[] playerJoinMessages;
    public String[] playerQuitMessages;

//    public String[] playerDeathMessages = {
//            "§fLMFAO",
//            "§cGGEZ",
//            "§fUtterly Shameful",
//            "§eKeep inventory is not enabled buddy",
//            "§cFF",
//            "§fOpen your eyes kid",
//            "§fin what world does",
//            "§fthats peak...",
//            "§c'is it hardcore?'",
//            "§fkaput,",
//            "§elag ->"
//
//    };


    @Override
    public void onEnable() {
        loadConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info(prefix + "Ready");
        getCommand("reloadCustomJoinMessage").setExecutor(this);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(prefix + "Shutdown Initiated");
    }

    private void loadConfig() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        //Load Join Messages from config.yml
        playerJoinMessages = config.getStringList("playerJoinMessages").stream()
                .toArray(String[]::new);
        playerQuitMessages = config.getStringList("playerQuitMessages").stream()
                .toArray(String[]::new);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(playerJoinMessages[(int) (Math.random() * playerJoinMessages.length - 1)] + " " + event.getPlayer().getName() + " has joined the Server");
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        event.setQuitMessage(playerQuitMessages[(int) (Math.random() * playerQuitMessages.length - 1)] + " " + event.getPlayer().getName() + " has left the Server");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("reloadCustomJoinMessage")) {
            if (sender.hasPermission("CustomJoinMessage.reloadconfig")) {
                reloadConfig();
                loadConfig(); // Reload your custom configuration loading logic
                sender.sendMessage("§aCustom Join Message config reloaded.");
                return true;
            } else {
                sender.sendMessage("§cYou don't have permission to use this command.");
                return true;
            }
        }
        return false;
    }
/*
    @EventHandler
    public void onPlayerDie(PlayerDeathEvent event){
        String deathCause = event.deathMessage().toString();
        event.setDeathMessage(null);
        String playerName = event.getPlayer().getName();

        int randomSentence = (int) (Math.random() * playerDeathMessages.length - 1);
        Bukkit.broadcastMessage(playerDeathMessages[randomSentence] + " " + playerName + deathCause);
    }

 */
}