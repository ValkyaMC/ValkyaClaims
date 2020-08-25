package fr.volax.valkyaclaims;

import fr.volax.volaxapi.tool.database.Database;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Valkya Claims for Valkya PvP Faction Modded
 * @author Volax
 */
public class ValkyaClaims extends JavaPlugin {
    private static ValkyaClaims instance;
    public static Economy economy;
    public Database sql;

    private static final String PREFIX = "§6Valkya »";

    @Override
    public void onEnable() {
        if(!setupEconomy()) Bukkit.shutdown();
        instance = this;

        ChunkManager.createAPFaction();

        Bukkit.getPluginManager().registerEvents(new SignListener(), instance);
        new APCommand("ap");
        saveDefaultConfig();

        sql = new Database("jdbc:mysql://", ConfigBuilder.getString("sql.host"), ConfigBuilder.getString("sql.database"), ConfigBuilder.getString("sql.user"), ConfigBuilder.getString("sql.pass"));
        sql.connection();
    }

    public static ValkyaClaims getInstance() {
        return instance;
    }
    public static String getPREFIX() { return PREFIX; }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null)
            economy = economyProvider.getProvider();
        return (economy != null);
    }

    public boolean isInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) { return false; }
    }
}
