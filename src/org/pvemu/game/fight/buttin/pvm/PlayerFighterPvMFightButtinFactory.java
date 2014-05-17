/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.buttin.pvm;

import java.util.Collection;
import java.util.HashSet;
import org.pvemu.game.fight.FightTeam;
import org.pvemu.game.fight.Fighter;
import org.pvemu.game.fight.buttin.FightButtin;
import org.pvemu.game.fight.buttin.FighterFightButtinFactory;
import org.pvemu.game.fight.fightertype.PlayerFighter;
import org.pvemu.game.fight.fightmode.PvMFight;
import org.pvemu.game.fight.teamtype.MonsterTeam;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.common.Config;
import org.pvemu.common.utils.Pair;
import org.pvemu.common.utils.Utils;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class PlayerFighterPvMFightButtinFactory implements FighterFightButtinFactory<PlayerFighter, PvMFight>{

    @Override
    public Class<PlayerFighter> fighterClass() {
        return PlayerFighter.class;
    }

    @Override
    public FightButtin makeButtin(PvMFight fight, PlayerFighter fighter, FightTeam winners, Collection<FightTeam> loosers) {
        if(fighter.getTeam() != winners)
            return FightButtin.emptyButtin();
        
        return new FightButtin(getWinKamas(fighter, winners, loosers), getWinXp(fighter, winners, loosers), new HashSet<Pair<Integer, Integer>>());
    }
    
    private int getWinKamas(PlayerFighter fighter, FightTeam winners, Collection<FightTeam> loosers){
        if(fighter.getTeam() != winners)
            return 0;
        
        int minKamas = 0;
        int maxKamas = 0;
        
        for(FightTeam team : loosers){
            if(team instanceof MonsterTeam){
                MonsterTeam t = (MonsterTeam)team;
                minKamas += t.getMinKamas();
                maxKamas += t.getMaxKamas();
            }
        }
        
        minKamas /= winners.size();
        maxKamas /= winners.size();
        
        return Utils.rand(minKamas, maxKamas);
    }
    
    private long getWinXp(PlayerFighter fighter, FightTeam winners, Collection<FightTeam> loosers){
        if(fighter.getTeam() != winners)
            return 0;
        
        double[] aBonus = new double[]{1, 1.1, 1.3, 2.2, 2.5, 2.8, 3.1, 3.5};
        int i = 0;
        for(Fighter f : winners.getFighters().values()){
            if(f.getLevel() > (winners.getLevelMax() / 3))
                ++i;
        }
        
        if(i > aBonus.length)
            i = aBonus.length - 1;
        
        double bonus = aBonus[i];
        
        int loosersLevel = 0;
        long groupXp = 0;
        
        for(FightTeam team : loosers){
            loosersLevel += team.getTeamLevel();
            
            if(team instanceof MonsterTeam){
                groupXp += ((MonsterTeam)team).getGroupXp();
            }
        }
        
        double teamLvlRatio = 1+((double)loosersLevel/(double)winners.getTeamLevel());
        
        if(teamLvlRatio < 1.3)
            teamLvlRatio = 1.3;
        
        double lvlRation = 1 + ((double)fighter.getLevel() / (double)winners.getTeamLevel());
        double coef = (fighter.getBaseStats().get(Stats.Element.SAGESSE) / 100) + 1;
        coef *= Config.RATE_PVM.getValue();
        
        return (long)(bonus * teamLvlRatio * lvlRation * coef * groupXp);
    }
}
