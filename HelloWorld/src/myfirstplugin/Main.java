package myfirstplugin;

import org.bukkit.plugin.java.JavaPlugin;
import myfirstplugin.commands.HelloCommand;

public class Main extends JavaPlugin{
	
	@Override
	public void onEnable() {
		new HelloCommand(this);
	}
	
}
