package fr.volax.valkyaclaims;

import com.massivecraft.factions.*;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChunkManager {

    public static void buyChunk(Chunk chunk, Player player){
        int id = getChunkID(chunk);
        Chunk secondChunk = getSecondChunk(getDirection(id), chunk);

        Board.getInstance().removeAt(new FLocation(chunk.getWorld().getName(), chunk.getX(), chunk.getZ()));
        Board.getInstance().removeAt(new FLocation(secondChunk.getWorld().getName(), secondChunk.getX(), secondChunk.getZ()));
        FPlayer fPlayer =  FPlayers.getInstance().getByPlayer(player);

        fPlayer.attemptClaim(fPlayer.getFaction(), new FLocation(chunk.getWorld().getName(), chunk.getX(), chunk.getZ()), true);
        fPlayer.attemptClaim(fPlayer.getFaction(), new FLocation(secondChunk.getWorld().getName(), secondChunk.getX(), secondChunk.getZ()), true);

        changeAPFaction(id, FPlayers.getInstance().getByPlayer(player).getFaction().getTag());
        player.sendMessage(ValkyaClaims.getPREFIX() + " §eVous venez d'acheter votre AP n°§6" + getNumberAP(FPlayers.getInstance().getByPlayer(player).getFaction().getTag()) + " §e!");
    }

    public static void registerChunk(Chunk chunk, String direction, int price, Player player){
        Chunk secondChunk = getSecondChunk(direction, chunk);

        FPlayers.getInstance().getByPlayer(player).getFaction().setPowerBoost(FPlayers.getInstance().getByPlayer(player).getFaction().getPowerBoost() + 20);
        FPlayers.getInstance().getByOfflinePlayer(Bukkit.getOfflinePlayer("VolaxYT")).attemptClaim(Factions.getInstance().getByTag("APs"), new FLocation(chunk.getWorld().getName(), chunk.getX(), chunk.getZ()), true);
        FPlayers.getInstance().getByOfflinePlayer(Bukkit.getOfflinePlayer("VolaxYT")).attemptClaim(Factions.getInstance().getByTag("APs"), new FLocation(secondChunk.getWorld().getName(), secondChunk.getX(), secondChunk.getZ()), true);

        try {
            PreparedStatement insert = ValkyaClaims.getInstance().sql.connection.prepareStatement("INSERT INTO ap (chunk1_X, chunk1_Z, chunk2_X, chunk2_Z, price, faction, direction) VALUES (?,?,?,?,?,?,?)");
            insert.setInt(1, chunk.getX());
            insert.setInt(2, chunk.getZ());
            insert.setInt(3, secondChunk.getX());
            insert.setInt(4, secondChunk.getZ());
            insert.setInt(5, price);
            insert.setString(6, "none");
            insert.setString(7, direction);
            insert.executeUpdate();
            insert.close();
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("L'AP n'a pas d'informations dans la BDD !");
    }

    public static void registerChunk(Chunk chunk, String direction, int price){
        Chunk secondChunk = getSecondChunk(direction, chunk);

        FPlayers.getInstance().getByOfflinePlayer(Bukkit.getOfflinePlayer("VolaxYT")).attemptClaim(Factions.getInstance().getByTag("APs"), new FLocation(chunk.getWorld().getName(), chunk.getX(), chunk.getZ()), true);
        FPlayers.getInstance().getByOfflinePlayer(Bukkit.getOfflinePlayer("VolaxYT")).attemptClaim(Factions.getInstance().getByTag("APs"), new FLocation(secondChunk.getWorld().getName(), secondChunk.getX(), secondChunk.getZ()), true);

        try {
            PreparedStatement insert = ValkyaClaims.getInstance().sql.connection.prepareStatement("INSERT INTO ap (chunk1_X, chunk1_Z, chunk2_X, chunk2_Z, price, faction, direction) VALUES (?,?,?,?,?,?,?)");
            insert.setInt(1, chunk.getX());
            insert.setInt(2, chunk.getZ());
            insert.setInt(3, secondChunk.getX());
            insert.setInt(4, secondChunk.getZ());
            insert.setInt(5, price);
            insert.setString(6, "none");
            insert.setString(7, direction);
            insert.executeUpdate();
            insert.close();
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("L'AP n'a pas d'informations dans la BDD !");
    }

    public static void deleteChunk(Chunk chunk, Player player){
        int id = getChunkID(chunk);
        String direction = getDirection(id);
        int price = getPrice(id);
        Chunk secondChunk = getSecondChunk(getDirection(id), chunk);
        FPlayer fPlayer =  FPlayers.getInstance().getByPlayer(player);
        Faction faction = fPlayer.getFaction();
        if(!player.isOp())
            faction.setPowerBoost(faction.getPowerBoost() - 20);

        Board.getInstance().removeAt(new FLocation(chunk.getWorld().getName(), chunk.getX(), chunk.getZ()));
        Board.getInstance().removeAt(new FLocation(secondChunk.getWorld().getName(), secondChunk.getX(), secondChunk.getZ()));

        registerChunk(chunk, direction, price);
        try {
            PreparedStatement query = ValkyaClaims.getInstance().sql.connection.prepareStatement("DELETE FROM ap WHERE id=?");
            query.setInt(1, id);
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void noReverseChunk(Chunk chunk){
        int id = getChunkID(chunk);
        Chunk secondChunk = getSecondChunk(getDirection(id), chunk);

        Board.getInstance().removeAt(new FLocation(chunk.getWorld().getName(), chunk.getX(), chunk.getZ()));
        Board.getInstance().removeAt(new FLocation(secondChunk.getWorld().getName(), secondChunk.getX(), secondChunk.getZ()));

        FPlayers.getInstance().getByOfflinePlayer(Bukkit.getOfflinePlayer("VolaxYT")).attemptClaim(Factions.getInstance().getWarZone(), new FLocation(chunk.getWorld().getName(), chunk.getX(), chunk.getZ()), true);
        FPlayers.getInstance().getByOfflinePlayer(Bukkit.getOfflinePlayer("VolaxYT")).attemptClaim(Factions.getInstance().getWarZone(), new FLocation(secondChunk.getWorld().getName(), secondChunk.getX(), secondChunk.getZ()), true);

        try {
            PreparedStatement query = ValkyaClaims.getInstance().sql.connection.prepareStatement("DELETE FROM ap WHERE id=?");
            query.setInt(1, id);
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getChunkID(Chunk chunk){
        try{
            PreparedStatement insert = ValkyaClaims.getInstance().sql.connection.prepareStatement("SELECT ID FROM ap WHERE chunk1_X=? AND chunk1_Z=?");
            insert.setInt(1, chunk.getX());
            insert.setInt(2, chunk.getZ());

            ResultSet rs = insert.executeQuery();
            if(rs.next())
                return rs.getInt("id");
        } catch (SQLException ignored) {}
        return 0;
    }

    public static Faction getFactionInAP(int id){
        try{
            PreparedStatement insert = ValkyaClaims.getInstance().sql.connection.prepareStatement("SELECT faction FROM ap WHERE id=?");
            insert.setInt(1, id);

            ResultSet rs = insert.executeQuery();

            if(rs.next()){
                return Factions.getInstance().getFactionById(rs.getString("faction"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new NullPointerException("L'AP n'a pas d'informations dans la BDD !");
    }

    public static String getDirection(int id){
        try{
            PreparedStatement insert = ValkyaClaims.getInstance().sql.connection.prepareStatement("SELECT direction FROM ap WHERE id=?");
            insert.setInt(1, id);
            ResultSet rs = insert.executeQuery();

            if(rs.next()) return rs.getString("direction");
        } catch (SQLException throwables) { throwables.printStackTrace(); }
        return null;
    }

    public static int getPrice(int id){
        try{
            PreparedStatement insert = ValkyaClaims.getInstance().sql.connection.prepareStatement("SELECT price FROM ap WHERE id=?");
            insert.setInt(1, id);
            ResultSet rs = insert.executeQuery();

            if(rs.next()) return rs.getInt("price");
        } catch (SQLException throwables) { throwables.printStackTrace(); }
        throw new NullPointerException("L'AP n'a pas d'informations dans la BDD !");
    }

    public static Chunk getSecondChunk(String direction, Chunk firstChunk){
        Chunk secondChunkkk = null;
        switch (direction){
            case "NORD": secondChunkkk = firstChunk.getWorld().getChunkAt(firstChunk.getX(), firstChunk.getZ() - 1); break;
            case "SUD": secondChunkkk = firstChunk.getWorld().getChunkAt(firstChunk.getX(), firstChunk.getZ() + 1); break;
            case "EST": secondChunkkk = firstChunk.getWorld().getChunkAt(firstChunk.getX() + 1, firstChunk.getZ()); break;
            case "OUEST": secondChunkkk = firstChunk.getWorld().getChunkAt(firstChunk.getX() - 1, firstChunk.getZ()); break;
            default:break;
        }
        return secondChunkkk;
    }

    public static void changeAPFaction(int id, String factionName) {
        try {
            PreparedStatement update = ValkyaClaims.getInstance().sql.connection.prepareStatement("UPDATE ap SET faction=? WHERE id=?");
            update.setString(1, factionName);
            update.setInt(2, id);
            update.executeUpdate();
            update.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void createAPFaction(){
        if(Factions.getInstance().getAllFactions().contains(Factions.getInstance().getByTag("APs"))) return;
        Faction faction = Factions.getInstance().createFaction();
        faction.setTag("APs");
        faction.setDescription("Auto Faction for AP's");
        faction.setOpen(false);
        faction.setPermanent(true);
        faction.setPermanentPower(Integer.MAX_VALUE);
        System.out.println("[Valkya-Logger] Création de la Faction APs");
    }

    public static int getNumberAP(String factionName){
        try{
            PreparedStatement query = ValkyaClaims.getInstance().sql.connection.prepareStatement("SELECT price FROM ap WHERE faction=?");
            query.setString(1, factionName);
            ResultSet rs = query.executeQuery();
            int i = 0;
            while(rs.next()) i++;
            return i;
        }catch (SQLException e){ e.printStackTrace();}
        return 0;
    }

    public static boolean isFree(int id){
        try{
            PreparedStatement insert = ValkyaClaims.getInstance().sql.connection.prepareStatement("SELECT price FROM ap WHERE id=?");
            insert.setInt(1, id);
            ResultSet rs = insert.executeQuery();
            if(rs.next())
                return rs.getInt("price") == 0;
        } catch (SQLException throwables) { throwables.printStackTrace(); }
        throw new NullPointerException("L'AP n'a pas d'informations dans la BDD !");
    }
}
