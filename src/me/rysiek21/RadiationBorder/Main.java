package me.rysiek21.RadiationBorder;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
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
}
