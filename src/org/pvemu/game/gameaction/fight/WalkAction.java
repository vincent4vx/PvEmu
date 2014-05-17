/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.gameaction.fight;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;
import org.pvemu.game.fight.fightertype.PlayerFighter;
import org.pvemu.game.gameaction.GameAction;
import org.pvemu.game.gameaction.GameActionData;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.utils.Crypt;
import org.pvemu.jelly.utils.Pathfinding;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class WalkAction implements GameAction<PlayerFighter>{

    @Override
    public short id() {
        return FightActionsRegistry.WALK;
    }

    @Override
    public void start(GameActionData<PlayerFighter> data) {
        if(!data.getPerformer().canPlay()){
            GameSendersRegistry.getGameAction().error(data.getPerformer().getSession());
            return;
        }
        
        Collection<Short> path = Pathfinding.parsePath(
                data.getPerformer().getFight().getFightMap().getMap(),
                data.getPerformer().getCellId(),
                data.getArgument(0),
                true
        );
        
        Loggin.debug("path : %s", path);
        
        path = data.getPerformer().getFight().getFightMap().validatePath(path, data.getPerformer());
        
        int steps = path.size();

        Loggin.debug("[Fight] Tentative de déplacement de %s de %d en %d étapes", data.getPerformer().getName(), data.getPerformer().getCellId(), steps);

        if (steps == -1000 || steps == 0) {
            Loggin.debug("[Fight] Path invalide !");
            GameSendersRegistry.getGameAction().error(data.getPerformer().getSession());
            return;
        }
        
        String newPath = Crypt.compressPath(
                data.getPerformer().getFight().getFightMap().getMap(), 
                data.getPerformer().getCellId(), 
                path,
                false
        );
        
        data.setArgument(0, newPath);

        short id = data.getPerformer().getActionsManager().addGameAction(data);
        
        GameSendersRegistry.getGameAction().gameActionStartToFight(
                data.getPerformer().getFight(), 
                data.getPerformer().getID()
        );

        GameSendersRegistry.getGameAction().gameActionToFight(
                data.getPerformer().getFight(),
                id,
                data
        );
        
        data.getPerformer().removePM(steps);
        GameSendersRegistry.getEffect().removePMOnWalk(data.getPerformer().getFight(), data.getPerformer().getID(), steps);
    }

    @Override
    public void end(GameActionData<PlayerFighter> data, boolean success, String[] args) {
        short cellDest;

        if (success) {
            cellDest = Crypt.cellCode_To_ID(data.getArgument(0).substring(data.getArgument(0).length() - 2));
        } else {
            cellDest = Short.parseShort(args[1]);
        }

        data.getPerformer().getFight().getFightMap().moveFighter(data.getPerformer(), cellDest);
        
        Loggin.debug("[Fight] %s arrived to cell %d", data.getPerformer().getName(), cellDest);
        
        GameSendersRegistry.getGameAction().gameActionFinishToFight(
                data.getPerformer().getFight(), 
                2, 
                data.getPerformer().getID()
        );
    }
    
}
