package me.rysiek21.RadiationBorder;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Plugin plugin;
	private static FileConfiguration config;
	
	@Override
	public void onEnable() {
		System.out.println("[RadiationBorder] Running version 1.0");
		plugin = (Plugin) this;
		this.saveDefaultConfig();
		config = this.getConfig();
		PluginListeners pluginListeners = new PluginListeners();
		registerEvents(this, pluginListeners);
		pluginListeners.DamagePlayer();
		CreateRecipe();
	}
	
	public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
	
	public static Plugin getPlugin() {
        return plugin;
    }
	
	public static FileConfiguration getConfigFile() {
		return config;
	}
	
	void CreateRecipe() {
		ItemStack item = new ItemStack(Material.POTION);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("messages.potion-name")));
		item.setItemMeta(meta);
		
		NamespacedKey key = new NamespacedKey(this, "radiation_potion");
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		recipe.shape("ABC","DEF","GHI");
		recipe.setIngredient('A', Material.getMaterial(config.getString("recipe.1")));
		recipe.setIngredient('B', Material.getMaterial(config.getString("recipe.2")));
		recipe.setIngredient('C', Material.getMaterial(config.getString("recipe.3")));
		recipe.setIngredient('D', Material.getMaterial(config.getString("recipe.4")));
		recipe.setIngredient('E', Material.getMaterial(config.getString("recipe.5")));
		recipe.setIngredient('F', Material.getMaterial(config.getString("recipe.6")));
		recipe.setIngredient('G', Material.getMaterial(config.getString("recipe.7")));
		recipe.setIngredient('H', Material.getMaterial(config.getString("recipe.8")));
		recipe.setIngredient('I', Material.getMaterial(config.getString("recipe.9")));
		
		Bukkit.addRecipe(recipe);
		
	}
}
