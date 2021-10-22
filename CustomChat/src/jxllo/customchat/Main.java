package jxllo.customchat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		if (event.getPlayer() != null) {
			
			if (event.getPlayer().getName().equals("Cherds")) {
				event.setFormat("§c§l[OWNER] §f" + event.getPlayer().getName() + ": " +  event.getMessage());
			} else if (event.getPlayer().isOp()) {
				event.setFormat("§c§l[ADMIN] §f" + event.getPlayer().getName() + ": " +  event.getMessage());
			} else {
				event.setFormat("§7[✦] " + event.getPlayer().getName() + ": " +  event.getMessage());
			}
			
		}
	}
}
