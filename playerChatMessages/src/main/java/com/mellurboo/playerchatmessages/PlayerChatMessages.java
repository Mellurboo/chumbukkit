package com.mellurboo.playerchatmessages;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerChatMessages extends JavaPlugin implements Listener {

    public String prefix = "[CustomJoinMessage] ";
    public String[] playerJoinMessages;
    public String[] playerQuitMessages;
    public String[] playerDeathMessages;

    public String[] MVPplayerJoinMessages;
    public String[] MVPplayerQuitMessages;
    public String[] MVPplayerDeathMessages;



    @Override
    public void onEnable() {
        loadConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info(prefix + "Ready");
        getCommand("playerChatMessages.reload").setExecutor(this);
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
        MVPplayerJoinMessages = config.getStringList("MVPplayerJoinMessages").stream()
                .toArray(String[]::new);

        playerQuitMessages = config.getStringList("playerQuitMessages").stream()
                .toArray(String[]::new);
        MVPplayerQuitMessages = config.getStringList("playerQuitMessages").stream()
                .toArray(String[]::new);

        playerDeathMessages = config.getStringList("playerDeathMessages").stream()
                .toArray(String[]::new);
        MVPplayerQuitMessages = config.getStringList("MVPplayerDeathMessages").stream()
                .toArray(String[]::new);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player  = event.getPlayer();
        if (!(player.hasPermission("MVPPerks.isMVP"))){
            event.setJoinMessage("§a" + playerJoinMessages[(int) (Math.random() * playerJoinMessages.length)] + " " + event.getPlayer().getName() + " has joined the Server");
        }else {
            event.setJoinMessage(MVPplayerJoinMessages[(int) (Math.random() * MVPplayerJoinMessages.length)] + " [MVP] " + event.getPlayer().getName() + " has joined the Server");
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player  = event.getPlayer();
        if (!(player.hasPermission("MVPPerks.isMVP"))) {
            event.setQuitMessage("§c" + playerQuitMessages[(int) (Math.random() * playerQuitMessages.length)] + " " + event.getPlayer().getName() + " has left the Server");
        }else {
            event.setQuitMessage(MVPplayerQuitMessages[(int) (Math.random() * MVPplayerQuitMessages.length)] + " [MVP] " + event.getPlayer().getName() + " has left the Server");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("playerChatMessages.reload")) {
            if (sender.isOp()) {
                reloadConfig();
                loadConfig(); // Reload your custom configuration loading logic
                sender.sendMessage("§CConfig reloaded.");
                return true;
            } else {
                sender.sendMessage("§cYou don't have permission to use this command.");
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        String deathCause = event.getDeathMessage();
        Player player = (Player) event;
        event.setDeathMessage(null);

        int randomSentence = (int) (Math.random() * playerDeathMessages.length);
        if (!(player.hasPermission("MVPPerks.isMVP"))){
            Bukkit.broadcastMessage(playerDeathMessages[randomSentence] + " (" + deathCause + ")");
        }else {
            Bukkit.broadcastMessage(MVPplayerDeathMessages[randomSentence] + " (" + deathCause + ")");
        }
    }

}
