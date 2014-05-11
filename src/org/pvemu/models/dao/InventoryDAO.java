package org.pvemu.models.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.pvemu.game.objects.item.ItemPosition;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.database.DatabaseHandler;
import org.pvemu.jelly.database.Query;
import org.pvemu.jelly.database.ReservedQuery;
import org.pvemu.jelly.database.UpdatableDAO;
import org.pvemu.models.InventoryEntry;

public class InventoryDAO extends UpdatableDAO<InventoryEntry> {

    final private Query getByOwner = DatabaseHandler.instance().prepareQuery("SELECT * FROM inventory_entries WHERE owner = ? AND owner_type = ?");
    final private Query create = DatabaseHandler.instance().prepareInsert("INSERT INTO inventory_entries(item_id, owner, owner_type, position, stats, qu) VALUES(?,?,?,?,?,?)");
    final private Query update = DatabaseHandler.instance().prepareQuery("UPDATE inventory_entries SET position = ?, qu = ?, stats = ? WHERE id = ?");
    final private Query getAccessoriesByPlayerId;

    public InventoryDAO() {
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
        getAccessoriesByPlayerId = DatabaseHandler.instance().prepareQuery(query.toString());
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
     * Load an item list
     * @param id
     * @return
     */
    public ArrayList<InventoryEntry> getByOwner(byte type, int id) {
        ArrayList<InventoryEntry> list = new ArrayList<>();
        
        ReservedQuery query = getByOwner.reserveQuery();
        try {
            query.getStatement().setInt(1, id);
            query.getStatement().setByte(2, type);

            ResultSet RS = query.getStatement().executeQuery();

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
            Loggin.error("Cannot load inventory of " + id, ex);
        }finally{
            query.release();
        }

        return list;
    }

    /**
     * Get accessories list of a player
     * @param id
     * @return 
     */
    public HashMap<Byte, Integer> getAccessoriesByPlayerId(int id) {
        HashMap<Byte, Integer> list = new HashMap<>();

        ReservedQuery query = getAccessoriesByPlayerId.reserveQuery();
        try {
            query.getStatement().setInt(1, id);
            ResultSet RS = query.getStatement().executeQuery();

            while (RS.next()) {
                list.put(RS.getByte("aPOS"), RS.getInt("aID"));
            }
        } catch (SQLException ex) {
            Loggin.error("Cannot load accessories of " + id, ex);
        }finally{
            query.release();
        }

        return list;
    }

    @Override
    public boolean update(InventoryEntry obj) {
        ReservedQuery query = update.reserveQuery();
        try {
            query.getStatement().setByte(1, obj.position);
            query.getStatement().setInt(2, obj.qu);
            query.getStatement().setString(3, obj.stats);
            query.getStatement().setInt(4, obj.id);

            return query.getStatement().execute();
        } catch (SQLException ex) {
            Loggin.error("Cannot save item " + obj, ex);
            return false;
        }finally{
            query.release();
        }

    }

    @Override
    public boolean create(InventoryEntry obj) {
        ReservedQuery query = create.reserveQuery();
        try {
            query.getStatement().setInt(1, obj.item_id);
            query.getStatement().setInt(2, obj.owner);
            query.getStatement().setByte(3, obj.owner_type);
            query.getStatement().setByte(4, obj.position);
            query.getStatement().setString(5, obj.stats);
            query.getStatement().setInt(6, obj.qu);

            query.getStatement().execute();
            
            ResultSet RS = query.getStatement().getGeneratedKeys();

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
            Loggin.error("Cannot create " + obj, ex);
            return false;
        }finally{
            query.release();
        }
    }
}
