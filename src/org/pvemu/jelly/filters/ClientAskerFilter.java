/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.jelly.filters;

import org.pvemu.commands.askers.Asker;
import org.pvemu.commands.askers.ClientAsker;
import org.pvemu.models.Account;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ClientAskerFilter<T extends ClientAsker> extends AskerFilter<T> {
    
    private Filter<Account> accountFilter = new YesFilter();

    /**
     * Set the value of accountFilter
     *
     * @param accountFilter new value of accountFilter
     */
    public void setAccountFilter(Filter<Account> accountFilter) {
        this.accountFilter = accountFilter;
    }

    @Override
    public boolean corresponds(T obj) {
        return super.corresponds(obj) && obj.corresponds(accountFilter);
    }
    
    

}
