package fr.volax.valkyaclaims.commands;

import fr.volax.valkyaclaims.ValkyaClaims;
import fr.volax.valkyaclaims.chunk.ChunkManager;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandClaims implements CommandExecutor {


    public CommandClaims(String commandName) {
        ValkyaClaims.getInstance().getCommand(commandName).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Vous devez être un joueur pour pouvoir executer cette commande !");
            return false;
        }
        Player player = (Player)sender;
        if(!player.hasPermission("valkya.claims.create")){
            player.sendMessage("§cVous n'avez pas la permission d'executer cette commande !");
            return false;
        }

        if(args.length != 1){
            helpMessage(player);
            return false;
        }

        if(args[0].equalsIgnoreCase("create")){
            Chunk chunk = player.getLocation().getChunk();

            if(ChunkManager.doesChunkIsRegistered(chunk)){
                player.sendMessage(ValkyaClaims.getPREFIX() + " §eCe chunk a déjà été ajoutée à la liste des chunks en ventes !");
                return false;
            }

            player.sendMessage(ValkyaClaims.getPREFIX() + " §eVous venez d'ajouter ce chunk dans la liste des chunks en ventes.");
            ChunkManager.registerNewChunk(chunk);
        }else if(args[0].equalsIgnoreCase("delete")) {
            Chunk chunk = player.getLocation().getChunk();

            if (!ChunkManager.doesChunkIsRegistered(chunk)) {
                player.sendMessage(ValkyaClaims.getPREFIX() + " §eCe chunk n'a pas été créé !");
                return false;
            }

            player.sendMessage(ValkyaClaims.getPREFIX() + " §eVous venez de retirer ce chunk dans la liste des chunks en ventes, cela veut dire que ce chunk n'est plus vendable.");
            ChunkManager.deleteChunk(chunk);
        }else if(args[0].equalsIgnoreCase("buy")){
            //TODO Soon
        }else if(args[0].equalsIgnoreCase("map")){
            Chunk playerChunk = player.getLocation().getChunk();

            StringBuilder chunks0 = new StringBuilder();
            for(int i = 12; i >= 1; i--){
                chunks0.append(a(player.getWorld().getChunkAt(playerChunk.getX(), playerChunk.getZ() + i)));
            }
            chunks0.append(ChunkManager.getColoredStringPlayerChunk(playerChunk));

            for(int i = 1; i <= 12; i++){
                chunks0.append(a(player.getWorld().getChunkAt(playerChunk.getX(), playerChunk.getZ() - i)));
            }


            StringBuilder chunks1 = new StringBuilder();
            for(int i = 12; i >= 1; i--){
                chunks1.append(a(player.getWorld().getChunkAt(playerChunk.getX() + 1, playerChunk.getZ() + i)));
            }

            for(int i = 0; i <= 12; i++){
                chunks1.append(a(player.getWorld().getChunkAt(playerChunk.getX() + 1, playerChunk.getZ() - i)));
            }


            StringBuilder chunks2 = new StringBuilder();
            for(int i = 12; i >= 1; i--){
                chunks2.append(a(player.getWorld().getChunkAt(playerChunk.getX() + 2, playerChunk.getZ() + i)));
            }

            for(int i = 0; i <= 12; i++){
                chunks2.append(a(player.getWorld().getChunkAt(playerChunk.getX() + 2, playerChunk.getZ() - i)));
            }


            StringBuilder chunks3 = new StringBuilder();
            for(int i = 12; i >= 1; i--){
                chunks3.append(a(player.getWorld().getChunkAt(playerChunk.getX() + 3, playerChunk.getZ() + i)));
            }

            for(int i = 0; i <= 12; i++){
                chunks3.append(a(player.getWorld().getChunkAt(playerChunk.getX() + 3, playerChunk.getZ() - i)));
            }


            StringBuilder chunks4 = new StringBuilder();
            for(int i = 12; i >= 1; i--){
                chunks4.append(a(player.getWorld().getChunkAt(playerChunk.getX() + 4, playerChunk.getZ() + i)));
            }

            for(int i = 0; i <= 12; i++){
                chunks4.append(a(player.getWorld().getChunkAt(playerChunk.getX() + 4, playerChunk.getZ() - i)));
            }


            StringBuilder chunks6 = new StringBuilder();
            for(int i = 12; i >= 1; i--){
                chunks6.append(a(player.getWorld().getChunkAt(playerChunk.getX() - 1, playerChunk.getZ() + i)));
            }

            for(int i = 0; i <= 12; i++){
                chunks6.append(a(player.getWorld().getChunkAt(playerChunk.getX() - 1, playerChunk.getZ() - i)));
            }


            StringBuilder chunks7 = new StringBuilder();
            for(int i = 12; i >= 1; i--){
                chunks7.append(a(player.getWorld().getChunkAt(playerChunk.getX() - 2, playerChunk.getZ() + i)));
            }

            for(int i = 0; i <= 12; i++){
                chunks7.append(a(player.getWorld().getChunkAt(playerChunk.getX() - 2, playerChunk.getZ() - i)));
            }


            StringBuilder chunks8 = new StringBuilder();
            for(int i = 12; i >= 1; i--){
                chunks8.append(a(player.getWorld().getChunkAt(playerChunk.getX() - 3, playerChunk.getZ() + i)));
            }

            for(int i = 0; i <= 12; i++){
                chunks8.append(a(player.getWorld().getChunkAt(playerChunk.getX() - 3, playerChunk.getZ() - i)));
            }


            StringBuilder chunks9 = new StringBuilder();
            for(int i = 12; i >= 1; i--){
                chunks9.append(a(player.getWorld().getChunkAt(playerChunk.getX() - 4, playerChunk.getZ() + i)));
            }

            for(int i = 0; i <= 12; i++){
                chunks9.append(a(player.getWorld().getChunkAt(playerChunk.getX() - 4, playerChunk.getZ() - i)));
            }


            String map = "§7§l------------------------------------" + "\n"
                    + chunks9 + "\n"
                    + chunks8 + "\n"
                    + chunks7 + "\n"
                    + chunks6 + "\n"
                    + chunks0 + "\n"
                    + chunks1 + "\n"
                    + chunks2 + "\n"
                    + chunks3 + "\n"
                    + chunks4 + "\n"
                    + "§7§l------------------------------------";
            player.sendMessage(map);
            return false;
        }
        return false;
    }

    private void helpMessage(Player player){
        player.sendMessage(ValkyaClaims.getPREFIX() + " §e/claims create");
        player.sendMessage(ValkyaClaims.getPREFIX() + " §e/claims delete");
        player.sendMessage(ValkyaClaims.getPREFIX() + " §e/claims map");
    }

    private String a(Chunk chunk){
        return ChunkManager.getColoredStringChunk(chunk);
    }
}
