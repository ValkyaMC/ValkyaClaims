package fr.volax.valkyaclaims;

import fr.volax.valkyaclaims.commands.CommandManager;
import fr.volax.valkyaclaims.events.EventManager;
import fr.volax.valkyaclaims.gui.GuiManager;
import fr.volax.valkyaclaims.gui.ValkyaClaimsAdminManager;
import fr.volax.valkyaclaims.player.ChunkPlayerManager;
import fr.volax.valkyaclaims.tool.ConfigBuilder;
import fr.volax.valkyaclaims.tool.GuiBuilder;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Valkya Claims for Valkya PvP Faction Modded
 * @author Volax
 */
public class ValkyaClaims extends JavaPlugin {
    private static ValkyaClaims instance;
    private GuiManager guiManager;
    private ChunkPlayerManager chunkPlayerManager;

    private Map<Class<? extends GuiBuilder>, GuiBuilder> registeredMenus;
    private static final String PREFIX = "§6Valkya »";

    @Override
    public void onEnable() {
        instance = this;
        guiManager = new GuiManager();
        chunkPlayerManager = new ChunkPlayerManager();

        registeredMenus = new HashMap<>();

        CommandManager.register(this);
        EventManager.register(this);

        guiManager.addMenu(new ValkyaClaimsAdminManager());


        saveDefaultConfig();
        ConfigBuilder.configs.getConfig("chunks.yml").saveDefaultConfig();
    }

    public static ValkyaClaims getInstance() {
        return instance;
    }
    public Map<Class<? extends GuiBuilder>, GuiBuilder> getRegisteredMenus() {
        return registeredMenus;
    }
    public static String getPREFIX() { return PREFIX; }
    public GuiManager getGuiManager() {
        return guiManager;
    }

    public ChunkPlayerManager getChunkPlayerManager() {
        return chunkPlayerManager;
    }
}
