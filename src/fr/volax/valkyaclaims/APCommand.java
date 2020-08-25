package fr.volax.valkyaclaims;

import com.massivecraft.factions.*;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class APCommand implements CommandExecutor {
    public APCommand(String string) {
        ValkyaClaims.getInstance().getCommand(string).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Vous devez être un joueur pour pouvoir executer cette commande !");
            return false;
        }
        Player player = (Player) sender;

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("disband")) {
                FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
                Faction faction = Factions.getInstance().getFactionById(fplayer.getFactionId());
                Chunk chunk = player.getLocation().getChunk();
                FLocation fLocation = new FLocation(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());

                if (!Board.getInstance().getFactionAt(fLocation).getId().equals(faction.getId())) {
                    player.sendMessage(ValkyaClaims.getPREFIX() + " §eVous n'êtes pas propriétaire de cet AP !");
                    return false;
                }else if(Board.getInstance().getFactionAt(fLocation).getId().equals(faction.getId()) && ChunkManager.getDirection(ChunkManager.getChunkID(chunk)) == null){
                    player.sendMessage(ValkyaClaims.getPREFIX() + " §eVous devez vous placer dans le premier chunk de votre AP pour pouvoir le disband !");
                    return false;
                } else {
                    if (fplayer != faction.getFPlayerAdmin()) {
                        player.sendMessage(ValkyaClaims.getPREFIX() + " §eVous devez être le chef de votre faction pour pouvoir disband cet AP !");
                        return false;
                    }

                    player.sendMessage(ValkyaClaims.getPREFIX() + " §eVous venez de supprimer cet AP !");
                    ChunkManager.deleteChunk(chunk, player);
                }
                return false;
            } else if (args[0].equalsIgnoreCase("reload")) {
                if (!player.isOp()) {
                    player.sendMessage("§cVous n'avez pas la permission d'éxecuter cette commande !");
                    return false;
                }
                player.sendMessage(ValkyaClaims.getPREFIX() + " §eVous venez de reload config.yml");
                ValkyaClaims.getInstance().reloadConfig();
                return false;
            } else if (args[0].equalsIgnoreCase("delete")) {
                ChunkManager.noReverseChunk(player.getLocation().getChunk());
                return false;
            } else {
                helpMessage(player);
                return false;
            }
        } else {
            helpMessage(player);
            return false;
        }
    }

    private void helpMessage(Player player) {
        if(player.isOp()){
            player.sendMessage(ValkyaClaims.getPREFIX() + " §e/ap reload");
            player.sendMessage(ValkyaClaims.getPREFIX() + " §e/ap delete §CUTILISER CETTE COMMANDE QUAND CAS DE FAIL DES AP");
        }
        player.sendMessage(ValkyaClaims.getPREFIX() + " §e/ap disband");
    }
}
