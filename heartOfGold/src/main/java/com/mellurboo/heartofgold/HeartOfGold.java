package com.mellurboo.heartofgold;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
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

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        if (event.getBlock().getType() == Material.GOLD_ORE || event.getBlock().getType() == Material.DEEPSLATE_GOLD_ORE) {
            Player player = event.getPlayer();
            if (player.getMaxHealth() != 26 && !hasSilkTouch(player.getItemInHand())) {
                float rng = (float) (Math.random() * (1 - 0.01f + 0.01f) + 0.01f);
                Bukkit.getLogger().info(rng + ":"+ hasSilkTouch(player.getItemInHand()));
                if (rng < 0.15) { // 17% chance
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 3, 10));
                    increaseHealth(event.getPlayer(), 1f, rng);
                }
            }
        }
    }
    @EventHandler
    public void onPlayerDie(PlayerDeathEvent event){
        if (event.getPlayer().getMaxHealth() >= 22){
            int healthLost = 2;
            Player player = event.getPlayer();
            player.playSound(player.getLocation(), Sound.AMBIENT_CAVE, 1.0f, 1.0f);
            decreaseHealth(player, healthLost);
        }
    }
    public void decreaseHealth(Player p, float val){
        p.getPlayer().setMaxHealth(p.getMaxHealth() - val);
    }

    public void increaseHealth(Player p, float val, float rand){
        p.getPlayer().setMaxHealth(p.getMaxHealth() + val);
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        p.sendMessage("§6Chrysus has blessed you, you now have §e(" + p.getHealth() + " health points)");
    }

    private boolean hasSilkTouch(ItemStack item) {
        if (item != null) {
            return item.getEnchantments().containsKey(Enchantment.SILK_TOUCH);
        }
        return false;
    }
}
