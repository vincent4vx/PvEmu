/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import org.apache.mina.core.session.IoSession;
import org.pvemu.actions.ActionsRegistry;
import org.pvemu.commands.argument.ArgumentList;
import org.pvemu.commands.argument.CommandArgumentException;
import org.pvemu.commands.askers.Asker;
import org.pvemu.commands.askers.PlayerAsker;
import org.pvemu.game.objects.map.GameMap;
import org.pvemu.game.objects.map.MapCell;
import org.pvemu.game.objects.map.MapFactory;
import org.pvemu.game.objects.map.MapUtils;
import org.pvemu.game.objects.player.Player;
import org.pvemu.common.filters.ClientAskerFilter;
import org.pvemu.common.filters.Filter;
import org.pvemu.common.filters.comparators.MoreThanComparator;
import org.pvemu.network.SessionAttributes;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class TeleportCommand extends Command{

    @Override
    public String name() {
        return "teleport";
    }

    @Override
    public void perform(ArgumentList args, Asker asker) throws CommandArgumentException{
        if(args.size() < 2){
            asker.writeError("Wrong number of parameters");
            return;
        }
        
        short mapID = args.getShort(1);
        
        GameMap map = MapFactory.getById(mapID);
        
        if(map == null){
            asker.writeError("Wrong parameter 1");
            return;
        }
        
        MapCell cell;
        if(args.size() < 3){
            cell = MapUtils.getRandomValidCell(map);
        }else{
            short cellID = args.getShort(2);
            cell = map.getCellById(cellID);
            
            if(cell == null){
                asker.writeError("Wrong parameter 2");
                return;
            }
        }
        
        IoSession session = ((PlayerAsker)asker).getAccount().getSession();
        Player player = SessionAttributes.PLAYER.getValue(session);
        
        ActionsRegistry.getPlayer().teleport(player, mapID, cell.getID());
    }

    @Override
    public Filter conditions() {
        ClientAskerFilter filter = new ClientAskerFilter();
        
        filter.setLevel(new MoreThanComparator((byte)0));
        
        return filter;
    }

    @Override
    public String[] usage() {
        return new String[]{
            "Téléporte le personnage à la map indiqué",
            "teleport [mapid] {cellid}"
        };
    }
    
    
    
}
