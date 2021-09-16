package me.rysiek21.RadiationBorder;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("radiationborder")) {
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("reload")) {
					if(sender.hasPermission("radiationborder.admin")) {
						Main.ReloadConfig();
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&aRadiationBorder&7] &aConfig reloaded"));
						return true;
					}else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&aRadiationBorder&7] &cYou don't have permissions"));
					}
				}
			} else {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7----------- &cRadiationBorder &7-----------"));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/radiationborder reload &7- &areload config"));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7------ &cversion &b1.0 &7--- &cby &brysiek21 &7------"));
				return true;
			}
		}
		return false;
	}
}
