package fr.volax.valkyaclaims;

public class ConfigBuilder {
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
}
