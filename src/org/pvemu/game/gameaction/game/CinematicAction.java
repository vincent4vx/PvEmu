/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.gameaction.game;

import java.util.logging.Level;
import org.pvemu.game.cinematic.CinematicsHandler;
import org.pvemu.game.gameaction.GameAction;
import org.pvemu.game.gameaction.GameActionData;
import org.pvemu.game.objects.player.Player;
import org.pvemu.common.Loggin;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class CinematicAction implements GameAction<Player> {

    @Override
    public short id() {
        return GameActionsRegistry.CINEMATIC;
    }

    @Override
    public void start(GameActionData<Player> data) {
        int id = data.getPerformer().getActionsManager().addGameAction(data);
        GameSendersRegistry.getGameAction().gameAction(data.getPerformer().getSession(), id, data);
    }

    @Override
    public void end(GameActionData<Player> data, boolean success, String[] args) {
        try{
            byte cinematic = Byte.parseByte(data.getArgument(0));
            CinematicsHandler.instance().endOfCinematic(cinematic, data.getPerformer());
        }catch(Exception e){
            Loggin.game("Erreur cinématique", Level.WARNING, e);
        }
    }
    
}
