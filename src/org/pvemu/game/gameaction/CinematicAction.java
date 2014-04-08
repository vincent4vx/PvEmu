/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.gameaction;

import java.util.logging.Level;
import org.pvemu.game.cinematic.CinematicsHandler;
import org.pvemu.jelly.Loggin;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class CinematicAction implements GameAction {

    @Override
    public short id() {
        return GameActionsRegistry.CINEMATIC;
    }

    @Override
    public void start(GameActionData data) {
        int id = data.getPlayer().getActionsManager().addGameAction(data);
        GameSendersRegistry.getGameAction().gameAction(data.getPlayer().getSession(), id, data);
    }

    @Override
    public void end(GameActionData data, boolean success, String[] args) {
        try{
            byte cinematic = Byte.parseByte(data.getArgument(0));
            CinematicsHandler.instance().endOfCinematic(cinematic, data.getPlayer());
        }catch(Exception e){
            Loggin.game("Erreur cinématique", Level.WARNING, e);
        }
    }
    
}
