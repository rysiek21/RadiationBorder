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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public class PluginListeners implements Listener{
	
	public List<Player> playersToDamage = new ArrayList<>();
	public List<Player> noDamagePlayers = new ArrayList<>();
	int damageTask;
	int noDamageTask;
	
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
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.enter")));
			}
		}else {
			if(playersToDamage.contains(p)) {
				playersToDamage.remove(p);
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.leave")));
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
		FileConfiguration config = Main.getConfigFile();
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getItem() != null) {
				if(e.getItem().getType() == Material.POTION && e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', config.getString("messages.potion-name")))) {
					if(!noDamagePlayers.contains(p)) {
						noDamagePlayers.add(p);
						p.getInventory().remove(e.getItem());
						p.getInventory().addItem(new ItemStack(Material.GLASS_BOTTLE));
						NoDamageTimer(p);
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
	
	public void NoDamageTimer(Player p) {
		FileConfiguration config = Main.getConfigFile();
		new BukkitRunnable() {
			int time = config.getInt("potion.time");
			@Override
			public void run() {
            	if(time == 0) {
            		noDamagePlayers.remove(p);
            		p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.potion-end")));
            		cancel();
            	}
            	if(time == config.getInt("potion.warning")) {
            		p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.potion-warning")));
            	}
            	time--;
			}
		}.runTaskTimer(Main.getPlugin(), 0L, 20L);
	}
}
