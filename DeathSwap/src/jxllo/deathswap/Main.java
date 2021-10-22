package jxllo.deathswap;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Main extends JavaPlugin implements CommandExecutor, Listener {
	private Boolean cancel = false;
	private Boolean gameActive = false;
	
	private Player plr1;
	private Player plr2;
	
	@Override
	public void onEnable() {
		this.getCommand("startdeathswap").setExecutor(this);
		this.getCommand("stopdeathswap").setExecutor(this);
		this.getCommand("deathswapgame").setExecutor(this);
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (gameActive) {
			if (event.getPlayer().equals(plr1) || event.getPlayer().equals(plr2)) {
				cancel = true;
				gameActive = false;
				plr1.sendMessage("§9Death Swap> §cGame cancelled! One of the players left!");
				plr2.sendMessage("§9Death Swap> §cGame cancelled! One of the players left!");
				plr1 = null;
				plr2 = null;
			}
		}
	}
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		
		if (sender instanceof Player && sender.hasPermission("deathswap.control")) {
			
			
			if (cmd.getName().equals("startdeathswap")) {
				
				if (gameActive) {
					sender.sendMessage("§9Death Swap> §cThere is already a game playing! To stop that game, /stopds");
					return false;
				}
				
				cancel = false;
				int time;
				
				if (args.length < 3 || args[0] == null || args[1] == null || args[2] == null) {
					
					sender.sendMessage("§9Death Swap> §cFormat: /startdeathswap <delay between swaps(mins)> <nameOfPlayer1> <nameOfPlayer2>");
					
				} else {
					
					try {
						time = Integer.valueOf(args[0]);
					} catch (NumberFormatException e) {
						sender.sendMessage("§9Death Swap> §cFormat: /startdeathswap <delay between swaps(mins)> <nameOfPlayer1> <nameOfPlayer2>");
						return false;
					}
					
					Player p1 = Bukkit.getPlayerExact(args[1]);
					Player p2 = Bukkit.getPlayerExact(args[2]);
					
					if (p1 == null || p2 == null) {
						sender.sendMessage("§9Death Swap> §cCould not find player(s)! Ensure spelling is exact!");
						return false;
					}
					
					plr1 = p1;
					plr2 = p2;
					
					gameLoop(time, p1, p2);
					p1.sendMessage("§9Death Swap> §aGame started between " + p1.getName() + " and " + p2.getName() + "! " + time + " minute(s) until swap!");
					p2.sendMessage("§9Death Swap> §aGame started between " + p1.getName() + " and " + p2.getName() + "! " + time + " minute(s) until swap!");
					gameActive = true;
					
				}
			} else if(cmd.getName().equals("stopdeathswap")) {
				
				if (gameActive) {
					cancel = true;
					gameActive = false;
					plr1 = null;
					plr2 = null;
					sender.sendMessage("§9Death Swap> §aGame stopped!");
				} else {
					sender.sendMessage("§9Death Swap> §cThere is no game to stop!");
				}
				
			}
			
		} else {
			sender.sendMessage("§9Death Swap> §cInvalid Permissions");
		}
		
		if (sender instanceof Player && cmd.getName().equals("deathswapgame")) {
			if (gameActive) {
				sender.sendMessage("§9Death Swap> §aThere is an ongoing game between " + plr1.getName() + " and " + plr2.getName() + "!");
			} else {
				sender.sendMessage("§9Death Swap> §aThere are no ongoing games!");
			}
		}
		

		return true;
	}
	
	public void gameLoop(int time, Player p1, Player p2) {
		new BukkitRunnable() {
			@Override
			public void run() {
				
				if (!cancel) {
					countdown(time, p1, p2);
				} else {
					this.cancel();
				}
				
			}
		}.runTaskTimer(this, time * 1200, time * 1200);
	}
	
	public void countdown(int time, Player p1, Player p2) {
		new BukkitRunnable() {
			int count = 10;
			
			@Override
			public void run() {
				if (!cancel) {
					p1.sendMessage("§9Death Swap> §c§l Swapping in " + count);
				    p2.sendMessage("§9Death Swap> §c§l Swapping in " + count);
				    if (count == 1) {
						this.cancel();
						swap(time, p1, p2);
					}
				    count--;
				} else {
					this.cancel();
				}
			}
		}.runTaskTimer(this, 0, 20);
	}
	
	public void swap(int time, Player p1, Player p2) {
		Location p1Location = p1.getLocation();
		
		p1.setVelocity(new Vector(0, 0, 0));
		p2.setVelocity(new Vector(0, 0, 0));
		p1.setFallDistance(0);
		p2.setFallDistance(0);
		
		p1.teleport(p2.getLocation());
		p2.teleport(p1Location);
		
		p1.sendMessage("§9Death Swap> §aSwapped! " + time + " minutes till next swap!");
		p2.sendMessage("§9Death Swap> §aSwapped! " + time + " minutes till next swap!");
	}
	
}
