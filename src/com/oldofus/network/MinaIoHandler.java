package com.oldofus.network;

import com.oldofus.jelly.Loggin;
import com.oldofus.models.Account;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public abstract class MinaIoHandler extends IoHandlerAdapter {

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        System.out.println("Recv << " + (String) message);
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        System.out.println("Nouvelle connexion");
    }

    @Override
    public final void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        Loggin.error(cause.getMessage(), cause);
        session.close(true);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println("Send >> " + (String) message);
    }

    protected static Account getAccount(IoSession session) {
        Account acc = (Account) session.getAttribute("account");

        return acc;
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        Account acc = getAccount(session);
        String name = "?";
        if (acc != null) {
            name = acc.pseudo;
        }
        Loggin.debug("Déconnexion pour inactivité de %s", name);
        session.write("M01|");
        session.close(true);
    }
}
