package org.pvemu.models;

//import org.pvemu.game.ActionsHandler.Action;
import org.pvemu.common.database.Model;

public class NpcResponseAction implements Model {
    public int response_id;
    public short action_id;
    public String[] args;
    
/*    private Action _action = null;
    
    public Action getAction(){
        if(_action == null){
            _action = new Action(action_id, args, "-1");
        }
        return _action;
    }*/

    @Override
    public int getPk() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
