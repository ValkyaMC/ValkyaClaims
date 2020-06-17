package fr.volax.valkyaclaims.events;

import fr.volax.valkyaclaims.ValkyaClaims;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class EventManager {
    public static void register(ValkyaClaims instance) {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new ChunkListener(), instance);
        pm.registerEvents(new ConnectionListener(), instance);
    }
}
