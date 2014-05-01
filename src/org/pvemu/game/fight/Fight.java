package org.pvemu.game.fight;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import org.pvemu.game.effect.EffectData;
import org.pvemu.game.fight.buttin.FightButtinFactory;
import org.pvemu.game.fight.endactions.EndActionsHandler;
import org.pvemu.jelly.Constants;
import org.pvemu.jelly.Loggin;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class Fight {
    final private int id;
    final private FightMap map;
    final private FightTeam[] teams;
    final private int initID;
    final private FighterList fighters = new FighterList();
    private byte state;
    private long startTime = 0;
    private ScheduledFuture timer;
    private int lastID = -1;
    private int startCountdown = Constants.START_FIGHT_TIME;
    private ScheduledFuture startCountdownTimer = null;
    
    final static public byte STATE_INIT     = 1;
    final static public byte STATE_PLACE    = 2;
    final static public byte STATE_ACTIVE   = 3;
    final static public byte STATE_FINISHED = 4;

    public Fight(int id, FightMap map, FightTeam[] teams, int initID) {
        this.id = id;
        this.map = map;
        this.teams = teams;
        this.initID = initID;
        state = STATE_INIT;
        GameSendersRegistry.getFight().flagsToMap(map.getMap(), this);
        FightUtils.startCountdownTimer(this);
    }
    
    public void addFighterToTeamByNumber(Fighter fighter, byte number){
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

    public void setStartCountdownTimer(ScheduledFuture startCountdownTimer) {
        this.startCountdownTimer = startCountdownTimer;
    }
    
    public void startFight(){
        if(startCountdownTimer != null)
            startCountdownTimer.cancel(true);
        
        state = STATE_ACTIVE;
        startTime = System.currentTimeMillis();
        GameSendersRegistry.getFight().removeFlags(map.getMap(), id);
        GameSendersRegistry.getFight().startFight(this);
        GameSendersRegistry.getFight().turnList(this);
        nextFighter();
    }
    
    public void nextFighter(){
        if(state == STATE_FINISHED)
            return;
        
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
        
        timer = FightUtils.turnTimer(this);
    }
    
    abstract public byte getType();
    abstract public int spec();
    abstract public boolean canReady();
    abstract public boolean canCancel();
    abstract public boolean isHonnorFight();

    public int getStartCountdown() {
        return startCountdown;
    }
    
    public int decrementCountdown(){
        return --startCountdown;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }
    
    public Collection<Fighter> getFighters(){
        return fighters;
    }

    public FightMap getFightMap() {
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
    
    public void applyEffects(Fighter caster, Set<EffectData> effects, short cell){
        if(effects.isEmpty())
            return;
        
        for(EffectData effect : effects){
            effect.getEffect().applyToFight(effect, this, caster, cell);
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
    
    private FightTeam getWinTeam(){
        FightTeam team = teams[0];
        
        while(team.isAllDead()){
            team = teams[team.getNumber() + 1];
        }
        
        return team;
    }
    
    private void endOfGame(){
        Loggin.debug("end of fight %d", id);
        if(timer != null && !timer.isDone() && !timer.isCancelled())
            timer.cancel(true);
        
        fighters.getCurrent().setCanPlay(false);
        
        state = STATE_FINISHED;
        map.getMap().removeFight(this);
        
        FightUtils.scheduleTask(new Runnable() {
            @Override
            public void run() {
                FightTeam winners = getWinTeam();
                endActions(winners);
                endRewards(winners);
            }
        }, 2);
    }
    
    private void endActions(FightTeam winners){
        for(Fighter fighter : fighters){
            fighter.onEnd(fighter.getTeam() == winners);
            EndActionsHandler.instance().applyEndActions(this, fighter, fighter.getTeam() == winners);
        }
    }
    
    private void endRewards(FightTeam winners){
        Collection<FightTeam> loosers = new HashSet<>();
        
        for(FightTeam team : teams){
            if(team != winners)
                loosers.add(team);
        }
        
        for(Fighter fighter : getFighters()){
            fighter.setFightButtin(FightButtinFactory.instance().getButtin(this, fighter, winners, loosers));
        }
        
        GameSendersRegistry.getFight().gameEnd(this, winners.getNumber());
    }
    
    public long getTime(){
        return (System.currentTimeMillis() - startTime) / 1000;
    }
    
    public int getNewId(){
        return --lastID;
    }
}
