package fr.volax.valkyaclaims.events;

import fr.volax.valkyaclaims.ValkyaClaims;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        ValkyaClaims.getInstance().getChunkPlayerManager().newChunkPlayer(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        ValkyaClaims.getInstance().getChunkPlayerManager().removeUhcPlayer(ValkyaClaims.getInstance().getChunkPlayerManager().getChunkPlayer(event.getPlayer().getUniqueId()));
    }
}
