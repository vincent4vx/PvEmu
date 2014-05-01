/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.ai;

import java.util.Collection;
import java.util.Iterator;
import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.Fighter;
import org.pvemu.game.gameaction.fight.FightActionsRegistry;
import org.pvemu.game.objects.map.MapUtils;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.utils.Crypt;
import org.pvemu.jelly.utils.Pathfinding;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class AIUtils {
    static public Fighter getNearestEnnemy(Fight fight, Fighter fighter){
        Iterator<Fighter> it = fight.getFighters().iterator();
        Fighter target = null;
        int dist = Integer.MAX_VALUE;
        
        for(Fighter f = it.next(); it.hasNext(); f = it.next()){
            if(f.getTeam() == fighter.getTeam())
                continue;
            
            
            int curDist = MapUtils.getDistanceBetween(
                    fight.getFightMap().getMap(), 
                    fighter.getCellId(), 
                    f.getCellId()
            );
            
            if(target == null || dist > curDist){
                dist = curDist;
                target = f;
            }
        }
        
        return target;
    }
    
    static public boolean moveNear(Fight fight, Fighter fighter, Fighter target){
        if(fight == null || fighter == null || target == null)
            return false;
        
        if(fighter.getNumPM() <= 0)
            return false;
        
        if(MapUtils.isAdjacentCells(fighter.getCellId(), target.getCellId()))
            return false;
        
        Collection<Short> path;
        
        try{
            path = Pathfinding.findPath(fight, fighter.getCellId(), target.getCellId());
        }catch(Exception e){
            Loggin.error("Cannot find path", e);
            return false;
        }
        
        if(path == null)
            return false;
        
        short last = fighter.getCellId();
        short pm = -1;
        StringBuilder strPath = new StringBuilder(3 * fighter.getNumPM());
        boolean first = true;
        for(short cell : path){
            if(first){
                strPath.append('a');
                first = false;
            }else{
                strPath.append(MapUtils.getDirBetweenTwoCase(
                        last, 
                        cell, 
                        fight.getFightMap().getMap(), 
                        true
                ));
            }
            strPath.append(Crypt.cellID_To_Code(cell));
            last = cell;
            
            if(++pm >= fighter.getNumPM())
                break;
        }
        
        if(!fight.canMove(fighter, last, pm)){
            Loggin.debug("%s can't move", fighter);
            return false;
        }
        
        GameSendersRegistry.getGameAction().gameActionToFight(
                fight, 
                1, 
                FightActionsRegistry.WALK,
                fighter.getID(),
                strPath.toString()
        );
        
        try{
            Thread.sleep(800 + 200 * pm);
        }catch(InterruptedException e){}
        
        fighter.removePM(pm);
        GameSendersRegistry.getEffect().removePMOnWalk(fight, fighter.getID(), pm);
        
        fight.getFightMap().moveFighter(fighter, last);
        Loggin.debug("[AI/Fight] %s arrived on cell %d", fighter, last);
        return true;
    }
}
