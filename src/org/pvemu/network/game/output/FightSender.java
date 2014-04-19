/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.output;

import java.util.List;
import org.apache.mina.core.session.IoSession;
import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.FightTeam;
import org.pvemu.game.fight.Fighter;
import org.pvemu.game.fight.PlayerFighter;
import org.pvemu.game.objects.player.Player;
import org.pvemu.jelly.Loggin;
import org.pvemu.network.game.GamePacketEnum;
import org.pvemu.network.generators.GeneratorsRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class FightSender {
    public void joinFightOk(IoSession session, Fight fight){
        GamePacketEnum.FIGHT_JOIN_OK.send(
                session, 
                GeneratorsRegistry.getFight().generateJoinFightOk(fight)
        );
    }
    
    public void addToTeam(Fight fight, Fighter fighter){
        String packet = GeneratorsRegistry.getFight().generateAddToTeam(fighter);
        
        for(Fighter f : fight.getFighters()){
            if(f instanceof PlayerFighter){
                Player p = (Player)f.getCreature();
                GamePacketEnum.ADD_TO_TEAM.send(p.getSession(), packet);
            }
        }
    }
    
    public void getAllTeams(IoSession session, Fight fight){
        for(FightTeam team : new FightTeam[]{fight.getTeam1(), fight.getTeam2()}){
            if(team.isEmpty()){
                Loggin.debug("empty team : %d", team.getId());
                continue;
            }
            GamePacketEnum.ADD_TO_TEAM.send(
                    session,
                    GeneratorsRegistry.getFight().generateTeamList(fight, team)
            );
        }
    }
    
    public void GMListToFight(Fight fight, List<Fighter> fighters){
        StringBuilder packet = new StringBuilder();
        
        for(Fighter fighter : fighters){
            packet.append(fighter.getGMData());
        }
        
        for(Fighter fighter : fight.getFighters()){
            if(fighter instanceof PlayerFighter){
                Player player = (Player)fighter.getCreature();
                GamePacketEnum.MAP_ELEM.send(player.getSession(), packet);
            }
        }
    }
    
    public void GMList(IoSession session, Fight fight){
        if(fight.getFighters().isEmpty()){
            Loggin.debug("empty fight !");
            return;
        }
        
        StringBuilder packet = new StringBuilder();
        
        for(Fighter fighter : fight.getFighters()){
            packet.append(fighter.getGMData());
        }
        
        GamePacketEnum.MAP_ELEM.send(session, packet);
    }
    
    public void GMToFight(Fight fight, Fighter fighter){
        String packet = fighter.getGMData();
        
        for(Fighter f : fight.getFighters()){
            if(f instanceof PlayerFighter){
                Player player = (Player)f.getCreature();
                GamePacketEnum.MAP_ELEM.send(player.getSession(), packet);
            }
        }
    }
    
    public void fightPlaces(PlayerFighter fighter){
        GamePacketEnum.FIGHT_PLACES.send(
                fighter.getSession(), 
                GeneratorsRegistry.getFight().generateFightPlaces(
                        fighter.getFight().getMap().getMap().getModel().places, 
                        fighter.getTeam().getId()
                )
        );
    }
}
