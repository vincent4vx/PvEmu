package com.oldofus.models;

import com.oldofus.jelly.database.Model;
import com.oldofus.models.dao.DAOFactory;

public class MapNpcs implements Model {
    public short mapid;
    public short cellid;
    public int npcid;
    public byte orientation;
    
    private NpcTemplate _template = null;
    
    /**
     * Retourne les données du npc
     * @return 
     */
    public NpcTemplate getTemplate(){
        if(_template == null){
            _template = DAOFactory.npcTemplate().getById(npcid);
        }
        return _template;
    }

    @Override
    public int getPk() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
