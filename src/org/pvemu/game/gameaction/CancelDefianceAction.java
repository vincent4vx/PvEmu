/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.gameaction;

import org.pvemu.actions.ActionsRegistry;
import org.pvemu.game.objects.player.Player;
import org.pvemu.jelly.Loggin;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class CancelDefianceAction implements GameAction{

    @Override
    public short id() {
        return GameActionsRegistry.CANCEL_DEFIANCE;
    }

    @Override
    public void start(GameActionData data) {
        Loggin.debug("%s cancelling defiance", data.getPlayer().getName());
        ActionsRegistry.getPlayer().cancelDefiance(data.getPlayer());
    }

    @Override
    public void end(GameActionData data, boolean success, String[] args) {
    }
    
}
