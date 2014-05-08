/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands.parser.variable;

import java.util.ArrayList;
import java.util.List;
import org.pvemu.commands.askers.Asker;
import org.pvemu.commands.parser.ParserError;
import org.pvemu.game.World;
import org.pvemu.game.objects.player.Player;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class VariableUtils {
    static public List<Player> getPlayerListIntoVar(String var, Asker asker) throws ParserError{
        List<Player> players = new ArrayList<>();
        
        List<String> names = VariablesHandler.instance().eval(var, asker);
        
        for(String name : names){
            Player player = World.instance().getOnlinePlayer(name);
            
            if(player != null)
                players.add(player);
        }
        
        return players;
    }
    
    static public List<Player> getMe(Asker asker) throws ParserError{
        return getPlayerListIntoVar("$me", asker);
    }
}
