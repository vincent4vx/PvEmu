/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pvemu.game.gameaction;

import java.util.concurrent.atomic.AtomicReference;
import org.pvemu.actions.ActionsRegistry;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.utils.Pathfinding;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class WalkAction implements GameAction {

    @Override
    public short id() {
        return GameActionsRegistry.WALK;
    }

    @Override
    public void start(GameActionData data) {
        AtomicReference<String> rPath = new AtomicReference<>((String) data.getArgument(0));
        short steps;
        
        if(!data.isFight()){
            steps = Pathfinding.validatePath(
                    data.getPlayer().getMap(), 
                    data.getPlayer().getCell().getID(), 
                    rPath,
                    false
            );
            
            Loggin.debug("Tentative de déplacement de %s de %d en %d étapes", data.getPlayer().getName(), data.getPlayer().getCell().getID(), steps);
        }else{
            steps = Pathfinding.validatePath(
                    data.getFighter().getFight().getMap().getMap(),
                    data.getFighter().getCellId(),
                    rPath,
                    true
            );
            
            Loggin.debug("Fight : Tentative de déplacement de %s de %d en %d étapes", data.getPlayer().getName(), data.getFighter().getCellId(), steps);
        }

        if (steps == -1000 || steps == 0) {
            Loggin.debug("Path invalide !");
            GameSendersRegistry.getGameAction().error(data.getPlayer().getSession());
            return;
        }
        
        if(data.isFight()){
            short dest = Pathfinding.cellCode_To_ID(rPath.get().substring(rPath.get().length() - 2));
            
            if(!data.getFighter().getFight().canMove(data.getFighter(), dest, steps)){
                Loggin.debug("Cannot move fighter");
                GameSendersRegistry.getGameAction().error(data.getPlayer().getSession());
                return;
            }
        }
        
        String newPath = "a" + 
                Pathfinding.cellID_To_Code(
                        data.isFight() ?
                            data.getFighter().getCellId() :
                            data.getPlayer().getCell().getID()
                ) + 
                rPath.get();
        
        data.setArgument(0, newPath);

        short id = data.getPlayer().getActionsManager().addGameAction(data);

        if(!data.isFight()){
            GameSendersRegistry.getGameAction().gameActionToMap(
                    data.getPlayer().getMap(),
                    id,
                    data
            );
        }else{
            GameSendersRegistry.getGameAction().gameActionStartToFight(
                    data.getFighter().getFight(), 
                    data.getFighter().getID()
            );
            GameSendersRegistry.getGameAction().gameActionToFight(
                    data.getFighter().getFight(),
                    id,
                    data
            );
            data.getFighter().removePM(steps);
        }

        data.getPlayer().getActionsManager().setWalking(true);
        data.getPlayer().getActionsManager().clearPendingActions();
    }

    @Override
    public void end(GameActionData data, boolean success, String[] args) {        
        short cellDest;
        
        if (success) {
            cellDest = Pathfinding.cellCode_To_ID(data.getArgument(0).substring(data.getArgument(0).length() - 2));
        } else {
            cellDest = Short.parseShort(args[1]);
        }
        
        if(!data.isFight()){
            ActionsRegistry.getPlayer().arrivedOnCell(data.getPlayer(), data.getPlayer().getMap().getCellById(cellDest));
            data.getPlayer().orientation = Utils.parseBase64Char(data.getArgument(0).charAt(data.getArgument(0).length() - 3));
        }else{
            data.getFighter().getFight().getMap().moveFighter(data.getFighter(), cellDest);
            GameSendersRegistry.getGameAction().gameActionFinishToFight(
                    data.getFighter().getFight(), 
                    2, 
                    data.getFighter().getID()
            );
        }

        data.getPlayer().getActionsManager().setWalking(false);
        data.getPlayer().getActionsManager().performPendingActions();
    }

}
