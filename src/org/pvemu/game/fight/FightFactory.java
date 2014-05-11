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
    /**
     * create a new defiance fight
     * @param p1
     * @param p2
     * @return 
     */
    static public DefianceFight defiance(Player p1, Player p2){
        GameMap map = p1.getMap();
        
        if(!map.canFight())
            return null;
        
        boolean b = Utils.randBool();
        
        FightTeam team1 = new PlayerTeam((byte)(b ? 1 : 0), p1.getID() << 16, p1.getCellId(), map.getPlaces()[(b ? 1 : 0)]);
        FightTeam team2 = new PlayerTeam((byte)(b ? 0 : 1), p2.getID() << 16, p2.getCellId(), map.getPlaces()[(b ? 0 : 1)]);
        FightTeam[] teams = new FightTeam[2];
        teams[team1.getNumber()] = team1;
        teams[team2.getNumber()] = team2;
        
        DefianceFight fight = new DefianceFight(
                map.getFreeFightId(),
                new FightMap(map),
                teams,
                p1.getID()
        );
        
        map.addFight(fight);
        
        fight.setState(Fight.STATE_PLACE);
        
        fight.addToTeam(new PlayerFighter(p1, fight), team1);
        fight.addToTeam(new PlayerFighter(p2, fight), team2);
        
        return fight;
    }
    
    static public Fighter newFighter(Player player, Fight fight){
        return new PlayerFighter(player, fight);
    }
    
    /**
     * Create a new pvm fight
     * @param player
     * @param group
     * @return 
     */
    static public PvMFight pvm(Player player, MonsterGroup group){
        GameMap map = player.getMap();
        
        if(!map.canFight())
            return null;
        
        map.removeMonsterGroup(group);
        GameSendersRegistry.getMap().removeGMable(map, group);
        
        boolean b = Utils.randBool();
        
        FightTeam team1 = new PlayerTeam((byte)(b ? 1 : 0), player.getID() << 16, player.getCellId(), map.getPlaces()[(b ? 1 : 0)]);
        FightTeam team2 = new MonsterTeam((byte)(b ? 0 : 1), group.getID() << 16, group.getCellId(), map.getPlaces()[(b ? 0 : 1)]);
        
        FightTeam[] teams = new FightTeam[2];
        teams[team1.getNumber()] = team1;
        teams[team2.getNumber()] = team2;
        
        PvMFight fight = new PvMFight(
                map.getFreeFightId(), 
                new FightMap(map), 
                teams, 
                player.getID()
        );
        
        map.addFight(fight);
        
        
        fight.setState(Fight.STATE_PLACE);
        
        fight.addToTeam(newFighter(player, fight), team1);
        
        for(MonsterTemplate template : group.getMonsters()){
            fight.addToTeam(newMonsterFighter(template, fight), team2);
        }
        
        return fight;
    }
    
    static public Fighter newMonsterFighter(MonsterTemplate template, Fight fight){
        return new MonsterFighter(fight.getNewId(), template, template.getBasicStats(), fight);
    }
}
