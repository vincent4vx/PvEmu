/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.GameActionHandler;
import org.pvemu.game.objects.Player;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class GameActionOkPacket implements InputPacket {

    @Override
    public String id() {
        return "GK";
    }

    @Override
    public void perform(String extra, IoSession session) {
        boolean ok = extra.charAt(0) == 'K';

        Player player = SessionAttributes.PLAYER.getValue(session);//(Player) session.getAttribute("player");

        if (player == null) {
            return;
        }

        short actionID = 0;
        String[] args;

        try {
            args = Utils.split(extra.substring(1), "|");
            actionID = Short.parseShort(args[0]);
        } catch (Exception e) {
            return;
        }
        
        player.getActionsManager().endGameAction(actionID, ok, args);
        
        
//
//        GameActionHandler.GameAction GA = p.getActions().get(actionID);
//
//        if (GA == null) {
//            Loggin.debug("GameAction %d non trouvée !", new Object[]{actionID});
//            return;
//        }
//
//        /*switch (GA.actionID) {
//            case 1: //déplacement
//                if (ok) {
//                    short cellDest = (Short) GA.get("dest");
//                    MapEvents.onArrivedOnCell(session, cellDest);
//                } else {
//                    short cellDest = Short.parseShort(args[1]);
//                    MapEvents.onArrivedOnCell(session, cellDest);
//                }
//                p.orientation = (byte) GA.get("ori");
//                break;
//            case 2: //cinématiques
//                switch ((int) GA.args[1]) {
//                    case 7: //téléportation incarnam => astrub
//                        short[] mapData = ClassData.getStatuesPos(p.getClassID());
//                        p.teleport(mapData[0], mapData[1]);
//                        p.setStartPos(mapData);
//                        ChatEvents.onSendInfoMessage(p.getSession(), 6);
//                        break;
//                }
//                break;
//        }*/
//        GA.apply(p, ok, args);
//        GA.delete();
        
    }
    
}
