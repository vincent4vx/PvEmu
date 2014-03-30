/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pvemu.game.cinematic;

import org.pvemu.actions.ActionsRegistry;
import org.pvemu.game.objects.Player;
import org.pvemu.game.objects.dep.ClassData;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class GoToAstrubCinematic implements Cinematic {

    @Override
    public byte id() {
        return CinematicsHandler.GO_TO_ASTRUB;
    }

    @Override
    public void end(Player player) {
        short[] mapData = ClassData.getStatuesPos(player.getClassID());
        ActionsRegistry.getPlayer().teleport(player, mapData[0], mapData[1]);
        player.setStartPos(mapData);
        GameSendersRegistry.getInformativeMessage().info(player.getSession(), 6);
    }

}
