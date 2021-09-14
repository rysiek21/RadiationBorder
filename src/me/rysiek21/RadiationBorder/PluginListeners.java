package me.rysiek21.RadiationBorder;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

public class PluginListeners implements Listener{
	
	public List<Player> playersToDamage = new ArrayList<>();
	public List<Player> noDamagePlayers = new ArrayList<>();
	int damageTask;
	
	@EventHandler
	void PlayerMove(PlayerMoveEvent e) {
		FileConfiguration config = Main.getConfigFile();
		int xp = config.getInt("border.center.x") + config.getInt("border.radius");
		int xm = config.getInt("border.center.x") - config.getInt("border.radius");
		int zp = config.getInt("border.center.z") + config.getInt("border.radius");
		int zm = config.getInt("border.center.z") - config.getInt("border.radius");
		
		Player p = e.getPlayer();
		if(e.getTo().getBlockX() > xp || e.getTo().getBlockX() < xm || e.getTo().getBlockZ() > zp || e.getTo().getBlockZ() < zm) {
			if (!playersToDamage.contains(p)) {
				playersToDamage.add(p);
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&aRadiationBorder&7] &cYou enter to the radiation zone"));
			}
		}else {
			if(playersToDamage.contains(p)) {
				playersToDamage.remove(p);
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&aRadiationBorder&7] &aYou leave from the radiation zone"));
			}
		}
	}
	
	@EventHandler
	void PlayerLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if(playersToDamage.contains(p)) {
			playersToDamage.remove(p);
		}
	}
	
	@EventHandler
	void UsePotion(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getItem() != null) {
				if(e.getItem().getType() == Material.POTION && e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Anti-Radiation potion")) {
					if(!noDamagePlayers.contains(p)) {
						noDamagePlayers.add(p);
						p.getInventory().remove(e.getItem());
						p.getInventory().addItem(new ItemStack(Material.GLASS_BOTTLE));
					}
				}
			}
		}
	}
	
	public void DamagePlayer() {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		damageTask = scheduler.scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            @Override
            public void run()
            {
               	for (Player player : playersToDamage) {
               		if(!noDamagePlayers.contains(player)) {
                    	player.damage(1);	
               		}
               	}
            }
        }, 0L, 20L);
	}
}
