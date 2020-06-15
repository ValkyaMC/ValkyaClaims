package fr.volax.valkya.commands;

import fr.volax.valkya.ValkyaClaims;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandAP implements CommandExecutor {

    private final ValkyaClaims plugin;

    public CommandAP(ValkyaClaims plugin, String commandName) {
        this.plugin = plugin;
        this.plugin.getCommand(commandName).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return false;
    }
}
