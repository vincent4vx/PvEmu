package org.pvemu.network.events;

import org.pvemu.game.GameActionHandler.GameAction;
import org.pvemu.game.objects.Player;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.jelly.utils.Pathfinding;
import org.apache.mina.core.session.IoSession;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.GamePacketEnum;

@Deprecated
public class GameActionEvents {
    
    @Deprecated
    public static void onMapAction(Player p, GameAction GA){
        short cellID = Short.parseShort((String)GA.args[0]);
        
        if(Pathfinding.isAdjacentCells(p.getCell().getID(), cellID)){
            GA.apply(p, true, null);
        }else{
            GA.save();
        }
    }

    /**
     * Envoi la GameAction au client
     *
     * @param session
     * @param actionID
     * @param params
     */
    @Deprecated
    public static void onSendGameAction(IoSession session, GameAction GA) {
        if (session == null) {
            return;
        }

        GamePacketEnum.GAME_ACTION.send(session, GA.id + ";" + GA.actionID + ";" + Utils.implode(";", GA.args));
    }

    /**
     * Crée une GA, la sauvegarde et l'envoi au client
     *
     * @param session
     * @param actionID
     * @param args
     */
    public static void onCreateGameAction(IoSession session, int actionID, Object... args) {
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player) session.getAttribute("player");

        if (p == null) {
            return;
        }

        GameAction GA = new GameAction(p.getActions(), actionID, args);
        GA.save();
        onSendGameAction(session, GA);
    }
}
