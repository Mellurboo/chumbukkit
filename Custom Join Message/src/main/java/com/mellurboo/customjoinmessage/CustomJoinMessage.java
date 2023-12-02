package com.mellurboo.customjoinmessage;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomJoinMessage extends JavaPlugin implements Listener {

    public String prefix = "[CustomJoinMessage] ";
    public String[] playerJoinMessages = {
            "§ewhat a shame",
            "§cbe careful, the new one bites,",
            "§cLook out for the one from birmingham,",
            "§ebring class A drugs only!!!!",
            "§eLook out,",
            "§dOnly you can prevent v-bucks scams,",
            "§aTHATS WHAT THE MASK ISSSSDDAS,",
            "§afaking speedruns is not allowed!",
            "§eAll thigh pics must go through izzy first,",
            "§ayou want to play lets play,",
            "§dWindows fanboy",
            "§eTopG",
            "§cWatch your wallets!",
            "§dSilver instalock reyna main",
            "§cyou feel a chill go down your spine,",
            "gravity feels 3x stronger"

    };

    public String[] playerQuitMessages = {
            "§eFinally",
            "§dWhat a pussy",
            "§eCombat log?",
            "§dfreelo",
            "§2this is a nice color. anyway",
            "§3Peace control",
            "§eGGEZ",
            "§3that one person who thinks theyre part of the friend group",
            "§aSkibbidi toilet 69420",
            "§e(Willy watcher 9000)",
            "§eI think",
            "§eAnd keep the change you filthy animal"
    };

    public String[] playerDeathMessages = {
            "§fLMFAO",
            "§cGGEZ",
            "§fUtterly Shameful",
            "§eKeep inventory is not enabled buddy",
            "§cFF",
            "§fOpen your eyes kid",
            "§fin what world does",
            "§fthats peak...",
            "§c'is it hardcore?'",
            "§fkaput,",
            "§elag ->"

    };


    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info(prefix + "Ready");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(prefix + "Shutdown Initiated");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        String playerName = event.getPlayer().getName();

        int randomSentence = (int) (Math.random() * playerJoinMessages.length - 1);
        Bukkit.broadcastMessage(playerJoinMessages[randomSentence] + " " + playerName + " has joined the Server");
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        event.setQuitMessage(null);
        String playerName = event.getPlayer().getName();

        int randomSentence = (int) (Math.random() * playerQuitMessages.length - 1);
        Bukkit.broadcastMessage(playerQuitMessages[randomSentence] + " " + playerName + " has left the Server");
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