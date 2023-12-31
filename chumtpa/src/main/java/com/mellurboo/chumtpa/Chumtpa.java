package com.mellurboo.chumtpa;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Chumtpa extends JavaPlugin implements Listener {
    private Map<UUID, UUID> teleportRequests = new HashMap<>();

    public String playerNotFound;
    public String recipientPrompt;
    public String senderPrompt;
    public String noRequests;
    public String requestAcceptedSender;
    public String senderAccepted;
    public String playerNotOnline;
    public String senderDeniedTPA;
    public String recipientDeniedTPA;
    public String notEnoughExp;
    public int expCost;

    @Override
    public void onEnable() {
        loadConfig();
        getCommand("tpa").setExecutor(this);
        getCommand("tpaccept").setExecutor(this);
        getCommand("tpdeny").setExecutor(this);
        getCommand("tpreload").setExecutor(this);
    }

    public void loadConfig(){
        FileConfiguration config = getConfig();
        playerNotFound = config.getString("PlayerNotFound", "§cPlayer Not Found or they're not online");
        recipientPrompt = config.getString("recipientPrompt", "§e Has requested to teleport to you, use /tpaccept or /tpdeny");
        senderPrompt = config.getString("senderPrompt", "§eTeleport request sent to ");
        noRequests = config.getString("noRequests", "§cYou have no pending request(s)");
        requestAcceptedSender = config.getString("requestAcceptedSender", "§e Has accepted your TP request");
        senderAccepted = config.getString("senderAccepted", "§e Teleport Request Accepted");
        playerNotOnline = config.getString("playerNotOnline", "§c the player who sent this request is no longer online!");
        senderDeniedTPA = config.getString("senderDeniedTPA", "§c Has denied your teleport request");
        recipientDeniedTPA = config.getString("recipientDeniedTPA", "§cTeleport request denied");
        notEnoughExp = config.getString("senderNotEnoughExp", "§cYou do not have enough exp to complete this request, cost: ");
        expCost = config.getInt("expCost",27);

    }


    private void teleportPlayer(Player sender, Player target) {
        sender.teleport(target.getLocation());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can use this command.");
            return true;
        }

        Player senderPlayer = (Player) sender;

        if (command.getName().equalsIgnoreCase("tpa")) {
            if (args.length != 1) {
                senderPlayer.sendMessage("§cUsage: /tpa <player>");
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null || !target.isOnline()) {
                senderPlayer.sendMessage(playerNotOnline);
                return true;
            }

            if (((Player) sender).getExp() >= expCost){
                senderPlayer.setExp(senderPlayer.getExp() - expCost);

                teleportRequests.put(target.getUniqueId(), senderPlayer.getUniqueId());
                target.sendMessage(senderPlayer.getName() + recipientPrompt);
                senderPlayer.sendMessage(senderPrompt + target.getName());
            }else {
                sender.sendMessage(notEnoughExp + expCost + " you have " + ((Player) sender).getExp());
            }

        } else if (command.getName().equalsIgnoreCase("tpaccept")) {
            if (!teleportRequests.containsKey(senderPlayer.getUniqueId())) {
                senderPlayer.sendMessage(noRequests);
                return true;
            }

            UUID targetUUID = senderPlayer.getUniqueId();
            UUID senderUUID = teleportRequests.remove(targetUUID);

            Player requester = Bukkit.getPlayer(senderUUID);

            if (requester != null && requester.isOnline()) {
                if (senderPlayer.getLevel() >= expCost) {
                    senderPlayer.setLevel(senderPlayer.getLevel() - expCost);
                    teleportPlayer(requester, senderPlayer);
                    requester.sendMessage("§e" + senderPlayer.getName() + requestAcceptedSender);
                    senderPlayer.sendMessage(senderAccepted);
                } else {
                    senderPlayer.sendMessage(notEnoughExp);
                }

            } else {
                senderPlayer.sendMessage(playerNotOnline);
            }
        } else if (command.getName().equalsIgnoreCase("tpdeny")) {
            if (!teleportRequests.containsKey(senderPlayer.getUniqueId())) {
                senderPlayer.sendMessage(noRequests);
                return true;
            }

            UUID targetUUID = senderPlayer.getUniqueId();
            UUID senderUUID = teleportRequests.remove(targetUUID);

            Player requester = Bukkit.getPlayer(senderUUID);

            if (requester != null && requester.isOnline()) {
                requester.sendMessage("§c" + senderPlayer.getName() + senderDeniedTPA);
                senderPlayer.sendMessage(recipientDeniedTPA);
            } else {
                senderPlayer.sendMessage(ChatColor.RED + "The player who sent the request is no longer online.");
            }
        }else if (command.getName().equalsIgnoreCase("tpreload") && sender.isOp()){
            reloadConfig();
        }

        return true;
    }
}