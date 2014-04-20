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
                map.getFreeFightId(),
                new FightMap(map),
                new FightTeam[]{
                    new FightTeam((byte)0, p1.getID() << 16, p1.getCellId(), MapUtils.parseCellList(places[0], map)),
                    new FightTeam((byte)1, p2.getID() << 16, p2.getCellId(), MapUtils.parseCellList(places[1], map))
                }
        );
        
        fight.setState(Fight.STATE_PLACE);
        
        fight.addFighterToTeamByNumber(new PlayerFighter(p1, fight), (byte)0);
        fight.addFighterToTeamByNumber(new PlayerFighter(p2, fight), (byte)1);
        
        return fight;
    }
}
