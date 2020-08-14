package fr.volax.valkyaclaims;

import com.massivecraft.factions.*;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Commande /claims
 *
 * @author Volax
 * @deprecated Under Rewrite
 * @see SignListener
 */
public class CommandClaims implements CommandExecutor {
    public CommandClaims(String commandName) {
        ValkyaClaims.getInstance().getCommand(commandName).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Vous devez être un joueur pour pouvoir executer cette commande !");
            return false;
        }
        Player player = (Player) sender;

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("buy")) {
                FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
                Faction faction = Factions.getInstance().getFactionById(fplayer.getFactionId());

                if (faction.getId().equalsIgnoreCase(Factions.getInstance().getWilderness().getId())) {
                    player.sendMessage(ValkyaClaims.getPREFIX() + " §eVous devez être dans une faction pour pouvoir acheter un Avant Poste !");
                    return false;
                } else {
                    if (fplayer != faction.getFPlayerAdmin()) {
                        player.sendMessage(ValkyaClaims.getPREFIX() + " §eVous devez être le chef de votre faction pour pouvoir acheter un Avant Poste !");
                        return false;
                    }

                    int balance = (int) ValkyaClaims.economy.getBalance(player);

                    Chunk chunk;
                }
            } else {
                helpMessage(player);
            }
        } else {
            helpMessage(player);
            return false;
        }
        return false;
    }

    private void helpMessage(Player player) {
        if (player.hasPermission("valkya.claims.setPrice"))
            player.sendMessage(ValkyaClaims.getPREFIX() + " §e/claims setPrice <NORD|SUD|EST|OUEST|CUSTOM>");
        player.sendMessage(ValkyaClaims.getPREFIX() + " §e/claims buy");
    }
}