package org.pvemu.models.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.database.FindableDAO;
import org.pvemu.models.ItemTemplate;

public class ItemTemplateDAO extends FindableDAO<ItemTemplate> {
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
            T.type = RS.getByte("type");
            
            itemTemplateById.put(T.id, T);
            
            return T;
        } catch (SQLException ex) {
            Loggin.error("Chargement impossible de l'item", ex);
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
    
}
