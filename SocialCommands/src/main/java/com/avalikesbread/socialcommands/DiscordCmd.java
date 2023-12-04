package com.avalikesbread.socialcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DiscordCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("§x§0§0§F§F§E§0D§x§0§B§D§7§E§4I§x§1§5§A§E§E§7S§x§2§0§8§6§E§BC§x§2§A§5§E§E§EO§x§3§5§3§5§F§2R§x§3§F§0§D§F§5D§f -- https://discord.com/invite/bmz4wayCZb"); //DISCORD vv [LINK]
        // This sends the link and nice text to the user. If you want to make a gradient like this, use https://www.simplymc.art/Gradients/ <3
        return true;
    }
}
