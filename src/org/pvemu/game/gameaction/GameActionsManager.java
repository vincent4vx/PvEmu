/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.gameaction;

import java.util.ArrayList;
import java.util.HashMap;
import org.pvemu.jelly.Loggin;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class GameActionsManager {
    final static public int NO_DEFIANCE_TARGET = -1;
    
    private boolean walking = false;
    private boolean busy = false;
    final private HashMap<Short, GameActionData> gameActions = new HashMap<>();
    final private ArrayList<GameActionData> pendingActions = new ArrayList<>();
    private int defianceTarget = NO_DEFIANCE_TARGET;

    public boolean isWalking() {
        return walking;
    }

    public void setDefianceTarget(int defiantTarget) {
        this.defianceTarget = defiantTarget;
    }

    public int getDefianceTarget() {
        return defianceTarget;
    }
    
    public boolean isBusy(){
        return walking || !gameActions.isEmpty() || busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    void setWalking(boolean walking) {
        this.walking = walking;
    }
    
    void addPendingAction(GameActionData gad){
        pendingActions.add(gad);
    }
    
    void clearPendingActions(){
        pendingActions.clear();
    }
    
    short addGameAction(GameActionData gad){
        short id = 0;
        Object[] keys = gameActions.keySet().toArray();
        if (keys.length > 0) {
            id = (short)((short)(keys[keys.length - 1]) + 1);
        }
        gameActions.put(id, gad);
        
        return id;
    }
    
    ArrayList<GameActionData> getPendingActions(){
        return pendingActions;
    }
    
    void performPendingActions(){
        for(GameActionData data : pendingActions){
            GameAction GA = GameActionsRegistry.instance().getGameAction(data.getGameActionID());
            GA.end(data, true, null);
        }
        pendingActions.clear();
    }
    
    public void startGameAction(GameActionData gad){
        GameAction GA = GameActionsRegistry.instance().getGameAction(gad.getGameActionID());
        
        if(GA == null){
            Loggin.debug("GameAction %d introuvable !", gad.getGameActionID());
            GameSendersRegistry.getGameAction().error(gad.getPlayer().getSession());
            return;
        }
        
        GA.start(gad);
    }
    
    public void endGameAction(short id, boolean success, String[] args){
        GameActionData gad = gameActions.get(id);
        
        if(gad == null) //error : no found game action
            return;
        
        GameAction GA = GameActionsRegistry.instance().getGameAction(gad.getGameActionID());
        GA.end(gad, success, args);
        gameActions.remove(id);
    }
    
    public void clearAll(){
        walking = false;
        busy = false;
        gameActions.clear();
        pendingActions.clear();
        defianceTarget = NO_DEFIANCE_TARGET;
    }
}
