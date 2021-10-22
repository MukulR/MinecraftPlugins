package jxllo.manhuntcompass.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import jxllo.manhuntcompass.Main;

public class CompassCommand implements CommandExecutor{
	private Main plugin;
	
	public CompassCommand(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("autobridge").setExecutor(this);
	}
	
	 @Override
	 public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		 if (sender instanceof Player && sender.hasPermission("manhuntcompass.use")) {
	    		if (args.length == 0) {
	    			giveCompass((Player) sender);
	    			sender.sendMessage("§9Manhunt Compass> §aCompass received.");
	    		} else {
	    			Player target = Bukkit.getPlayerExact(args[0]);
	    			if (target instanceof Player) {
	    				giveCompass(target);
	    				target.sendMessage("§9Manhunt Compass> §aAn admin has given you §lManhunt Compass!");
	    				sender.sendMessage("§9Manhunt Compass> §aCompass sent to " + target.getName() + ".");
	    			} else {
	    				sender.sendMessage("§9Manhunt Compass> §cThat player does not exist!");
	    			}
	    		}
	    	} else {
	    		sender.sendMessage("§9Manhunt Compass> §cInvalid Permissions");
	    	}
	    	return true;

	 }
	 
	 public void giveCompass(Player player) {
    	ItemStack item = new ItemStack(Material.COMPASS, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "Manhunt Compass");
		item.setItemMeta(meta);
		player.getInventory().addItem(item);
    }
}
