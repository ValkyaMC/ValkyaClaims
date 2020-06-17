package fr.volax.valkyaclaims.events;

import fr.volax.valkyaclaims.ValkyaClaims;
import fr.volax.valkyaclaims.player.ChunkPlayer;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ChunkListener implements Listener {
    @EventHandler
    public void onNewChunk(PlayerMoveEvent event){
        ChunkPlayer chunkPlayer = ValkyaClaims.getInstance().getChunkPlayerManager().getChunkPlayer(event.getPlayer().getUniqueId());
        Player player = event.getPlayer();
        Chunk chunk = player.getLocation().getChunk();

        if(chunkPlayer.getLastChunk() != chunk) chunkPlayer.setLastChunk(chunk);
    }
}
