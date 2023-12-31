package com.mellurboo.handbook;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Handbook extends JavaPlugin implements CommandExecutor {

    public String title;
    public String author;
    List<String> pages;


    @Override
    public void onEnable() {
        getCommand("handbook").setExecutor(this);
        loadConfig();
    }

    public void loadConfig(){
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        title = config.getString("server-info.title");
        author = config.getString("server-info.author");
        pages = config.getStringList("server-info.pages");
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("handbook")) {

            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (args.length == 0) {
                    // /handbook command without arguments, show server information
                    handbookCommand(player, false);
                } else if (args.length == 1 && args[0].equalsIgnoreCase("give")) {
                    // /handbook give command, give the player a physical copy of the handbook
                    handbookCommand(player, true);
                } else if (args.length == 1 && args [0].equalsIgnoreCase("reload") && sender.isOp()){
                    loadConfig();
                    sender.sendMessage("Handbook config reloaded");
                }else {
                    player.sendMessage("Usage: /handbook [give]");
                }

                return true;
            } else {
                sender.sendMessage("Only players can use this command.");
                return true;
            }
        }

        return false;
    }

    private void handbookCommand(Player player, boolean give) {
        ItemStack handbook = createHandbook(title, author, pages);
        player.openBook(handbook);

        // Give the player a physical copy of the handbook
        if(give){
            player.getInventory().addItem(handbook);
        }
    }

    private ItemStack createHandbook(String title, String author, List<String> pages) {
        // Create a written book item
        ItemStack handbook = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) handbook.getItemMeta();

        // Set the title and author of the book
        meta.setTitle(title);
        meta.setAuthor(author);

        // Add pages with server information
        meta.setPages(pages);

        handbook.setItemMeta(meta);

        return handbook;
    }
}