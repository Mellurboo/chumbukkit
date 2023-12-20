package com.mellurboo.reminders;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class Reminders extends JavaPlugin {

    public String   mvp_announcementText;
    public int      mvp_announcementFrequency;          // this is always done in mins, I don't know why you would need it by second lol

    public void loadConfig(){
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        mvp_announcementText = config.getString("mvp_AnnouncementText", "§x§C§B§2§D§3§E[§x§C§D§2§E§3§E]§x§C§F§3§0§3§E=§x§D§0§3§1§3§D=§x§D§2§3§2§3§D=§x§D§4§3§4§3§D=§x§D§6§3§5§3§D=§x§D§8§3§6§3§D=§x§D§9§3§7§3§C=§x§D§B§3§9§3§C=§x§D§D§3§A§3§C=§x§D§F§3§B§3§C=§x§E§1§3§D§3§C=§x§E§2§3§E§3§B=§x§E§4§3§F§3§B=§x§E§6§4§1§3§B=§x§E§8§4§2§3§B=§x§E§A§4§3§3§B=§x§E§B§4§4§3§A=§x§E§D§4§6§3§A[§x§E§F§4§7§3§A]\\n§x§C§B§2§D§3§ED§x§C§C§2§D§3§Eo§x§C§C§2§E§3§En§x§C§D§2§E§3§E'§x§C§D§2§F§3§Et §x§C§E§2§F§3§Ef§x§C§E§3§0§3§Eo§x§C§F§3§0§3§Er§x§D§0§3§0§3§Dg§x§D§0§3§1§3§De§x§D§1§3§1§3§Dt §x§D§1§3§2§3§Dt§x§D§2§3§2§3§Do §x§D§3§3§2§3§Du§x§D§3§3§3§3§Ds§x§D§4§3§3§3§De §x§D§4§3§4§3§D/§x§D§5§3§4§3§Ds§x§D§5§3§5§3§Dt§x§D§6§3§5§3§Do§x§D§7§3§5§3§Dr§x§D§7§3§6§3§De §x§D§8§3§6§3§Dt§x§D§8§3§7§3§Do §x§D§9§3§7§3§Cc§x§D§A§3§7§3§Ch§x§D§A§3§8§3§Ce§x§D§B§3§8§3§Cc§x§D§B§3§9§3§Ck §x§D§C§3§9§3§Co§x§D§C§3§A§3§Cu§x§D§D§3§A§3§Ct §x§D§E§3§A§3§Co§x§D§E§3§B§3§Cu§x§D§F§3§B§3§Cr §x§D§F§3§C§3§Cs§x§E§0§3§C§3§Ch§x§E§0§3§D§3§Co§x§E§1§3§D§3§Cp §x§E§2§3§D§3§Ba§x§E§2§3§E§3§Bn§x§E§3§3§E§3§Bd §x§E§3§3§F§3§Bh§x§E§4§3§F§3§Be§x§E§5§3§F§3§Bl§x§E§5§4§0§3§Bp §x§E§6§4§0§3§Bs§x§E§6§4§1§3§Bu§x§E§7§4§1§3§Bp§x§E§7§4§2§3§Bp§x§E§8§4§2§3§Bo§x§E§9§4§2§3§Br§x§E§9§4§3§3§Bt §x§E§A§4§3§3§Bt§x§E§A§4§4§3§Bh§x§E§B§4§4§3§Ae §x§E§C§4§4§3§As§x§E§C§4§5§3§Ae§x§E§D§4§5§3§Ar§x§E§D§4§6§3§Av§x§E§E§4§6§3§Ae§x§E§E§4§7§3§Ar§x§E§F§4§7§3§A!\\n§x§C§B§2§D§3§E[§x§C§D§2§E§3§E]§x§C§F§3§0§3§E=§x§D§0§3§1§3§D=§x§D§2§3§2§3§D=§x§D§4§3§4§3§D=§x§D§6§3§5§3§D=§x§D§8§3§6§3§D=§x§D§9§3§7§3§C=§x§D§B§3§9§3§C=§x§D§D§3§A§3§C=§x§D§F§3§B§3§C=§x§E§1§3§D§3§C=§x§E§2§3§E§3§B=§x§E§4§3§F§3§B=§x§E§6§4§1§3§B=§x§E§8§4§2§3§B=§x§E§A§4§3§3§B=§x§E§B§4§4§3§A=§x§E§D§4§6§3§A[§x§E§F§4§7§3§A]");
        mvp_announcementFrequency = config.getInt("mvp_AnnouncementFrequency", 30);
    }

    @Override
    public void onEnable() {
        loadConfig();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this::mvp_sendAnnouncement, 0, 20 * (60 * mvp_announcementFrequency));
    }

    private void mvp_sendAnnouncement() {
        Bukkit.broadcastMessage(mvp_announcementText);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (label.equalsIgnoreCase("reminders.reload") && sender.isOp()){
            sender.sendMessage("§cReminders config reloaded!");
        }
        return false;
    }
}
