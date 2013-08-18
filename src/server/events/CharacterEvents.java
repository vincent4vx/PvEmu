package server.events;

import game.objects.Player;
import jelly.Utils;
import models.dao.DAOFactory;
import org.apache.mina.core.session.IoSession;
import server.game.GamePacketEnum;

public class CharacterEvents {

    public static void onCharacterSelected(IoSession session, String packet) {

        try {
            int id = Integer.parseInt(packet.substring(2));
            models.Character chr = DAOFactory.character().getById(id);

            if (chr == null) {
                GamePacketEnum.SELECT_CHARACTER_ERROR.send(session);
                return;
            }

            session.setAttribute("player", chr.getPlayer());
            chr.getPlayer().setSession(session);


            StringBuilder param = new StringBuilder();

            param.append(chr.id).append("|").append(chr.name).append("|")
                    .append(chr.level).append("|").append(chr.classId).append("|")
                    .append(chr.sexe).append("|").append(chr.gfxid).append("|")
                    .append(Utils.implode("|", chr.getPlayer().getColors())).append("|");

            GamePacketEnum.SELECT_CHARACTER_OK.send(session, param.toString());
        } catch (Exception e) {
            e.printStackTrace();
            GamePacketEnum.SELECT_CHARACTER_ERROR.send(session);
        }
    }

    public static void onGameCreate(IoSession session) {
        Player p = getPlayer(session);

        if (p == null) {
            return;
        }

        GamePacketEnum.GAME_CREATE_OK.send(session, p.getName());
        GamePacketEnum.STATS_PACKET.send(session, p.getStatsPacket());
        MapEvents.onArrivedInGame(session);
    }

    private static Player getPlayer(IoSession session) {
        Player p = (Player) session.getAttribute("player");

        if (p == null) {
            session.close(false);
        }

        return p;
    }
}
