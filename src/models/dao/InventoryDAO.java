package models.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jelly.database.DAO;
import jelly.database.Database;
import models.Inventory;

public class InventoryDAO extends DAO<Inventory> {
    
    private PreparedStatement getByPlayerId = null;
    private PreparedStatement createStatement = null;

    @Override
    protected String tableName() {
        return "inventory";
    }

    @Override
    protected Inventory createByResultSet(ResultSet RS) {
        try {
            Inventory I = new Inventory();
            
            I.id = RS.getInt("id");
            I.item_id = RS.getInt("item_id");
            I.owner = RS.getInt("owner");
            I.owner_type = RS.getByte("owner_type");
            I.position = RS.getByte("position");
            I.stats = RS.getString("stats");
            I.qu = RS.getInt("qu");
            
            return I;
        } catch (SQLException ex) {
            Logger.getLogger(InventoryDAO.class.getName()).log(Level.SEVERE, "Impossible de charge l'item en inventaire", ex);
            return null;
        }
    }
    
    /**
     * Charge l'inventaire d'un perso
     * @param id
     * @return 
     */
    public ArrayList<Inventory> getByPlayerId(int id){
        ArrayList<Inventory> list = new ArrayList<>();
        
        if(getByPlayerId == null){
            getByPlayerId = Database.prepare("SELECT * FROM inventory WHERE owner = ? AND owner_type = 1");
        }
        try {
            getByPlayerId.setInt(1, id);
            
            ResultSet RS = getByPlayerId.executeQuery();
            
            while(RS.next()){
                list.add(createByResultSet(RS));
            }
        } catch (SQLException ex) {
            Logger.getLogger(InventoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }

    @Override
    public boolean update(Inventory obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(Inventory obj) {
        if(createStatement == null){
            createStatement = Database.prepareInsert("INSERT INTO inventory(item_id, owner, owner_type, position, stats, qu) VALUES(?,?,?,?,?,?)");
        }
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
            while(RS.next()){
                id = RS.getInt(1);
            }
            
            if(id < 1){
                return false;
            }
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(InventoryDAO.class.getName()).log(Level.SEVERE, "Enregistrement impossible de l'item dans la dbb", ex);
            return false;
        }
    }
    
}
