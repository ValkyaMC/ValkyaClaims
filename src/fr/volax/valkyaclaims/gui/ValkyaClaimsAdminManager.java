package fr.volax.valkyaclaims.gui;

import fr.volax.valkyaclaims.tool.GuiBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ValkyaClaimsAdminManager implements GuiBuilder {
    //TODO this
    @Override
    public String name() {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public void contents(Player player, Inventory inv) {

    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {

    }
}