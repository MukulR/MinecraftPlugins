package jxllo.manhuntcompass;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import jxllo.manhuntcompass.commands.CompassCommand;

public class Main extends JavaPlugin  {
	@Override
	public void onEnable() {
		new CompassCommand(this);
	}
}
