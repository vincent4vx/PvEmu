package server;

import models.Account;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public abstract class MinaIoHandler extends IoHandlerAdapter {

    
    @Override
    public void messageReceived( IoSession session, Object message ) throws Exception
    {
        System.out.println("Recv << " + (String)message);
    }
    
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        System.out.println("Nouvelle connexion");
    }
    
    @Override
    public void exceptionCaught( IoSession session, Throwable cause ) throws Exception
    {
        cause.printStackTrace();
        session.close(true);
    }
    
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println("Send >> " + (String)message);
    }
    
    protected static Account getAccount(IoSession session){
        Account acc = (Account)session.getAttribute("account");
        
        if(acc == null){
            session.close(false);
        }
        
        return acc;
    }
}
