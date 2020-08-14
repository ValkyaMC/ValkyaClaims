package fr.volax.valkyaclaims;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * Gérer les events des panneaux pour acheter des AP/Créer les panneaux pour acheter des AP
 *
 * @author Volax
 * @version 1.0
 */
public class SignListener implements Listener {
    @EventHandler
    public void OnSignCreate(SignChangeEvent e){
        Block b = e.getBlock();
        Material blockType = b.getType();
        Sign sign = (Sign) b.getState();

        if((blockType.equals(Material.SIGN)) || (blockType.equals(Material.SIGN_POST)) || (blockType.equals(Material.WALL_SIGN))){
            if(e.getLine(0).equalsIgnoreCase("[ValkyaAP]")){
                if(e.getPlayer().hasPermission("valkya.ap.createsign")){
                    if(e.getLine(1) == null || e.getLine(1).equals(" ")
                            || !e.getLine(1).equalsIgnoreCase("NORD")
                            || !e.getLine(1).equalsIgnoreCase("SUD")
                            || !e.getLine(1).equalsIgnoreCase("OUEST")
                            || !e.getLine(1).equalsIgnoreCase("EST")
                            || e.getLine(2) == null || e.getLine(2).equals(" ")
                            || ValkyaClaims.getInstance().isInt(e.getLine(2))){
                        e.getPlayer().sendMessage(ValkyaClaims.getPREFIX() + " §eSetup une pancarte: \n" + ValkyaClaims.getPREFIX() + " §eLigne 1 : [ValkyaAP]\n" + ValkyaClaims.getPREFIX() + " §eLigne 2: La sortie de l'AP (NORD|SUD|EST|OUEST)\n" + ValkyaClaims.getPREFIX() + " §eLigne 3: Le prix de l'AP");
                        e.setCancelled(true);
                        b.breakNaturally();
                        return;
                    }
                    String sortie = e.getLine(1);
                    int price = Integer.parseInt(e.getLine(2));
                    e.getPlayer().sendMessage(ValkyaClaims.getPREFIX() + " §eVous avez créer avec succès une pancarte AP à la sortie §6" + sortie + " §epour le prix de §6" + price);
                    e.setLine(0, "§6ValkyaAP");
                    e.setLine(1, "§e" + sortie);
                    e.setLine(2, "§e" + price);
                    e.setLine(3, "§6=-=-=-=-=-=-=-=-");
                }
            }
        }
    }
}
