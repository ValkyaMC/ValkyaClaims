package fr.volax.valkyaclaims.player;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ChunkPlayerManager {
    private List<ChunkPlayer> players;

    public ChunkPlayerManager() {
        players = Collections.synchronizedList(new ArrayList<>());
    }

    public boolean doesPlayerExist(Player player){
        return getChunkPlayer(player.getUniqueId()) != null;
    }

    public ChunkPlayer getChunkPlayer(Player player){
        return getChunkPlayer(player.getUniqueId());
    }

    public ChunkPlayer getChunkPlayer(String name){
        for(ChunkPlayer chunkPlayer : getPlayersList()){
            if(chunkPlayer.getName().equals(name)) {
                return chunkPlayer;
            }
        }
        return null;
    }

    public ChunkPlayer getChunkPlayer(UUID uuid){
        for(ChunkPlayer chunkPlayer : getPlayersList()){
            if(chunkPlayer.getUuid().equals(uuid)) {
                return chunkPlayer;
            }
        }
        return null;
    }

    public synchronized List<ChunkPlayer> getPlayersList(){
        return players;
    }

    public synchronized ChunkPlayer newChunkPlayer(Player player){
        ChunkPlayer newPlayer = new ChunkPlayer(player);
        getPlayersList().add(newPlayer);
        return newPlayer;
    }

    public void removeUhcPlayer(ChunkPlayer uhcPlayer){
        getPlayersList().remove(uhcPlayer);
    }

}
