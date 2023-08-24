package minealex.tkeepinventory.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import minealex.tkeepinventory.TKeepInventory;

public class Commands implements CommandExecutor {

    private final TKeepInventory plugin;

    public Commands(TKeepInventory plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                return handleReloadCommand(sender);
            } else if (args[0].equalsIgnoreCase("version")) {
                return handleVersionCommand(sender);
            }
        }
        return false;
    }

    private boolean handleReloadCommand(CommandSender sender) {
        if (sender.hasPermission("tki.reload")) {
            plugin.loadConfig();
            String reloadMessage = plugin.getConfig().getString("messages.reload-message", "&5TKI &e> &aThe configuration has been successfully reloaded!");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', reloadMessage));
        } else {
            String noPermissionMessage = plugin.getConfig().getString("messages.no-permission-message", "&5TKI &e> &cYou do not have permission to reload the configuration.");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermissionMessage));
        }
        return true;
    }

    private boolean handleVersionCommand(CommandSender sender) {
        if (sender.hasPermission("tki.version")) {
            String version = plugin.getDescription().getVersion();
            String versionMessage = plugin.getConfig().getString("messages.version-message", "&5TKI &e> &aPlugin version: &e%s").replace("%s", version);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', versionMessage));
        } else {
            String noPermissionMessage = plugin.getConfig().getString("messages.no-permission-message", "&5TKI &e> &cYou do not have permission to view the plugin version.");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermissionMessage));
        }
        return true;
    }
}
