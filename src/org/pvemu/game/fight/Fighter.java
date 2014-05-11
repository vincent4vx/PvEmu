/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight;

import org.pvemu.game.fight.buff.BuffList;
import org.pvemu.game.fight.buttin.FightButtin;
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
abstract public class Fighter implements GMable, Creature {
    
    private boolean alive = true;
    private boolean zombie = false;
    private boolean canPlay = false;
    protected int currentVita;
    final private Stats baseStats;
    protected Stats totalStats;
    private int numPA;
    private int numPM;
    protected short cell;
    private FightTeam team;
    final protected Fight fight;
    private FightButtin fightButtin;
    final private BuffList buffList = new BuffList(this);

    public Fighter(Stats baseStats, Fight fight) {
        this.baseStats = baseStats;
        totalStats = new Stats(baseStats);
        this.fight = fight;
    }
    
    public void enterFight(){
        currentVita = totalStats.get(Stats.Element.VITA);
        numPA = totalStats.get(Stats.Element.PA);
        numPM = totalStats.get(Stats.Element.PM);
    }
    
    public void startTurn(){
        Loggin.debug("Start turn for %s", getName());
        
        buffList.applyBuffs();
    }
    
    public void endTurn(){        
        numPA = totalStats.get(Stats.Element.PA);
        numPM = totalStats.get(Stats.Element.PM);
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

    abstract public boolean isReady();

    /**
     * Get the value of cell
     *
     * @return the value of cell
     */
    @Override
    public short getCellId() {
        return cell;
    }

    /**
     * Change the player's cell
     * @param cell 
     */
    public void setCell(short cell) {
        this.cell = cell;
    }

    @Override
    public byte getOrientation() {
        return 1;
    }

    /**
     * Get the current fight
     * @return 
     */
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
    
    /**
     * Get the maximum vita
     * @return 
     */
    public int getTotalVita(){
        return totalStats.get(Stats.Element.VITA);
    }
    
    /**
     * Get the base stats (stats without buff)
     * @return 
     */
    public Stats getBaseStats(){
        return baseStats;
    }

    /**
     * Get the total stats (with buff)
     * @return 
     */
    @Override
    public Stats getTotalStats() {
        return totalStats;
    }

    /**
     * Get the current number of PA
     * @return 
     */
    public int getNumPA() {
        return numPA;
    }
    
    public void removePA(int num){
        numPA -= num;
        
        if(numPA < 0)
            numPA = 0;
    }

    /**
     * Get the current number of PM
     * @return 
     */
    public int getNumPM() {
        return numPM;
    }
    
    public void removePM(int num){
        numPM -= num;
        
        if(numPM < 0)
            numPM = 0;
    }
    
    /**
     * Remove vita from current vita
     * @param num 
     */
    public void removeVita(int num){
        if(num > currentVita)
            num = currentVita;
        
        currentVita -= num;
        
        if(currentVita <= 0){
            currentVita = 0;
            setZombie();
        }
        
        GameSendersRegistry.getEffect().removeVita(fight, getID(), num);
    }
    
    /**
     * Heal fighter
     * @param num 
     */
    public void addVita(int num){
        int max = totalStats.get(Stats.Element.VITA) - currentVita;
        
        if(num > max)
            num = max;
        
        currentVita += num;
        GameSendersRegistry.getEffect().addVita(fight, getID(), num);
    }

    /**
     * Get the value of alive
     *
     * @return the value of alive
     */
    public boolean isAlive() {
        return alive;
    }
    
    /**
     * Set the fighter as a zombie
     * The fighter is dead, but the fight has not yet made is mourning
     */
    public void setZombie(){
        alive = false;
        zombie = true;
    }

    /**
     * The player is not alive, but is not concidered as death by the fight
     * @return 
     * @see Fight#checkZombies() 
     */
    public boolean isZombie() {
        return zombie;
    }
    
    public void onDie(){
        zombie = false;
    }
    
    public void onEnd(boolean win){
        
    }
    
    /**
     * Test if the fighter can use the weapon on dest
     * @param weapon the weapon to use
     * @param dest the targeted cell
     * @return true if good
     */
    abstract public boolean canUseWeapon(Weapon weapon, short dest);
    
    /**
     * Use the weapon into the fight
     * @param weapon weapon to use
     * @param dest targeted cell
     * @return true on success
     */
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
    
    /**
     * Test if the fighter can use the spell
     * @param spell spell to use
     * @param dest the targeted cell
     * @return true if it's good
     */
    abstract public boolean canUseSpell(GameSpell spell, short dest);
    
    /**
     * Cast the spell into fight
     * @param spell spell to use
     * @param dest the targeted cell
     * @return true on success
     */
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

    /**
     * Get the current fight buttin
     * @return 
     */
    public FightButtin getFightButtin() {
        return fightButtin;
    }

    public void setFightButtin(FightButtin fightButtin) {
        this.fightButtin = fightButtin;
    }

    public BuffList getBuffList() {
        return buffList;
    }

    @Override
    public String toString() {
        return "Fighter{" + "name=" + getName() + ", alive=" + alive + ", canPlay=" + canPlay + ", currentVita=" + currentVita + ", numPA=" + numPA + ", numPM=" + numPM + ", cell=" + cell + ", team=" + team + ", fight=" + fight + '}';
    }
    
    
}
