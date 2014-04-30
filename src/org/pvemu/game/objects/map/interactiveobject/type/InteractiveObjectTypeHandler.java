/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.map.interactiveobject.type;

import java.util.HashMap;
import java.util.Map;
import org.pvemu.game.objects.map.interactiveobject.InteractiveObject;
import org.pvemu.game.objects.map.interactiveobject.actions.InteractiveObjectAction;
import org.pvemu.game.objects.player.Player;
import org.pvemu.jelly.Loggin;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class InteractiveObjectTypeHandler {
    final static private InteractiveObjectTypeHandler instance = new InteractiveObjectTypeHandler();
    
    final private Map<Integer, InteractiveObjectType> types = new HashMap<>();
    final private InteractiveObjectType DEFAULT_TYPE = new InteractiveObjectType() {
        @Override
        public int[] objIDs() {
            return new int[]{};
        }

        @Override
        public void registerAction(InteractiveObjectAction action) {
            throw new UnsupportedOperationException("Cannot add action to default IO type");
        }

        @Override
        public void startAction(InteractiveObject IO, Player player, int actionID) {
            Loggin.debug("No actions implemented yet for %s" + IO);
        }
    };

    private InteractiveObjectTypeHandler() {
        registerType(new Zaap());
        registerType(new AstrubStatue());
    }
    
    public void registerType(InteractiveObjectType type){
        for(int objID : type.objIDs()){
            types.put(objID, type);
        }
    }
    
    public InteractiveObjectType getIOType(int objID){
        InteractiveObjectType type = types.get(objID);
        
        if(type == null)
            return DEFAULT_TYPE;
        
        return type;
    }

    static public InteractiveObjectTypeHandler instance() {
        return instance;
    }
    
}
