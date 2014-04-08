/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.actions;

import org.apache.mina.core.session.IoSession;
import org.pvemu.jelly.Loggin;
import org.pvemu.models.Account;
import org.pvemu.network.SessionAttributes;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class AccountAction {
    public void logout(Account account){
        IoSession session = account.getSession();
        account.removeSession();
        SessionAttributes.ACCOUNT.removeValue(session);
        Loggin.debug("account/%s logout", account.pseudo);
    }
}
