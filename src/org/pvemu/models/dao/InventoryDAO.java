package org.pvemu.models.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.pvemu.game.objects.item.ItemPosition;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.database.DAO;
import org.pvemu.jelly.database.Database;
import org.pvemu.models.InventoryEntry;

public class InventoryDAO extends DAO<InventoryEntry> {

    private PreparedStatement getByOwnerStatement = null;
    private PreparedStatement createStatement = null;
    private PreparedStatement updateStatement = null;
    private PreparedStatement getAccessoriesByPlayerIdStatement = null;

    public InventoryDAO() {
        getByOwnerStatement = Database.prepare("SELECT * FROM inventory_entries WHERE owner = ? AND owner_type = ?");
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT t.id AS aID, e.position AS aPOS FROM inventory_entries e ")
                .append("JOIN item_templates t ON e.item_id = t.id ")
                .append("WHERE position IN(");
        
        for(ItemPosition pos : ItemPosition.getAccessoriePositions()){
            for(byte p : pos.getPosIds()){
                query.append(p).append(',');
            }
        }
        
        query.setLength(query.length() - 1);
                        
        query.append(") AND owner = ? AND owner_type = 1");
        getAccessoriesByPlayerIdStatement = Database.prepare(query.toString());
        updateStatement = Database.prepare("UPDATE inventory_entries SET position = ?, qu = ?, stats = ? WHERE id = ?");
        createStatement = Database.prepareInsert("INSERT INTO inventory_entries(item_id, owner, owner_type, position, stats, qu) VALUES(?,?,?,?,?,?)");
    }

    @Override
    protected String tableName() {
        return "inventory_entries";
    }

    @Override
    protected InventoryEntry createByResultSet(ResultSet RS) {
        try {
            InventoryEntry I = new InventoryEntry();

            I.id = RS.getInt("id");
            I.item_id = RS.getInt("item_id");
            I.owner = RS.getInt("owner");
            I.owner_type = RS.getByte("owner_type");
            I.position = RS.getByte("position");
            I.stats = RS.getString("stats");
            I.qu = RS.getInt("qu");

            return I;
        } catch (SQLException ex) {
            Loggin.error("Impossible de charger l'iventaire !", ex);
            return null;
        }
    }

    /**
     * Charge l'inventaire d'un perso
     *
     * @param id
     * @return
     */
    public ArrayList<InventoryEntry> getByOwner(byte type, int id) {
        ArrayList<InventoryEntry> list = new ArrayList<>();
        
        try {
            getByOwnerStatement.setInt(1, id);
            getByOwnerStatement.setByte(2, type);

            ResultSet RS = getByOwnerStatement.executeQuery();

            while (RS.next()) {
                InventoryEntry I = createByResultSet(RS);
                if (I == null) {
                    continue;
                }
                if (I.qu < 1) {
                    delete(I);
                    continue;
                }
                list.add(I);
            }
        } catch (SQLException ex) {
            Loggin.error("Impossible de charger l'inventaire de " + id, ex);
        }

        return list;
    }

    public HashMap<Byte, Integer> getAccessoriesByPlayerId(int id) {
        HashMap<Byte, Integer> list = new HashMap<>();

        try {
            getAccessoriesByPlayerIdStatement.setInt(1, id);
            ResultSet RS = getAccessoriesByPlayerIdStatement.executeQuery();

            while (RS.next()) {
                list.put(RS.getByte("aPOS"), RS.getInt("aID"));
            }
        } catch (SQLException ex) {
            Loggin.error("Impossible de charger les accéssoires du personnage " + id, ex);
        }

        return list;
    }

    @Override
    public boolean update(InventoryEntry obj) {
        try {
            updateStatement.setByte(1, obj.position);
            updateStatement.setInt(2, obj.qu);
            updateStatement.setString(3, obj.stats);
            updateStatement.setInt(4, obj.id);

            return updateStatement.execute();
        } catch (SQLException ex) {
            Loggin.error("Impossible d'enregistrer l'item !", ex);
            return false;
        }

    }

    @Override
    public boolean create(InventoryEntry obj) {
        try {
            createStatement.setInt(1, obj.item_id);
            createStatement.setInt(2, obj.owner);
            createStatement.setByte(3, obj.owner_type);
            createStatement.setByte(4, obj.position);
            createStatement.setString(5, obj.stats);
            createStatement.setInt(6, obj.qu);

            createStatement.executeUpdate();
            ResultSet RS = createStatement.getGeneratedKeys();

            int id = -1;
            while (RS.next()) {
                id = RS.getInt(1);
            }

            if (id < 1) {
                return false;
            }
            obj.id = id; //ne pas oublier ça...
            return true;

        } catch (SQLException ex) {
            Loggin.error("Enregistrement impossible de l'item dans la dbb", ex);
            return false;
        }
    }
}
