/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands.askers;

import org.pvemu.game.objects.Player;
import org.pvemu.models.Account;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class PlayerAsker extends ClientAsker {
    
    final private Player player;

    public PlayerAsker(Player player, Account account) {
        super(account);
        this.player = player;
    }

    @Override
    public void write(String msg) {
        GameSendersRegistry.getMessage().infoServerMessage(player.getSession(), "> " + msg);
    }

    @Override
    public void writeError(String msg) {
        GameSendersRegistry.getMessage().errorServerMessage(player.getSession(), "> " + msg);
    }
    
}
