/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.buttin.defiance;

import java.util.HashSet;
import org.pvemu.game.ExperienceHandler;
import org.pvemu.game.fight.fightmode.DefianceFight;
import org.pvemu.game.fight.Fighter;
import org.pvemu.game.fight.fightertype.PlayerFighter;
import org.pvemu.game.fight.buttin.FightButtin;
import org.pvemu.game.fight.buttin.FighterFightButtinFactory;
import org.pvemu.jelly.utils.Pair;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.models.Experience;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class PlayerFighterDefianceFightButtinFactory implements FighterFightButtinFactory<PlayerFighter, DefianceFight>{

    @Override
    public Class<PlayerFighter> fighterClass() {
        return PlayerFighter.class;
    }

    @Override
    public FightButtin makeButtin(DefianceFight fight, PlayerFighter fighter, byte winners, int winTeamLevel, int loseLeamLevel) {
        if(fighter.getTeam().getNumber() == winners)
            return new FightButtin(0, getWinExperience(fighter, winTeamLevel, loseLeamLevel), new HashSet<Pair<Integer, Integer>>());
        
        return FightButtin.emptyButtin();
    }
    
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
