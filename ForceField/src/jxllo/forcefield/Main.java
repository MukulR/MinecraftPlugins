package jxllo.forcefield;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Main extends JavaPlugin implements Listener, CommandExecutor {
	
	HashMap<UUID, Boolean> ffmap = new HashMap<>(); 
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		this.getCommand("forcefield").setExecutor(this);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (event.getPlayer() != null) {
			Player movingPlayer = event.getPlayer();
			ArrayList<Player> ffedList = loadFfedPlayers();
			
			for (Player p : ffedList) {
				
				if((p.getLocation().distance(movingPlayer.getLocation()) <= 5 && !p.equals(movingPlayer) && !movingPlayer.hasPermission("forcefield.ignore"))) {
					
					movingPlayer.setVelocity(movingPlayer.getLocation().getDirection().multiply(-1.3).add(new Vector(0.0, 0.5, 0.0)));
					
				}
				
			}
			
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (movingPlayer.getLocation().distance((p.getLocation())) <= 5 && ffmap.get(movingPlayer.getUniqueId()) != null 
						&& ffmap.get(movingPlayer.getUniqueId()) == true
						&& !p.equals(movingPlayer) && ffmap.get(p.getUniqueId()) == null
						&& !p.hasPermission("forcefield.ignore")) {
					
					System.out.println(p.getName());
					
					p.setVelocity(p.getLocation().getDirection().multiply(-1.3).add(new Vector(0.0, 0.5, 0.0)));
					
				}
			}
			
		}
	}
	
	public ArrayList<Player> loadFfedPlayers() {
		ArrayList<Player> ffedList = new ArrayList<Player>();
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (ffmap.get(p.getUniqueId()) != null && ffmap.get(p.getUniqueId()) == true) {
				ffedList.add(p);
			} 
		}
		return ffedList;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player && sender.hasPermission("forcefield.use")) {
			Player p = (Player) sender;
			if (args[0].equals("on")) {
				
				ffmap.put(p.getUniqueId(), true);
				sender.sendMessage("§9Forcefield> §aEnabled");
				
			} else if (args[0].equals("off")) {
				
				ffmap.remove(p.getUniqueId(), true);
				sender.sendMessage("§9Forcefield> §cDisabled");
				
			} else if (args[0] == null){
				sender.sendMessage("§9Forcefield> §cInvalid Argument");
			} else {
				sender.sendMessage("§9Forcefield> §cInvalid Argument");
			}
			
		} else {
			sender.sendMessage("§9Forcefield> §cInvalid Permissions");
		}
		return true;
	}
}
