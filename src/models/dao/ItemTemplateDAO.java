package models.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jelly.database.DAO;
import models.ItemTemplate;

public class ItemTemplateDAO extends DAO<ItemTemplate> {
    private HashMap<Integer, ItemTemplate> itemTemplateById = new HashMap<>();

    @Override
    protected String tableName() {
        return "item_templates";
    }

    @Override
    protected ItemTemplate createByResultSet(ResultSet RS) {
        try {
            ItemTemplate T = new ItemTemplate();
            
            T.id = RS.getInt("id");
            T.name = RS.getString("name");
            T.level = RS.getInt("level");
            T.statsTemplate = RS.getString("statsTemplate");
            T.pods = RS.getInt("pods");
            
            itemTemplateById.put(T.id, T);
            
            return T;
        } catch (SQLException ex) {
            Logger.getLogger(ItemTemplateDAO.class.getName()).log(Level.WARNING, "Chargement impossible de l'item", ex);
            return null;
        }
    }
    
    /**
     * Charge un item donnée
     * @param id
     * @return 
     */
    public ItemTemplate getById(int id){
        if(!itemTemplateById.containsKey(id)){
            find(id);
        }
        return itemTemplateById.get(id);
    }

    @Override
    public boolean update(ItemTemplate obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(ItemTemplate obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
