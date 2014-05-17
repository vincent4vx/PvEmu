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
import org.pvemu.game.fight.fightertype.PlayerFighter;
import org.pvemu.game.gameaction.game.GameActionsRegistry;
import org.pvemu.game.objects.map.GameMap;
import org.pvemu.common.Loggin;
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
    
    public void joinFightError(IoSession session, int playerID, char error){
        GameSendersRegistry.getGameAction().unidentifiedGameAction(
                session, 
                GameActionsRegistry.JOIN_FIGHT, 
                playerID,
                error
        );
    }
    
    public void addToTeam(GameMap map, Fighter fighter){
        GamePacketEnum.ADD_TO_TEAM.sendToMap(
                map, 
                GeneratorsRegistry.getFight().generateAddToTeam(fighter)
        );
    }
    
    public void getAllTeams(IoSession session, Fight fight){
        for(FightTeam team : fight.getTeams()){
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
        
        GamePacketEnum.MAP_ELEM.sendToFight(fight, packet);
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
        GamePacketEnum.MAP_ELEM.sendToFight(fight, packet);
    }
    
    public void fightPlaces(PlayerFighter fighter){
        GamePacketEnum.FIGHT_PLACES.send(
                fighter.getSession(), 
                GeneratorsRegistry.getFight().generateFightPlaces(
                        fighter.getFight().getFightMap().getMap().getModel().places, 
                        fighter.getTeam().getNumber()
                )
        );
    }
    
    public void changePlaceError(IoSession session){
        GamePacketEnum.FIGHT_CHANGE_PLACE.send(session, "e");
    }
    
    public void changePlace(Fight fight, Fighter fighter){
        String packet = GeneratorsRegistry.getFight().generateChangePlace(fighter.getID(), fighter.getCellId());
        
        for(Fighter f : fight.getFighters()){
            if(f instanceof PlayerFighter){
                GamePacketEnum.FIGHT_CHANGE_PLACE.send(((PlayerFighter)f).getSession(), packet);
            }
        }
    }
    
    public void getFlags(IoSession session, Fight fight){
        GamePacketEnum.FIGHT_ADD_FLAG.send(
                session,
                GeneratorsRegistry.getFight().generateAddFlag(fight)
        );
    }
    
    public void flagsToMap(GameMap map, Fight fight){
        GamePacketEnum.FIGHT_ADD_FLAG.sendToMap(
                map,
                GeneratorsRegistry.getFight().generateAddFlag(fight)
        );
    }
    
    public void ready(Fighter fighter){
        GamePacketEnum.FIGHT_READY.sendToFight(
                fighter.getFight(), 
                GeneratorsRegistry.getFight().generateReady(fighter.getID(), fighter.isReady())
        );
    }
    
    public void removeFlags(GameMap map, int fightID){
        GamePacketEnum.FIGHT_REMOVE_FLAG.sendToMap(map, fightID);
    }
    
    public void startFight(Fight fight){
        GamePacketEnum.FIGHT_START.sendToFight(fight, "");
    }
    
    public void turnList(Fight fight){
        GamePacketEnum.FIGHT_TURN_LIST.sendToFight(
                fight, 
                GeneratorsRegistry.getFight().generateTurnList(fight.getFighters())
        );
    }
    
    public void turnMiddle(Fight fight){
        GamePacketEnum.FIGHT_TURN_MIDDLE.sendToFight(
                fight, 
                GeneratorsRegistry.getFight().generateTurnMiddle(fight.getFighters())
        );
    }
    
    public void turnStart(Fight fight, int fighter){
        GamePacketEnum.FIGHT_TURN_START.sendToFight(
                fight, 
                GeneratorsRegistry.getFight().generateTurnStart(fighter)
        );
    }
    
    public void turnEnd(Fight fight, int fighter){
        GamePacketEnum.FIGHT_TURN_FNISH.sendToFight(
                fight, 
                fighter
        );
    }
    
    public void gameEnd(Fight fight, byte winners){
        GamePacketEnum.FIGHT_END.sendToFight(
                fight, 
                GeneratorsRegistry.getFight().generateGameEnd(fight, winners)
        );
    }
    
    public void clearFightMap(Fight fight){
        GamePacketEnum.MAP_ELEM.sendToFight(
                fight, 
                GeneratorsRegistry.getFight().generateClearFightMap(fight.getFighters())
        );
    }
}
