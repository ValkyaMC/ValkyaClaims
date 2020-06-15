package fr.volax.valkya.commands;

import fr.volax.valkya.ValkyaClaims;

public class CommandManager {
    public static void register(ValkyaClaims instance) {
        new CommandAP(instance, "ap");
    }
}
