/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands.askers;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.objects.player.Player;
import org.pvemu.jelly.filters.AccountFilter;
import org.pvemu.jelly.filters.Filter;
import org.pvemu.models.Account;
import org.pvemu.network.Sessionable;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class ClientAsker implements Asker, Sessionable {
    final private IoSession session;
    final private Account account;
    final private Player player;

    public ClientAsker(IoSession session, Account account, Player player) {
        this.session = session;
        this.account = account;
        this.player = player;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public IoSession getSession() {
        return session;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public String name() {
        return player.getName();
    }

    @Override
    public byte level() {
        return account.level;
    }

    @Override
    public boolean corresponds(Filter filter) {
        return filter.corresponds(this);
    }
    
    public boolean corresponds(AccountFilter filter){
        return filter.corresponds(account);
    }
    
}
