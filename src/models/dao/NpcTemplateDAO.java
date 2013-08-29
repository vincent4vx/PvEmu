package models.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import jelly.Loggin;
import jelly.database.DAO;
import models.NpcTemplate;

public class NpcTemplateDAO extends DAO<NpcTemplate> {
    private HashMap<Integer, NpcTemplate> npcById = new HashMap<>();

    @Override
    protected String tableName() {
        return "npc_templates";
    }

    @Override
    protected NpcTemplate createByResultSet(ResultSet RS) {
        try {
            NpcTemplate T = new NpcTemplate();
            
            T.id = RS.getInt("id");
            T.gfxID = RS.getInt("gfxID");
            T.scaleX = RS.getShort("scaleX");
            T.scaleY = RS.getShort("scaleY");
            T.sex = RS.getByte("sex");
            T.color1 = RS.getInt("color1");
            T.color2 = RS.getInt("color2");
            T.color3 = RS.getInt("color3");
            T.accessories = RS.getString("accessories");
            
            npcById.put(T.id, T);
            
            return T;
        } catch (SQLException ex) {
            Loggin.error("Impossible de charger le template de pnj", ex);
            return null;
        }
        
    }
    
    /**
     * Retourne le pnj correspondant
     * @param id
     * @return 
     */
    public NpcTemplate getById(int id){
        if(!npcById.containsKey(id)){
            return find(id);
        }
        return npcById.get(id);
    }

    @Override
    public boolean update(NpcTemplate obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(NpcTemplate obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
