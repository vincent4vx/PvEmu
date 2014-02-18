/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import org.pvemu.jelly.filters.AccountFilter;
import org.pvemu.jelly.filters.Filter;
import org.pvemu.models.Account;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class ClientAsker implements Asker {
    final protected Account account;

    public ClientAsker(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public String name() {
        return account.pseudo;
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
