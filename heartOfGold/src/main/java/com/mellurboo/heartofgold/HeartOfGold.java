package com.mellurboo.heartofgold;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class HeartOfGold extends JavaPlugin implements Listener {

    private int maxHealthObtainable;            // how much health the player can have total + the extra hearts
    private float chanceOfObtaining;            // the chance the player has of getting the extra health
    private int healthGained;                   // the ammount of health gained
    private boolean canUseSilktouch;            // can obtain extra health when they have silk touch
    private int minimumHealth;                  // how much health the player can have maximum before it's removed on death
    private int healthLostOnDeath;              // how much health is lost on death

    private String healthGainedMessage;         // the message sent to the player when they gain health

    @Override
    public void onEnable() {
        LoadConfig();
        getCommand("heartOfGold.reloadConfig").setExecutor(this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    public void LoadConfig(){
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        maxHealthObtainable = config.getInt("maxObtainableHealth", 26);
        chanceOfObtaining = config.getInt("chanceOfObtaining", 15);
        healthGained = config.getInt("healthGained", 1);
        canUseSilktouch = config.getBoolean("canUseSilkTouch", false);
        minimumHealth = config.getInt("minimumHealth", 22);
        healthLostOnDeath = config.getInt("healthLostOnDeath", 2);
        healthGainedMessage = config.getString("healthGainedMessage", "§6Chrysus has blessed you, your total health is now §e");

        Bukkit.getLogger().info("\n[]=====[HeartOfGold]=====[]\n" +
                "MaxHealthObtainable: " + maxHealthObtainable + "\n" +
                "ChanceOfObtaining: " + chanceOfObtaining + "\n" +
                "heathGained: " + healthGained + "\n" +
                "canUseSilkTouch: " + canUseSilktouch + "\n" +
                "miniumum health: " + minimumHealth + "\n" +
                "healthLostOnDeath: " + healthLostOnDeath + "\n" +
                "healthGainedMessage: " + healthGainedMessage + "\n" +
                "[]=======================================================[]");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.GOLD_ORE || event.getBlock().getType() == Material.DEEPSLATE_GOLD_ORE) {
            Player player = event.getPlayer();

            if (player.getMaxHealth() != maxHealthObtainable && !hasSilkTouch(player.getItemInHand())) {
                int rng = (int)(Math.random() * (100 - 1 + 1) + 1);
                if (rng <= chanceOfObtaining) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, (3*20), 10));
                    increaseHealth(event.getPlayer(), healthGained);
                    // make sure the player cant go above the 26hp (13 heart) limit, and that the random number is less than
                    // or equal to 0.15*
                }
            }
        }
    }
    @EventHandler
    public void onPlayerDie(PlayerDeathEvent event){
        // we don't want to take more health from the player if they don't have any extra
        if (event.getPlayer().getMaxHealth() >= maxHealthObtainable){
            Player player = event.getPlayer();
            player.playSound(player.getLocation(), Sound.AMBIENT_CAVE, 1.0f, 1.0f);
            decreaseHealth(player, healthLostOnDeath);
        }
    }
    public void decreaseHealth(Player player, float decrement){
        player.getPlayer().setMaxHealth(player.getMaxHealth() - decrement);
    }

    public void increaseHealth(Player player, float increment){
        player.getPlayer().setMaxHealth(player.getMaxHealth() + increment);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        player.sendMessage(healthGainedMessage + (int) player.getMaxHealth());
    }

    private boolean hasSilkTouch(ItemStack item) {
        if (item != null) {
            return item.getEnchantments().containsKey(Enchantment.SILK_TOUCH);
        }

        if (canUseSilktouch){
            return false;
        }

        return false;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("heartOfGold.reloadConfig")) {
            if (sender.hasPermission("heartofgold.reloadconfig")) {
                LoadConfig();
                sender.sendMessage("§6Heart of gold config reloaded.");
                return true;
            } else {
                sender.sendMessage("§cYou don't have permission to use this command.");
                return true;
            }
        }
        return false;
    }
}
