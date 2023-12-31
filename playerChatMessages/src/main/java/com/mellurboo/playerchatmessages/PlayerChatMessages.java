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
    public String ownerJoin;
    public String adminJoin;

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

        Bukkit.getLogger().info("Player join messages " + playerJoinMessages.length);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(prefix + "Shutdown Initiated");
    }

    private void loadConfig() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        ownerJoin = config.getString("OwnerJoinMsg", "§x§A§D§7§1§F§FS§x§A§D§7§0§F§Fe§x§A§D§6§F§F§Er§x§A§C§6§E§F§Ev§x§A§C§6§D§F§Ee§x§A§C§6§C§F§Er §x§A§C§6§B§F§DO§x§A§C§6§A§F§Dw§x§A§B§6§9§F§Dn§x§A§B§6§8§F§Ce§x§A§B§6§7§F§Cr ");
        adminJoin = config.getString("adminJoinMsg", "§x§F§F§7§1§7§1A§x§F§F§6§D§6§Dd§x§F§F§6§9§6§9m§x§F§F§6§5§6§5i§x§F§F§6§1§6§1n ");

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
        String playerNameString = " §l" + event.getPlayer().getName();

        if (player.hasPermission("tab.group.owner")){
            event.setJoinMessage(ownerJoin + playerNameString + " §x§C§7§6§B§F§Fh§x§C§9§6§D§F§Fa§x§C§B§7§0§F§Fs §x§C§E§7§2§F§Fj§x§D§0§7§5§F§Fo§x§D§2§7§7§F§Fi§x§D§4§7§9§F§Fn§x§D§6§7§C§F§Fe§x§D§8§7§E§F§Fd §x§D§B§8§1§F§Ft§x§D§D§8§3§F§Fh§x§D§F§8§6§F§Fe §x§E§1§8§8§F§Fs§x§E§3§8§A§F§Fe§x§E§5§8§D§F§Fr§x§E§8§8§F§F§Fv§x§E§A§9§2§F§Fe§x§E§C§9§4§F§Fr");
        }else if (player.hasPermission("tab.group.admin")) {
            event.setJoinMessage(adminJoin + playerNameString + " §x§F§F§7§1§7§1h§x§F§F§7§0§7§0a§x§F§F§6§F§6§Fs §x§F§F§6§E§6§Ej§x§F§F§6§D§6§Do§x§F§F§6§C§6§Ci§x§F§F§6§B§6§Bn§x§F§F§6§A§6§Ae§x§F§F§6§9§6§9d §x§F§F§6§9§6§9t§x§F§F§6§8§6§8h§x§F§F§6§7§6§7e §x§F§F§6§6§6§6s§x§F§F§6§5§6§5e§x§F§F§6§4§6§4r§x§F§F§6§3§6§3v§x§F§F§6§2§6§2e§x§F§F§6§1§6§1r");
        }else if (!(player.hasPermission("mvpperks.ismvp"))){
            event.setJoinMessage("§a" + playerJoinMessages[(int) (Math.random() * playerJoinMessages.length)] + playerNameString + "§r§a has joined the Server");
        }else {
            event.setJoinMessage(MVPplayerJoinMessages[(int) (Math.random() * MVPplayerJoinMessages.length)] + " §l[MVP] " + event.getPlayer().getName() + " §r§x§F§F§C§B§6§Bh§x§F§E§C§9§6§Aa§x§F§E§C§6§6§8s §x§F§D§C§4§6§7j§x§F§C§C§1§6§6o§x§F§B§B§F§6§5i§x§F§B§B§D§6§3n§x§F§A§B§A§6§2e§x§F§9§B§8§6§1d §x§F§9§B§5§5§Ft§x§F§8§B§3§5§Eh§x§F§7§B§0§5§De §x§F§7§A§E§5§Bs§x§F§6§A§C§5§Ae§x§F§5§A§9§5§9r§x§F§4§A§7§5§8v§x§F§4§A§4§5§6e§x§F§3§A§2§5§5r");
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
        Player player = event.getPlayer();
        if (player != null){
            event.setDeathMessage(null);

            int randomSentence = (int) (Math.random() * playerDeathMessages.length);
            if (!(player.hasPermission("MVPPerks.isMVP"))){
                Bukkit.broadcastMessage(playerDeathMessages[randomSentence] + " (" + deathCause + ")");
            }else {
                Bukkit.broadcastMessage(MVPplayerDeathMessages[randomSentence] + " (" + deathCause + ")");
            }
        }
    }

}
