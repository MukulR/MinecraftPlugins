package jxllo.manhuntcompass.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerEvents implements Listener {
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
	    Action action = event.getAction();
	    ItemStack item = event.getItem();
	    
	    if (action.equals( Action.RIGHT_CLICK_AIR ) || action.equals( Action.RIGHT_CLICK_BLOCK ) ) {
	         if (item != null && item.getType() == Material.COMPASS ) {
	        	 
	             player.sendMessage( "You have right click a slime ball!" );
	         } 
	     }
	}
}
