/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.buttin.defiance;

import java.util.Collection;
import java.util.HashSet;
import org.pvemu.game.ExperienceHandler;
import org.pvemu.game.fight.FightTeam;
import org.pvemu.game.fight.fightmode.DefianceFight;
import org.pvemu.game.fight.Fighter;
import org.pvemu.game.fight.fightertype.PlayerFighter;
import org.pvemu.game.fight.buttin.FightButtin;
import org.pvemu.game.fight.buttin.FighterFightButtinFactory;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.common.Config;
import org.pvemu.common.utils.Pair;
import org.pvemu.common.utils.Utils;
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
    public FightButtin makeButtin(DefianceFight fight, PlayerFighter fighter, FightTeam winners, Collection<FightTeam> loosers) {
        int loosersLevel = 0;
        
        for(FightTeam team : loosers)
            loosersLevel += team.getTeamLevel();
        
        if(fighter.getTeam() == winners)
            return new FightButtin(0, getWinExperience(fighter, winners.getTeamLevel(), loosersLevel), new HashSet<Pair<Integer, Integer>>());
        
        return FightButtin.emptyButtin();
    }
    
    public long getWinExperience(PlayerFighter fighter, int winTeamLevel, int loseTeamLevel) {
        if(Config.RATE_DEFIANCE.getValue() <= 0)
            return 0;
        
        double fact = loseTeamLevel / winTeamLevel;
        fact *= Config.RATE_DEFIANCE.getValue();
        fact *= (10 / fighter.getLevel()) + 1;
        fact *= (double)(((double)fighter.getPlayer().getTotalStats().get(Stats.Element.SAGESSE) / 100) + (double)1);
        
        Pair<Experience, Experience> xps = ExperienceHandler.instance().getLevel(fighter.getLevel());
        long inter = xps.getSecond().player - xps.getFirst().player;
        
        long ret = (long)(Utils.randLong((long)(0.01 * inter), (long)(0.1 * inter)) * fact);
        
        if(ret < 0)
            ret = 0;
        
        return ret;
    }
    
}
