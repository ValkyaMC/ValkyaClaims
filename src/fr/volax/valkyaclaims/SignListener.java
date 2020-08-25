package fr.volax.valkyaclaims;

import com.massivecraft.factions.*;
import com.massivecraft.factions.event.FactionDisbandEvent;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

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
        Player player = e.getPlayer();
        if((blockType.equals(Material.SIGN)) || (blockType.equals(Material.SIGN_POST)) || (blockType.equals(Material.WALL_SIGN))){
            if(e.getLine(0).equalsIgnoreCase("ValkyaAP")){
                if(e.getPlayer().hasPermission("valkya.ap.createsign")){
                    if(e.getLine(1) == null || e.getLine(1).equals(" ")
                            || e.getLine(2) == null || e.getLine(2).equals(" ")
                            || !ValkyaClaims.getInstance().isInt(e.getLine(2))){
                        e.getPlayer().sendMessage(ValkyaClaims.getPREFIX() + " §eSetup une pancarte: \n" + ValkyaClaims.getPREFIX() + " §eLigne 1 : [ValkyaAP]\n" + ValkyaClaims.getPREFIX() + " §eLigne 2: La sortie de l'AP (NORD|SUD|EST|OUEST)\n" + ValkyaClaims.getPREFIX() + " §eLigne 3: Le prix de l'AP");
                        e.setCancelled(true);
                        b.breakNaturally();
                        return;
                    }
                    String sortie = e.getLine(1);
                    int price = Integer.parseInt(e.getLine(2));
                    e.setLine(0, "§6ValkyaAP");
                    e.setLine(1, "§e" + sortie);
                    e.setLine(2, price == 0 ? "§eGratuit" : "§e" + price);
                    e.setLine(3, "§6=-=-=-=-=-=-=-=-");

                    Chunk chunk = sign.getChunk();
                    switch (sortie){
                        case "NORD":
                        case "SUD":
                        case "EST":
                        case "OUEST":
                            ChunkManager.registerChunk(chunk, sortie, price, player);
                            e.getPlayer().sendMessage(ValkyaClaims.getPREFIX() + " §eVous avez créer avec succès une pancarte AP à la sortie §6" + sortie + " §epour le prix de §6" + price);
                            break;
                        default:
                            e.getPlayer().sendMessage(ValkyaClaims.getPREFIX() + " §eSetup une pancarte: \n" + ValkyaClaims.getPREFIX() + " §eLigne 1 : [ValkyaAP]\n" + ValkyaClaims.getPREFIX() + " §eLigne 2: La sortie de l'AP (NORD|SUD|EST|OUEST)\n" + ValkyaClaims.getPREFIX() + " §eLigne 3: Le prix de l'AP");
                            e.setCancelled(true);
                            b.breakNaturally();
                            break;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Block b = event.getBlock();
        Material blockType = b.getType();
        if(blockType != Material.SIGN_POST && blockType != Material.WALL_SIGN) return;
        Sign sign = (Sign) b.getState();
        if(sign.getLine(0).equals("§6ValkyaAP"))
            if(event.getPlayer().isOp())
                ChunkManager.deleteChunk(b.getChunk(), event.getPlayer());
            else
                event.setCancelled(true);
    }

    @EventHandler
    public void onDisband(FactionDisbandEvent event){
        if(event.getFaction().getTag().equals("APs")){
            event.setCancelled(true);
            event.getPlayer().sendMessage(ValkyaClaims.getPREFIX() + " §eVous ne pouvez pas disband cette faction");
        }else{
            if(ChunkManager.getNumberAP(event.getFaction().getTag()) > 0){
                event.setCancelled(true);
                event.getPlayer().sendMessage(ValkyaClaims.getPREFIX() + " §eVous ne pouvez pas disband cette faction tant que vous avez des AP acheté !");
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        if(event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getClickedBlock().getType() == Material.SIGN_POST || event.getClickedBlock().getType() == Material.WALL_SIGN){
                System.out.println(ConfigBuilder.getBoolean("isOpen"));
                if(!ConfigBuilder.getBoolean("isOpen")){
                    event.getPlayer().sendMessage(ValkyaClaims.getPREFIX() + " §eUnClaim des AP - Mercredi 16H");
                    return;
                }

                Sign sign = (Sign) event.getClickedBlock().getState();
                Chunk chunk = sign.getChunk();
                Player player = event.getPlayer();
                FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
                Faction faction = Factions.getInstance().getFactionById(fplayer.getFactionId());
                if (faction.getId().equalsIgnoreCase(Factions.getInstance().getWilderness().getId())) {
                    player.sendMessage(ValkyaClaims.getPREFIX() + " §eVous devez être dans une faction pour pouvoir acheter un Avant Poste !");
                } else {
                    if (fplayer != faction.getFPlayerAdmin()) {
                        player.sendMessage(ValkyaClaims.getPREFIX() + " §eVous devez être le chef de votre faction pour pouvoir acheter un Avant Poste !");
                        return;
                    }
                    if(!Board.getInstance().getFactionAt(new FLocation(chunk.getWorld().getName(), chunk.getX(), chunk.getZ())).getId().equals(Factions.getInstance().getByTag("APS").getId())){
                        player.sendMessage(ValkyaClaims.getPREFIX() + " §eCette AP est déjà acheté !");
                        return;
                    }
                    if(ChunkManager.getNumberAP(faction.getTag()) >= 3){
                        player.sendMessage(ValkyaClaims.getPREFIX() + " §eVous posséder déjà 3 AP !");
                        return;
                    }

                    int balance = (int) ValkyaClaims.economy.getBalance(player);
                    boolean isFree = ChunkManager.isFree(ChunkManager.getChunkID(chunk));
                    if(!isFree){
                        int price = Integer.parseInt(sign.getLine(2).substring(2));
                        if(balance < price){
                            player.sendMessage(ValkyaClaims.getPREFIX() + " §eVous n'avez pas assez d'argent pour acheter cette AP !");
                            return;
                        }

                        ValkyaClaims.economy.withdrawPlayer(player, price);
                    }
                    ChunkManager.buyChunk(chunk, player);
                }
            }
        }
    }
}
