/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.Fighter;
import org.pvemu.game.gameaction.fight.FightActionsRegistry;
import org.pvemu.game.objects.map.MapUtils;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.utils.Pathfinding;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class NormalAI extends AIType{

    @Override
    public byte typeID() {
        return 1;
    }

    @Override
    public boolean actions(Fight fight, Fighter fighter) {
        Fighter target = null;
        
        for(Fighter f : fight.getFighters()){
            if(f.getTeam() != fighter.getTeam()){
                target = f;
                break;
            }
        }
        
        if(target == null)
            return false;
        
        List<Short> path;
        try{
            path = new ArrayList<>(Pathfinding.findPath(fight, fighter.getCellId(), target.getCellId()));
        }catch(Exception e){
            Loggin.error("Cannot find path", e);
            return false;
        }
        
        if(path.isEmpty())
            return false;
        
        StringBuilder sb = new StringBuilder();
        
        short last = fighter.getCellId();
        for(int i = 1; i < path.size(); ++i){
            sb.append(MapUtils.getDirBetweenTwoCase(last, path.get(i), fight.getFightMap().getMap(), true));
            sb.append(Pathfinding.cellID_To_Code(path.get(i)));
            last = path.get(i);
        }
        
        GameSendersRegistry.getGameAction().gameActionToFight(
                fight, 
                1, 
                FightActionsRegistry.WALK,
                fighter.getID(),
                sb.toString()
        );
        try {
            Thread.sleep(500 * path.size());
        } catch (InterruptedException ex) {
            Logger.getLogger(NormalAI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
}
