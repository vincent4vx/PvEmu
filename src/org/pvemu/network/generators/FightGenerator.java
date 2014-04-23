/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.generators;

import java.util.Collection;
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
            .append(fighter.getTeam().getNumber()).append(';')
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
    
    public String generateChangePlace(int fighterID, short cellID){
        return "|" + fighterID + ";" + cellID;
    }
    
    public String generateAddFlag(Fight fight){
        StringBuilder packet = new StringBuilder();
        
        packet.append(fight.getId()).append(';').append(fight.getType());
        
        for(FightTeam team : fight.getTeams()){
            packet.append('|')
                    .append(team.getId()).append(';')
                    .append(team.getCell()).append(';')
                    .append(team.getType()).append(';')
                    .append(team.getAlignement());
        }
        
        return packet.toString();
    }
    
    public String generateReady(int id, boolean ready){
        return (ready ? "1" : "0") + id;
    }
    
    public String generateTurnList(Collection<Fighter> fighters){
        StringBuilder packet = new StringBuilder(fighters.size() * 4);
        
        for(Fighter fighter : fighters){
            packet.append('|').append(fighter.getID());
        }
        
        return packet.toString();
    }
    
    public String generateTurnMiddle(Collection<Fighter> fighters){
        StringBuilder packet = new StringBuilder(24 * fighters.size());
        
        for(Fighter fighter : fighters){
            packet.append('|')
                    .append(fighter.getID()).append(';');
            
            if(!fighter.isAlive()){
                packet.append('1');
                continue;
            }
            
            packet.append("0;")
                    .append(fighter.getCurrentVita()).append(';')
                    .append(fighter.getNumPA()).append(';')
                    .append(fighter.getNumPM()).append(';')
                    .append(fighter.getCellId()).append(';') //TODO: invisibility
                    .append(';')
                    .append(fighter.getTotalVita());
        }
        
        return packet.toString();
    }
    
    public String generateTurnStart(int fighterID){
        return fighterID + "|" + Constants.TURN_TIME * 1000;
    }
    
    public String generateGameEnd(Fight fight, byte winners){
        StringBuilder packet = new StringBuilder();
        
        packet.append(fight.getTime()).append('|')
                .append(fight.getInitID()).append('|')
                .append(fight.isHonnorFight() ? "1" : "0");
        
        for(Fighter fighter : fight.getFighters()){
            packet.append('|')
                    .append(fighter.getTeam().getNumber() == winners ? "2" : "0").append(';')
                    .append(fighter.getID()).append(';')
                    .append(fighter.getName()).append(';')
                    .append(fighter.getLevel()).append(';')
                    .append(fighter.isAlive() ? "0" : "1").append(';');
            
            if(fight.isHonnorFight()){
                
            }else{
                packet.append("0;0;0;0;0;0;") //minxp,curxp,maxxp,winxp,guildxp,mountxp
                        .append(";") //drop
                        .append("0"); //kamas
            }
        }
        
        return packet.toString();
    }
    
    public String generateClearFightMap(Collection<Fighter> fighters){
        StringBuilder packet = new StringBuilder(fighters.size() * 6);
        
        for(Fighter fighter : fighters){
            packet.append("|-").append(fighter.getID());
        }
        
        return packet.toString();
    }
}
