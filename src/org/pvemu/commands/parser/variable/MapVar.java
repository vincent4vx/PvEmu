/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands.parser.variable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.pvemu.commands.askers.Asker;
import org.pvemu.commands.askers.ClientAsker;
import org.pvemu.game.objects.player.Player;
import org.pvemu.common.filters.ClientAskerFilter;
import org.pvemu.common.filters.Filter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class MapVar implements DynamicVar{

    @Override
    public String name() {
        return "map";
    }

    @Override
    public List<String> getValue(Asker asker) {
        Collection<Player> players = ((ClientAsker)asker).getPlayer().getMap().getPlayers().values();
        List<String> list = new ArrayList<>(players.size());
        
        for(Player player : players){
            list.add(player.getName());
        }
        
        return list;
    }

    @Override
    public Filter condition() {
        return new ClientAskerFilter();
    }
    
}
