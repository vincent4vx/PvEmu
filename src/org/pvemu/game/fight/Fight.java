/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.pvemu.jelly.Constants;
import org.pvemu.jelly.Loggin;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class Fight {
    final static int TIMERS_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 4;
    final private FightMap map;
    final private FightTeam team1;
    final private FightTeam team2;
    final private List<Fighter> fighters = new ArrayList<>();
    final private static ScheduledExecutorService timers = Executors.newScheduledThreadPool(TIMERS_POOL_SIZE);
    private byte state;
    
    final static public byte STATE_INIT     = 1;
    final static public byte STATE_PLACE    = 2;
    final static public byte STATE_ACTIVE   = 3;
    final static public byte STATE_FINISHED = 4;

    Fight(FightMap map, FightTeam team1, FightTeam team2) {
        this.map = map;
        this.team1 = team1;
        this.team2 = team2;
        state = STATE_INIT;
    }
    
    public void addFighterToTeam1(Fighter fighter){
        addFighterToTeam(fighter, team1);
    }
    
    public void addFighterToTeam2(Fighter fighter){
        addFighterToTeam(fighter, team2);
    }
    
    private void addFighterToTeam(Fighter fighter, FightTeam team){
        Loggin.debug("new player (%s) into fight (there are %d players)", fighter.getName(), fighters.size());
        team.addFighter(fighter);
        addToFighterList(fighter);
        map.addFighter(fighter);
        fighter.enterFight();
    }

    public FightTeam getTeam1() {
        return team1;
    }

    public FightTeam getTeam2() {
        return team2;
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

    public static void startTimer(Runnable callback){
        timers.schedule(callback, Constants.TURN_TIME, TimeUnit.SECONDS);
    }
    
    abstract public byte getType();
    abstract public int spec();
    abstract public boolean isDuel();
    abstract public boolean canCancel();

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }
    
    public List<Fighter> getFighters(){
        return fighters;
    }

    public FightMap getMap() {
        return map;
    }
    
}
