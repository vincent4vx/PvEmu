/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands.askers;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.objects.player.Player;
import org.pvemu.models.Account;
import org.pvemu.network.game.GamePacketEnum;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ConsoleAsker extends ClientAsker {

    public ConsoleAsker(IoSession session, Account account, Player player) {
        super(session, account, player);
    }

    @Override
    public void write(String msg) {
        GamePacketEnum.CONSOLE_WRITE_OK.send(getSession(), msg);
    }

    @Override
    public void writeError(String msg) {
        GamePacketEnum.CONSOLE_WRITE_ERROR.send(getSession(), msg);
    }
    
}
