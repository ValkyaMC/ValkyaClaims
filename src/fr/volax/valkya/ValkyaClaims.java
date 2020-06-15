package fr.volax.valkya;

import fr.volax.valkya.commands.CommandManager;
import fr.volax.valkya.events.EventManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ValkyaClaims extends JavaPlugin {
    private static ValkyaClaims instance;

    @Override
    public void onEnable() {
        instance = this;

        CommandManager.register(this);
        EventManager.register(this);
    }

    public static ValkyaClaims getInstance() {
        return instance;
    }
}
