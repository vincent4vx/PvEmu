package org.pvemu.network.events;

import org.pvemu.game.objects.Player;
import org.apache.mina.core.session.IoSession;
import org.pvemu.network.game.GamePacketEnum;
import org.pvemu.network.generators.GeneratorsRegistry;

@Deprecated
public class CharacterEvents {

    public static void onStatsChange(IoSession session, Player p) {
        GamePacketEnum.STATS_PACKET.send(session, GeneratorsRegistry.getPlayer().generateAs(p));
    }
    
}
