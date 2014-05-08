/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands.askers;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.objects.player.Player;
import org.pvemu.models.Account;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class PlayerAsker extends ClientAsker {

    public PlayerAsker(IoSession session, Account account, Player player) {
        super(session, account, player);
    }

    @Override
    public void write(String msg) {
        GameSendersRegistry.getMessage().infoServerMessage(getSession(), "> " + msg);
    }

    @Override
    public void writeError(String msg) {
        GameSendersRegistry.getMessage().errorServerMessage(getSession(), "> " + msg);
    }
    
}
