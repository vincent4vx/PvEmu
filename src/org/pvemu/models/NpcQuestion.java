package org.pvemu.models;

import java.util.ArrayList;
import java.util.HashMap;
import org.pvemu.common.database.Model;
import org.pvemu.models.dao.DAOFactory;

public class NpcQuestion implements Model {
    public int id;
    public String responses;
    private HashMap<Integer, ArrayList<NpcResponseAction>> _responses = null;
    
    /**
     * Retourne la liste des actions de la réponse
     * @param rId
     * @return 
     */
    public ArrayList<NpcResponseAction> getResponseActions(int rId){
        if(_responses == null){
            _responses = new HashMap<>();
            
            for(String sid : responses.split(";")){
                int rid = Integer.parseInt(sid);
                _responses.put(rid, DAOFactory.responseAction().getByResponseId(rid));
            }
        }
        return _responses.get(rId);
    }

    @Override
    public int getPk() {
        return id;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
