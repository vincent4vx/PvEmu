/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.generators;

import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.FightTeam;
import org.pvemu.game.fight.Fighter;
import org.pvemu.game.fight.PlayerFighter;
import org.pvemu.jelly.Constants;
import org.pvemu.jelly.utils.Utils;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class FightGenerator {
    private String generateGMPacket(Fighter gmable){
        return "|+" + gmable.getCellId() + ";" + gmable.getOrientation() + ";0;"
                + gmable.getID() + ";" + gmable.getName() + ";";
    }
    
    public String generatePlayerGMPacket(PlayerFighter fighter){
        StringBuilder packet = new StringBuilder(generateGMPacket((Fighter)fighter));
        
        packet.append(fighter.getPlayer().getClassID()).append(';')
            .append(fighter.getPlayer().getGfxID()).append("^100;")
            .append(fighter.getPlayer().getSexe()).append(';')
            .append(fighter.getPlayer().getLevel()).append(';')
            .append("0,0,0,").append(fighter.getID()).append(';') //alignement
            .append(Utils.join(fighter.getPlayer().getColors(), ";")).append(';')
            .append(GeneratorsRegistry.getPlayer().generateAccessories(fighter.getPlayer())).append(';')
            .append(fighter.getTotalVita()).append(';')
            .append("6;3;") //TODO: PA/PM
            .append("0;0;0;0;0;0;0;") //TODO: resis
            .append(fighter.getTeam().getId()).append(';')
            .append(';') //TODO: mount
            ;
        
        return packet.toString();
    }
    
    public String generateJoinFightOk(Fight fight){
        return new StringBuilder().append(fight.getState()).append('|')
                .append(fight.canCancel() ? 1 : 0).append('|')
                .append(fight.isDuel() ? 1 : 0).append('|')
                .append(fight.spec()).append('|')
                .append(Constants.TURN_TIME).append('|')
                .append(fight.getType()).toString();
    }
    
    private String generateTeamElement(Fighter fighter){
        return new StringBuilder().append("|+")
                .append(fighter.getID()).append(';')
                .append(fighter.getName()).append(';')
                .append(fighter.getLevel()).toString();
    }
    
    public String generateAddToTeam(Fighter fighter){
        return fighter.getTeam().getId() + generateTeamElement(fighter);
    }
    
    public String generateTeamList(Fight fight, FightTeam team){
        StringBuilder packet = new StringBuilder();
        packet.append(team.getId());
        
        for(Fighter fighter : team.getFighters().values()){
            packet.append(generateTeamElement(fighter));
        }
        
        return packet.toString();
    }
    
    public String generateFightPlaces(String places, byte team){
        return places + "|" + team;
    }
}
