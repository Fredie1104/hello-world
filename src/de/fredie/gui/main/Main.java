package de.fredie.gui.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.fredie.gui.commands.Gui;

public class Main extends JavaPlugin {
	
	public void onEnable() {
		getCommand("gui").setExecutor(new Gui());
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new Gui(), this);
	}
	
	
	

}
