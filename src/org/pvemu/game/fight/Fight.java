/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight;

import java.util.Collection;
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
    final private int id;
    final private FightMap map;
    final private FightTeam[] teams;
    final private FighterList fighters = new FighterList();
    final private static ScheduledExecutorService timers = Executors.newScheduledThreadPool(TIMERS_POOL_SIZE);
    private byte state;
    private long startTime = 0;
    
    final static public byte STATE_INIT     = 1;
    final static public byte STATE_PLACE    = 2;
    final static public byte STATE_ACTIVE   = 3;
    final static public byte STATE_FINISHED = 4;

    Fight(int id, FightMap map, FightTeam[] teams) {
        this.id = id;
        this.map = map;
        this.teams = teams;
        state = STATE_INIT;
        GameSendersRegistry.getFight().flagsToMap(map.getMap(), this);
    }
    
    void addFighterToTeamByNumber(Fighter fighter, byte number){
        addToTeam(fighter, teams[number]);
    }
    
    public void addToTeamById(Fighter fighter, int teamID){
        FightTeam team;
        int number = 0;
        
        do{
            team = teams[number++];
        }while(team.getId() != teamID && teams.length < number);
        
        addToTeam(fighter, team);
    }
    
    private void addToTeam(Fighter fighter, FightTeam team){
        Loggin.debug("new player (%s) into fight (there are %d players)", fighter.getName(), fighters.size());
        team.addFighter(fighter);
        fighters.add(fighter);
        map.addFighter(fighter);
        fighter.enterFight();
    }

    public FightTeam[] getTeams() {
        return teams;
    }
    
    public void startIfAllReady(){
        if(!fighters.isAllReady())
            return;
        
        startFight();
    }
    
    protected void startFight(){
        state = STATE_ACTIVE;
        startTime = System.currentTimeMillis();
        GameSendersRegistry.getFight().removeFlags(map.getMap(), id);
        GameSendersRegistry.getFight().startFight(this);
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
    
    public Collection<Fighter> getFighters(){
        return fighters;
    }

    public FightMap getMap() {
        return map;
    }

    public int getId() {
        return id;
    }
    
}
