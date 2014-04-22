package org.pvemu.network.realm.input;

import org.apache.mina.core.session.IoSession;
import org.pvemu.jelly.Constants;
import org.pvemu.models.Account;
import org.pvemu.models.dao.DAOFactory;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.generators.GeneratorsRegistry;
import org.pvemu.network.realm.RealmPacketEnum;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ServerListPacket implements InputPacket {

    @Override
    public String id() {
        return "Ax";
    }

    @Override
    public void perform(String extra, IoSession session) {
        Account account = SessionAttributes.ACCOUNT.getValue(session);
        
        if(account == null)
            return;
        
        RealmPacketEnum.SERVER_LIST.send(
                session,
                GeneratorsRegistry.getAccount().generateServerList(
                        Constants.ONE_YEAR, 
                        Constants.SERV_ID, 
                        DAOFactory.character().countByAccount(account.id)
                )
        );
    }
    
}
