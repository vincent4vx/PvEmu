/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import org.pvemu.models.Account;
import org.pvemu.network.game.GamePacketEnum;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ConsoleAsker extends ClientAsker {

    public ConsoleAsker(Account account) {
        super(account);
    }

    @Override
    public void write(String msg) {
        GamePacketEnum.CONSOLE_WRITE_OK.send(account.getSession(), msg);
    }

    @Override
    public void writeError(String msg) {
        GamePacketEnum.CONSOLE_WRITE_ERROR.send(account.getSession(), msg);
    }
    
}
