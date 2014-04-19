/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight;

import org.pvemu.game.objects.map.GameMap;
import org.pvemu.game.objects.map.MapUtils;
import org.pvemu.game.objects.player.Player;
import org.pvemu.jelly.utils.Utils;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class FightFactory {
    static public DefianceFight defiance(Player p1, Player p2){
        GameMap map = p1.getMap();
        String[] places = Utils.split(map.getModel().places, "|", 2);
        
        DefianceFight fight = new DefianceFight(
                new FightMap(map),
                new FightTeam((byte)0, MapUtils.parseCellList(places[0], map)),
                new FightTeam((byte)1, MapUtils.parseCellList(places[1], map))
        );
        
        fight.setState(Fight.STATE_PLACE);
        
        fight.addFighterToTeam1(new PlayerFighter(p1, fight));
        fight.addFighterToTeam2(new PlayerFighter(p2, fight));
        
        return fight;
    }
}
