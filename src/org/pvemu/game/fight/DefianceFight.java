/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight;

import java.util.HashSet;
import org.pvemu.actions.ActionsRegistry;
import org.pvemu.game.ExperienceHandler;
import org.pvemu.game.objects.player.Player;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.utils.Pair;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.models.Experience;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class DefianceFight extends Fight{

    public DefianceFight(int id, FightMap map, FightTeam[] teams, int initID) {
        super(id, map, teams, initID);
    }

    @Override
    public byte getType() {
        return 0;
    }

    @Override
    public int spec() {
        return 0;
    }

    @Override
    public boolean isDuel() {
        return true;
    }

    @Override
    public boolean canCancel() {
        return true;
    }

    @Override
    public boolean isHonnorFight() {
        return false;
    }

    @Override
    protected void endAction(Fighter fighter, boolean isWinner) {
        if(fighter instanceof PlayerFighter){
            Player player = ((PlayerFighter)fighter).getPlayer();
            ActionsRegistry.getMap().addPlayer(player.getMap(), player);
        }
    }

    @Override
    protected void endRewards(byte winners) {
        int winTeamLevel = getTeams()[winners].getTeamLevel();
        int loseTeamLevel = 0;
        
        for(FightTeam team : getTeams()){
            if(team.getNumber() != winners)
                loseTeamLevel += team.getTeamLevel();
        }
        
        for(Fighter fighter : getFighters()){
            if(fighter.getTeam().getNumber() == winners){
                fighter.setFightButtin(new FightButtin(100, getWinExperience(fighter, winTeamLevel, loseTeamLevel), new HashSet<Pair<Integer, Integer>>()));
            }else{
                fighter.setFightButtin(FightButtin.emptyButtin());
            }
        }
        
        GameSendersRegistry.getFight().gameEnd(this, winners);
    }

    @Override
    public long getWinExperience(Fighter fighter, int winTeamLevel, int loseTeamLevel) {
        double fact = loseTeamLevel / winTeamLevel;
        fact *= 1;
        fact *= (10 / fighter.getLevel()) + 1;
        
        
        Pair<Experience, Experience> xps = ExperienceHandler.instance().getLevel(fighter.getLevel());
        long inter = xps.getSecond().player - xps.getFirst().player;
        
        long ret = (long)(Utils.randLong((long)(0.01 * inter), (long)(0.1 * inter)) * fact);
        
        if(ret < 0)
            ret = 0;
        
        return ret;
    }
    
}
