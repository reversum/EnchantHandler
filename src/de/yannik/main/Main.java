package de.yannik.main;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import de.yannik.listener.EnchantHandler;


public class Main extends JavaPlugin
{
    
    public void onEnable() {
        final PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents((Listener)new EnchantHandler(), (Plugin)this);
		getCommand("openEnchants").setExecutor(new EnchantHandler());
    }

}
