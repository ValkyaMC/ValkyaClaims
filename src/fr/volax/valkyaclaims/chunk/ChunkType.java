package fr.volax.valkyaclaims.chunk;

public enum ChunkType {
    CLAIMED("CLAIMED"),
    STAFFED("STAFF"),
    BLOCKED("BLOCKED");

    private String name;

    ChunkType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
