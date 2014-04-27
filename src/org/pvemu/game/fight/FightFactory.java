/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight;

import org.pvemu.game.fight.fightertype.MonsterFighter;
import org.pvemu.game.fight.fightertype.PlayerFighter;
import org.pvemu.game.fight.fightmode.DefianceFight;
import org.pvemu.game.fight.fightmode.PvMFight;
import org.pvemu.game.fight.teamtype.MonsterTeam;
import org.pvemu.game.fight.teamtype.PlayerTeam;
import org.pvemu.game.objects.map.GameMap;
import org.pvemu.game.objects.map.MapUtils;
import org.pvemu.game.objects.monster.MonsterGroup;
import org.pvemu.game.objects.monster.MonsterTemplate;
import org.pvemu.game.objects.player.Player;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.network.game.output.GameSendersRegistry;

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
                    new PlayerTeam((byte)0, p1.getID() << 16, p1.getCellId(), MapUtils.parseCellList(places[0], map)),
                    new PlayerTeam((byte)1, p2.getID() << 16, p2.getCellId(), MapUtils.parseCellList(places[1], map))
                },
                p1.getID()
        );
        
        map.addFight(fight);
        
        fight.setState(Fight.STATE_PLACE);
        
        fight.addFighterToTeamByNumber(new PlayerFighter(p1, fight), (byte)0);
        fight.addFighterToTeamByNumber(new PlayerFighter(p2, fight), (byte)1);
        
        return fight;
    }
    
    static public Fighter newFighter(Player player, Fight fight){
        return new PlayerFighter(player, fight);
    }
    
    static public PvMFight pvm(Player player, MonsterGroup group){
        GameMap map = player.getMap();
        map.removeMonsterGroup(group);
        GameSendersRegistry.getMap().removeGMable(map, group);
        
        String[] places = Utils.split(map.getModel().places, "|", 2);
        
        PvMFight fight = new PvMFight(
                map.getFreeFightId(), 
                new FightMap(map), 
                new FightTeam[]{
                    new PlayerTeam((byte)0, player.getID() << 16, player.getCellId(), MapUtils.parseCellList(places[0], map)),
                    new MonsterTeam((byte)1, group.getID() << 16, group.getCellId(), MapUtils.parseCellList(places[1], map))
                }, 
                player.getID()
        );
        
        map.addFight(fight);
        
        
        fight.setState(Fight.STATE_PLACE);
        
        fight.addFighterToTeamByNumber(newFighter(player, fight), (byte)0);
        
        for(MonsterTemplate template : group.getMonsters()){
            fight.addFighterToTeamByNumber(newMonsterFighter(template, fight), (byte)1);
        }
        
        return fight;
    }
    
    static public Fighter newMonsterFighter(MonsterTemplate template, Fight fight){
        return new MonsterFighter(fight.getNewId(), template, template.getBasicStats(), fight);
    }
}
