package org.pvemu.models.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.database.Database;
import org.pvemu.jelly.database.FindableDAO;
import org.pvemu.models.NpcResponseAction;

public class NpcResponseActionDAO extends FindableDAO<NpcResponseAction> {
    final private PreparedStatement findByResponseIdStatement = Database.prepare("SELECT * FROM npc_responses_actions WHERE response_id = ?");

    @Override
    protected String tableName() {
        return "npc_responses_actions";
    }

    @Override
    protected NpcResponseAction createByResultSet(ResultSet RS) {
        try {
            NpcResponseAction NRA = new NpcResponseAction();
            
            NRA.response_id = RS.getInt("response_id");
            NRA.action_id = RS.getShort("action_id");
            NRA.args = RS.getString("args").split(",");
            
            return NRA;
        } catch (SQLException ex) {
            Loggin.error("Impossible de charger l'action !", ex);
            return null;
        }
    }
    
    public ArrayList<NpcResponseAction> getByResponseId(int id){
        ArrayList<NpcResponseAction> list = new ArrayList<>();
        
        try {
            findByResponseIdStatement.setInt(1, id);
            ResultSet RS = findByResponseIdStatement.executeQuery();
            
            while(RS.next()){
                NpcResponseAction NRA = createByResultSet(RS);
                
                if(NRA == null){
                    continue;
                }
                list.add(NRA);
            }
        } catch (SQLException ex) {
            Loggin.error("Impossible de charger les actions de la réponse " + id, ex);
        }
        
        return list;
    }
    
}
