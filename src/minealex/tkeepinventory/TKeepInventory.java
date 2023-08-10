package minealex.tkeepinventory;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import minealex.tkeepinventory.commands.Commands;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public class TKeepInventory extends JavaPlugin implements Listener {

    private FileConfiguration config;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        
        // Cargar la configuración
        loadConfig();

        // Registrar el comando /tki reload
        getCommand("tki").setExecutor(new Commands(this));
    }

    public void loadConfig() {
        // Si no existe el archivo config.yml, lo crea
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }

        // Cargar la configuración desde el archivo
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String worldName = player.getWorld().getName();
        
        if (config.getBoolean("worlds." + worldName + ".enabled") && player.hasPermission("tki.keep")) {
            event.setKeepInventory(true);
            event.setKeepLevel(true);
            event.getDrops().clear();
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
