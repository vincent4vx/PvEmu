/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.map.interactiveobject.actions;

import org.pvemu.actions.ActionsRegistry;
import org.pvemu.game.objects.map.interactiveobject.InteractiveObject;
import org.pvemu.game.objects.player.Player;
import org.pvemu.common.filters.Filter;
import org.pvemu.common.filters.PlayerFilter;
import org.pvemu.common.filters.comparators.LessThanComparator;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class GotoIncarnam implements InteractiveObjectAction{

    @Override
    public int id() {
        return 183;
    }

    @Override
    public void startAction(InteractiveObject IO, Player player) {
        short mapID = player.getClassData().getStartMap();
        short cellID = player.getClassData().getStartCell();
        
        player.setStartPos(mapID, cellID);
        ActionsRegistry.getPlayer().teleport(player, mapID, cellID);
    }

    @Override
    public Filter condition() {
        PlayerFilter filter = new PlayerFilter();
        
        filter.setLevel(new LessThanComparator((short)16));
        
        return filter;
    }

    @Override
    public void onError(InteractiveObject IO, Player player) {
        GameSendersRegistry.getInformativeMessage().error(player.getSession(), 1127);
    }
    
}
