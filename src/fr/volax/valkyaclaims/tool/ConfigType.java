package fr.volax.valkyaclaims.tool;

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
