package fr.volax.valkyaclaims.player;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ChunkPlayer {
    private String name;
    private Chunk lastChunk;
    private Player player;
    private UUID uuid;

    ChunkPlayer(Player player) {
        this.player = player;
        this.name = player.getName();
        this.uuid = player.getUniqueId();
    }

    public Player getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Chunk getLastChunk() {
        return lastChunk;
    }

    public void setLastChunk(Chunk lastChunk) {
        this.lastChunk = lastChunk;
    }
}
