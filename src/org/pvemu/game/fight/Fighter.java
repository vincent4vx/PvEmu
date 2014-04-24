/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight;

import org.pvemu.game.gameaction.fight.FightActionsRegistry;
import org.pvemu.game.objects.dep.Creature;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.item.types.Weapon;
import org.pvemu.game.objects.map.GMable;
import org.pvemu.game.objects.spell.GameSpell;
import org.pvemu.jelly.Loggin;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class Fighter implements GMable {
    
    private boolean alive = true;
    private boolean canPlay = false;
    private boolean ready;
    protected int currentVita;
    final private Stats baseStats;
    protected Stats currentStats;
    private short numPA;
    private short numPM;
    protected short cell;
    private FightTeam team;
    final protected Fight fight;

    public Fighter(Stats baseStats, Fight fight) {
        this.baseStats = baseStats;
        this.fight = fight;
    }
    
    public void enterFight(){
        currentStats = new Stats(baseStats);
        currentVita = getTotalVita();
        numPA = currentStats.get(Stats.Element.PA);
        numPM = currentStats.get(Stats.Element.PM);
    }
    
    public void startTurn(){
        Loggin.debug("Start turn for %s", getName());
    }
    
    public void endTurn(){
        numPA = currentStats.get(Stats.Element.PA);
        numPM = currentStats.get(Stats.Element.PM);
    }

    public FightTeam getTeam() {
        return team;
    }

    public void setTeam(FightTeam team) {
        this.team = team;
    }

    public boolean canPlay() {
        return canPlay;
    }

    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    /**
     * Get the value of cell
     *
     * @return the value of cell
     */
    @Override
    public short getCellId() {
        return cell;
    }

    public void setCell(short cell) {
        this.cell = cell;
    }

    @Override
    public byte getOrientation() {
        return 1;
    }

    public Fight getFight() {
        return fight;
    }
    
    /**
     * Get the value of currentVita
     *
     * @return the value of currentVita
     */
    public int getCurrentVita() {
        return currentVita;
    }
    
    abstract public int getTotalVita();
    abstract public int getInitiative();
    
    public Stats getBaseStats(){
        return baseStats;
    }

    public Stats getCurrentStats() {
        return currentStats;
    }

    public short getNumPA() {
        return numPA;
    }
    
    public void removePA(short num){
        numPA -= num;
        
        if(numPA < 0)
            numPA = 0;
    }

    public short getNumPM() {
        return numPM;
    }
    
    public void removePM(short num){
        numPM -= num;
        
        if(numPM < 0)
            numPM = 0;
    }
    
    public void removeVita(short num){
        currentVita -= num;
        
        if(currentVita <= 0){
            currentVita = 0;
            alive = false;
        }
        
        GameSendersRegistry.getEffect().removeVita(fight, getID(), num);
    }

    /**
     * Get the value of alive
     *
     * @return the value of alive
     */
    public boolean isAlive() {
        return alive;
    }
    
    abstract public Creature getCreature();
    abstract public int getLevel();
    
    public void onDie(){
        
    }
    
    public void onEnd(boolean win){
        
    }
    
    abstract public boolean canUseWeapon(Weapon weapon, short dest);
    
    public boolean useWeapon(Weapon weapon, short dest){
        if(!canUseWeapon(weapon, dest))
            return false;
        
        GameSendersRegistry.getGameAction().unidentifiedGameActionToFight(
                fight, 
                FightActionsRegistry.WEAPON,
                getID(),
                dest
        );
        
        removePA(weapon.getWeaponData().getPACost());
        GameSendersRegistry.getEffect().removePAOnAction(
                fight,
                getID(),
                weapon.getWeaponData().getPACost()
        );
        
        fight.applyEffects(
                this, 
                weapon.getEffects(), 
                dest
        );
        
        return true;
    }
    
    abstract public boolean canUseSpell(GameSpell spell, short dest);
    
    public boolean castSpell(GameSpell spell, short dest){
        if(!canUseSpell(spell, dest))
            return false;
        
        GameSendersRegistry.getEffect().castSpell(
                fight, 
                getID(), 
                spell, 
                dest
        );
        
        removePA(spell.getPACost());
        GameSendersRegistry.getEffect().removePAOnAction(
                fight,
                getID(),
                spell.getPACost()
        );
        
        fight.applyEffects(this, spell.getEffects(), dest); //TODO critical / fail
        
        return true;
    }
    
    abstract public GameSpell getSpellById(int spellID);

    @Override
    public String toString() {
        return "Fighter{" + "name=" + getName() + ", alive=" + alive + ", canPlay=" + canPlay + ", currentVita=" + currentVita + ", numPA=" + numPA + ", numPM=" + numPM + ", cell=" + cell + ", team=" + team + ", fight=" + fight + '}';
    }
    
    
}
