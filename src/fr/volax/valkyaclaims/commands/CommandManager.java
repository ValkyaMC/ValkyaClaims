package fr.volax.valkyaclaims.commands;

import fr.volax.valkyaclaims.ValkyaClaims;

public class CommandManager {
    public static void register(ValkyaClaims instance) {
        new CommandClaims("claims");
    }
}
