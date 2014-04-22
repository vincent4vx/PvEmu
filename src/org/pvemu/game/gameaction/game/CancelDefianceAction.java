/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.gameaction.game;

import org.pvemu.actions.ActionsRegistry;
import org.pvemu.game.gameaction.GameAction;
import org.pvemu.game.gameaction.GameActionData;
import org.pvemu.game.objects.player.Player;
import org.pvemu.jelly.Loggin;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class CancelDefianceAction implements GameAction<Player>{

    @Override
    public short id() {
        return GameActionsRegistry.CANCEL_DEFIANCE;
    }

    @Override
    public void start(GameActionData<Player> data) {
        Loggin.debug("%s cancelling defiance", data.getPerformer().getName());
        ActionsRegistry.getPlayer().cancelDefiance(data.getPerformer());
    }

    @Override
    public void end(GameActionData<Player> data, boolean success, String[] args) {
    }
    
}
