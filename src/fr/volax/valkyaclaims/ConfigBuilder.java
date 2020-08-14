package fr.volax.valkyaclaims;


/**
 * ConfigBuilder pour gérer les configs
 *
 * @author Volax
 * @see ConfigType
 */
public class ConfigBuilder {
    public static FileManager configs = new FileManager(ValkyaClaims.getInstance());

    /**
     * Return un String dans une config custom
     *
     * @param path  La direction du string à get
     * @param config Config dans la quel il faut get la value
     * @return Contenue de "value" dans la config
     */
    public static String getCString(String path, ConfigType config) {
        return configs.getConfig(config.getConfigName()).get().getString(path).replaceAll("&", "§").replaceAll("%prefix%", ValkyaClaims.getPREFIX());
    }

    /**
     * Return un int dans une config custom
     *
     * @param path  La direction du int à get
     * @param config Config dans la quel il faut get la value
     * @return Contenue de "value" dans la config
     */
    public static int getCInt(String path, ConfigType config) {
        return configs.getConfig(config.getConfigName()).get().getInt(path);
    }

    /**
     * Return un Boolean dans une config custom
     *
     * @param path  La direction du boolean à get
     * @param config Config dans la quel il faut get la value
     * @return Contenue de "value" dans la config
     */
    public static Boolean getCBool(String path, ConfigType config) {
        return configs.getConfig(config.getConfigName()).get().getBoolean(path);
    }

    /**
     * Modifier une donnée dans une config custom
     *
     * @param path  La direction de la value à modifier
     * @param data   String à modifier dans la config
     * @param config Config dans la quel il faut modifier la value
     */
    public static void setCString(String path, String data, ConfigType config) {
        configs.getConfig(config.getConfigName()).set(path, data);
        configs.getConfig(config.getConfigName()).save();
    }

    /**
     * Modifier une donnée dans une config custom
     *
     * @param path  La direction de la value à modifier
     * @param data   int à modifier dans la config
     * @param config Config dans la quel il faut modifier la value
     */
    public static void setCInt(String path, int data, ConfigType config) {
        configs.getConfig(config.getConfigName()).set(path, data);
        configs.getConfig(config.getConfigName()).save();
        configs.getConfig(config.getConfigName()).reload();
    }

    /**
     * Modifier une donnée dans une config custom
     *
     * @param path  La direction de la value à modifier
     * @param data   Boolean à modifier dans la config
     * @param config Config dans la quel il faut modifier la value
     */
    public static void setCBool(String path, boolean data, ConfigType config) {
        configs.getConfig(config.getConfigName()).set(path, data);
        configs.getConfig(config.getConfigName()).save();
    }

    /**
     * Return un String dans la config principal
     *
     * @param path La direction du string à get
     * @return Contenue de "value" dans la config
     */
    public static String getString(String path) {
        return ValkyaClaims.getInstance().getConfig().getString(path).replaceAll("&", "§").replaceAll("%prefix%", ValkyaClaims.getPREFIX());
    }

    /**
     * Return un int dans la config principal
     *
     * @param path La direction du int à get
     * @return Contenue de "path" dans la config
     */
    public static int getInt(String path) {
        return ValkyaClaims.getInstance().getConfig().getInt(path);
    }

    /**
     * Return un Boolean dans la config principal
     *
     * @param path La direction du boolean à get
     * @return Contenue de "path" dans la config
     */
    public static boolean getBoolean(String path) {
        return ValkyaClaims.getInstance().getConfig().getBoolean(path);
    }

    /**
     * Modifier une donnée dans la config principale
     *
     * @param path La direction de la donnée à modifier
     * @param data  Donnée à modifier dans la config
     */
    public static void set(String path, String data) {
        ValkyaClaims.getInstance().getConfig().set(path, data);
        ValkyaClaims.getInstance().saveConfig();
        ValkyaClaims.getInstance().reloadConfig();
    }

    /**
     * Tout les configs custom
     *
     * @author Volax
     * @see ConfigBuilder
     */
    public enum ConfigType {
        CHUNKS("chunks.yml");
        public String configName;
        ConfigType(String configName) {
            this.configName = configName;
        }
        public String getConfigName() {
            return configName;
        }
    }
}
