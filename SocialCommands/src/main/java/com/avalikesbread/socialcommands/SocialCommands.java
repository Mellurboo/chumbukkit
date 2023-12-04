package com.avalikesbread.socialcommands;

import org.bukkit.plugin.java.JavaPlugin;

//
//  Ava "LikesBread" 2023
//  Chum SMP - https://discord.com/invite/bmz4wayCZb
//

public final class SocialCommands extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("Discord").setExecutor(new DiscordCmd()); // This registers the /discord command that can be found in this folder under "DiscordCmd"
    }
}
