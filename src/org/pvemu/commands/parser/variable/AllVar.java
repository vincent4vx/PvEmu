/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands.parser.variable;

import java.util.ArrayList;
import java.util.List;
import org.pvemu.commands.askers.Asker;
import org.pvemu.game.World;
import org.pvemu.game.objects.player.Player;
import org.pvemu.jelly.filters.Filter;
import org.pvemu.jelly.filters.FilterFactory;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class AllVar implements DynamicVar{

    @Override
    public String name() {
        return "all";
    }

    @Override
    public List<String> getValue(Asker asker) {
        List<String> list = new ArrayList<>();
        
        for(Player player : World.instance().getOnlinePlayers()){
            list.add(player.getName());
        }

        return list;
    }

    @Override
    public Filter condition() {
        return FilterFactory.yesFilter();
    }
    
}
