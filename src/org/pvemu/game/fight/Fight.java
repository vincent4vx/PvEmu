/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.pvemu.jelly.Constants;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class Fight {
    final static int TIMERS_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 4;
    final private Map<Integer, Fighter> team1 = new HashMap<>();
    final private Map<Integer, Fighter> team2 = new HashMap<>();
    final private List<Fighter> fighters = new ArrayList<>();
    final private static ScheduledExecutorService timers = Executors.newScheduledThreadPool(TIMERS_POOL_SIZE);
    final private Map<Short, Fighter> fightersByPos = new HashMap<>();
    
    public void addFighterToTeam1(Fighter fighter){
        team1.put(fighter.getID(), fighter);
        fighter.enterFight(this);
        addToFighterList(fighter);
    }
    
    public void addFighterToTeam2(Fighter fighter){
        team2.put(fighter.getID(), fighter);
        fighter.enterFight(this);
        addToFighterList(fighter);
    }
    
    /**
     * Add a fighter sorted by is initiative
     * @param fighter 
     */
    private void addToFighterList(Fighter fighter){
        synchronized(fighters){
            fighters.add(fighter);
            for(int i = fighters.size() - 1; i >= 0 && fighter.getInitiative() > fighters.get(i).getInitiative(); --i){
                Fighter tmp = fighters.get(i);
                fighters.set(i, fighter);
                fighters.set(i + 1, tmp);
            }
        }
    }
    
    void moveFighter(short from, short to, Fighter fighter){
        fightersByPos.remove(from);
        fightersByPos.put(to, fighter);
    }

    public static void startTimer(Runnable callback){
        timers.schedule(callback, Constants.TURN_TIME, TimeUnit.SECONDS);
    }
}
