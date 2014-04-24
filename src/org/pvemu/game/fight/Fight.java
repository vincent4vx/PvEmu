/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.pvemu.game.effect.EffectData;
import org.pvemu.game.objects.item.types.Weapon;
import org.pvemu.game.objects.map.MapUtils;
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
    final private int initID;
    final private FighterList fighters = new FighterList();
    final private static ScheduledExecutorService timers = Executors.newScheduledThreadPool(TIMERS_POOL_SIZE);
    private byte state;
    private long startTime = 0;
    private ScheduledFuture timer;
    
    final static public byte STATE_INIT     = 1;
    final static public byte STATE_PLACE    = 2;
    final static public byte STATE_ACTIVE   = 3;
    final static public byte STATE_FINISHED = 4;

    Fight(int id, FightMap map, FightTeam[] teams, int initID) {
        this.id = id;
        this.map = map;
        this.teams = teams;
        this.initID = initID;
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
        GameSendersRegistry.getFight().turnList(this);
        nextFighter();
    }
    
    public void nextFighter(){
        if(timer != null && !timer.isCancelled() && !timer.isDone()){
            timer.cancel(true); //stop the timer if is not stoped
        }
        
        Fighter fighter = fighters.getCurrent();
        
        if(fighter != null){
            fighter.setCanPlay(false);
            fighter.endTurn();
            GameSendersRegistry.getFight().turnEnd(this, fighter.getID());
        }
        
        GameSendersRegistry.getFight().turnMiddle(this);
        
        fighter = fighters.getNext();
        fighter.setCanPlay(true);
        fighter.startTurn();
        GameSendersRegistry.getFight().turnStart(this, fighter.getID());
        
        timer = startTimer(new Runnable() {
            @Override
            public void run() {
                nextFighter();
            }
        }); //start another timer
    }

    public static ScheduledFuture startTimer(Runnable callback){
        return timers.schedule(callback, Constants.TURN_TIME, TimeUnit.SECONDS);
    }
    
    abstract public byte getType();
    abstract public int spec();
    abstract public boolean isDuel();
    abstract public boolean canCancel();
    abstract public boolean isHonnorFight();

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

    public int getInitID() {
        return initID;
    }
    
    public boolean canMove(Fighter fighter, short dest, short nbPM){
        return map.isFreeCell(dest) && fighter.getNumPM() >= nbPM;
    }
    
    public boolean canUseWeapon(Fighter caster, Weapon weapon, short cell){
        int dist = MapUtils.getDistanceBetween(map.getMap(), caster.getCellId(), cell);
        return caster.canPlay() 
                && caster.getNumPA() >= weapon.getWeaponData().getPACost()
                && dist >= weapon.getWeaponData().getPOMin()
                && dist <= weapon.getWeaponData().getPOMax();
    }
    
    public void applyEffects(Fighter caster, Set<EffectData> effects, short cell){
        if(effects.isEmpty())
            return;
        
        for(EffectData effect : effects){
            Collection<Short> cells = MapUtils.getCellsFromArea(
                    map.getMap(), 
                    cell, 
                    caster.getCellId(), 
                    effect.getArea()
            );
            
            Loggin.debug("Apply effect %d (min=%d, max=%d, area=%s) on cell %d", effect.getEffect().id(), effect.getMin(), effect.getMax(), effect.getArea(), cell);
            
            for(Fighter target : map.getFightersByCells(cells)){
                if(state == STATE_FINISHED)
                    return;
                
                if(!target.isAlive())
                    continue;
                
                effect.getEffect().applyToFighter(effect, caster, target);
                
                if(!target.isAlive())
                    onFighterDie(target);
            }
        }
    }
    
    public void onFighterDie(Fighter fighter){
        if(state == STATE_FINISHED)
            return;
        
        Loggin.debug("fighter %s die", fighter.getName());
        map.removeFighter(fighter);
        
        GameSendersRegistry.getEffect().fighterDie(this, fighter.getID());
        fighter.onDie();
        
        if(verifyEndOfGame()){
            endOfGame();
            return;
        }
        
        if(fighter == fighters.getCurrent())
            nextFighter();
    }
    
    private boolean verifyEndOfGame(){
        int count = 0;
        
        for(FightTeam team : teams){
            if(!team.isAllDead())
                ++count;
        }
        
        return count <= 1;
    }
    
    private byte getWinTeam(){
        for(FightTeam team : teams){
            if(!team.isAllDead())
                return team.getNumber();
        }
        
        return 0;
    }
    
    private void endOfGame(){
        Loggin.debug("end of fight %d", id);
        if(timer != null && !timer.isDone() && !timer.isCancelled())
            timer.cancel(true);
        
        fighters.getCurrent().setCanPlay(false);
        
        state = STATE_FINISHED;
        map.getMap().removeFight(this);
        
        timers.schedule(new Runnable() {
            @Override
            public void run() {
                byte winners = getWinTeam();
                endRewards(winners);

                for(Fighter fighter : fighters){
                    boolean winner = fighter.getTeam().getNumber() == winners;
                    fighter.onEnd(winner);
                    endAction(fighter, winner);
                }
            }
        }, 2, TimeUnit.SECONDS);
    }
    
    abstract protected void endAction(Fighter fighter, boolean isWinner);
    abstract protected void endRewards(byte winners);
    
    public long getTime(){
        return (System.currentTimeMillis() - startTime) / 1000;
    }
}
