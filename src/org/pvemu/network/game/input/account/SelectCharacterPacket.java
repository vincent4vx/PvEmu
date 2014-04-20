/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input.account;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.World;
import org.pvemu.game.objects.player.Player;
import org.pvemu.game.objects.player.PlayerFactory;
import org.pvemu.jelly.Constants;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.models.Account;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.GamePacketEnum;
import org.pvemu.network.game.GameServer;
import org.pvemu.network.generators.GeneratorsRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class SelectCharacterPacket implements InputPacket {

    @Override
    public String id() {
        return "AS";
    }

    @Override
    public void perform(String extra, IoSession session) {

        try {
            Account acc = SessionAttributes.ACCOUNT.getValue(session);//(Account) session.getAttribute("account");

            if (acc == null) {
                return;
            }

            int id, svr = 0;
            
            try{
                String[] data = Utils.split(extra, "|");//packet.split("\\|");
                
                id = Integer.parseInt(data[0]);
                
                if(data.length == 2){
                    svr = Integer.parseInt(data[1]);
                }
            }catch(Exception e){
                GamePacketEnum.SELECT_CHARACTER_ERROR.send(session);
                return;
            }
            
            org.pvemu.models.Character chr = acc.getCharacter(id);

            if (chr == null) {
                GamePacketEnum.SELECT_CHARACTER_ERROR.send(session);
                return;
            }

            Player player = PlayerFactory.getPlayer(chr, acc);//chr.getPlayer();

            if (Constants.DOFUS_VER_ID >= 1100) { //pour dofus "récents" : connecte directement le pesonnage
                //session.setAttribute("player", p);
                SessionAttributes.PLAYER.setValue(player, session);
                player.setSession(session);

                World.instance().addOnline(player);

                GamePacketEnum.SELECT_CHARACTER_OK.send(session, GeneratorsRegistry.getPlayer().generateSelectionOk(player));
            } else { //vielles version (cf: 1.09.1), envoit les ids du game
                String ticket = acc.setWaiting();
                GamePacketEnum.SELECT_CHARACTER_OK.send(session, GameServer.CRYPT_IP + ticket);
                acc.setWaitingCharacter(player);
            }
        } catch (Exception e) {
            Loggin.error("Impossible de sélectionner le personnage", e);
            GamePacketEnum.SELECT_CHARACTER_ERROR.send(session);
        }
       
    }
    
}
