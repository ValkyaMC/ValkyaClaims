package fr.volax.valkyaclaims.chunk;

import fr.volax.valkyaclaims.tool.ConfigBuilder;
import fr.volax.valkyaclaims.tool.ConfigType;
import org.bukkit.Chunk;

public class ChunkManager {
    public static boolean doesChunkIsRegistered(Chunk chunk){
        return ConfigBuilder.getCInt("chunks.chunk_" + chunk.getX() + "_" + chunk.getZ() + ".X", ConfigType.CHUNKS) != 0 || ConfigBuilder.getCInt("chunks.chunk_" + chunk.getX() + "_" + chunk.getZ() + ".Z", ConfigType.CHUNKS) != 0;
    }

    public static void registerNewChunk(Chunk chunk){
        int X = chunk.getX();
        int Z = chunk.getZ();
        String worldName = chunk.getWorld().getName();

        ConfigBuilder.setCInt("chunks.chunk_" + X + "_" + Z + ".X", X, ConfigType.CHUNKS);
        ConfigBuilder.setCInt("chunks.chunk_" + X + "_" + Z + ".Z", Z, ConfigType.CHUNKS);
        ConfigBuilder.setCString("chunks.chunk_" + X + "_" + Z + ".WorldName", worldName, ConfigType.CHUNKS);
        ConfigBuilder.setCString("chunks.chunk_" + X + "_" + Z + ".STATEMENT", ChunkType.STAFFED.getName(), ConfigType.CHUNKS);
    }

    public static void deleteChunk(Chunk chunk){
        int X = chunk.getX();
        int Z = chunk.getZ();

        ConfigBuilder.setCString("chunks.chunk_" + X + "_" + Z + ".X", null, ConfigType.CHUNKS);
        ConfigBuilder.setCString("chunks.chunk_" + X + "_" + Z + "", null, ConfigType.CHUNKS);
        ConfigBuilder.setCString("chunks.chunk_" + X + "_" + Z + ".Z", null, ConfigType.CHUNKS);
        ConfigBuilder.setCString("chunks.chunk_" + X + "_" + Z + ".WorldName", null, ConfigType.CHUNKS);
        ConfigBuilder.setCString("chunks.chunk_" + X + "_" + Z + ".STATEMENT", null, ConfigType.CHUNKS);
    }

    public static String getStatementChunk(Chunk chunk){
       return ConfigBuilder.getCString("chunks.chunk_" + chunk.getX() + "_" + chunk.getZ() + ".STATEMENT", ConfigType.CHUNKS);
    }

    public static String getColoredStringChunk(Chunk chunk){
        if(!doesChunkIsRegistered(chunk)) return "§7■";
        if(getStatementChunk(chunk).equalsIgnoreCase("CLAIMED")) return "§a■";
        if(getStatementChunk(chunk).equalsIgnoreCase("STAFF")) return "§b■";
        if(getStatementChunk(chunk).equalsIgnoreCase("BLOCKED")) return "§0■";
        return "§7■";
    }

    public static String getColoredStringPlayerChunk(Chunk chunk){
        if(!doesChunkIsRegistered(chunk)) return "§7§l+";
        if(getStatementChunk(chunk).equalsIgnoreCase("CLAIMED")) return "§a§l+";
        if(getStatementChunk(chunk).equalsIgnoreCase("STAFF")) return "§b§l+";
        if(getStatementChunk(chunk).equalsIgnoreCase("BLOCKED")) return "§0§l+";
        return "§7§l+";
    }
}
