/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight;

import org.pvemu.game.objects.dep.Creature;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.map.GMable;
import org.pvemu.network.generators.GeneratorsRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class Fighter implements GMable {
    
    private boolean alive = true;
    private boolean canPlay = false;
    protected int currentVita;
    final private Stats baseStats;
    protected Stats currentStats;
    private short numPA;
    private short numPM;
    private Fight fight;
    private short cell;
    final private byte team;

    public Fighter(Stats baseStats, byte team) {
        this.baseStats = baseStats;
        this.team = team;
    }
    
    public void enterFight(Fight fight){
        currentStats = new Stats(baseStats);
        currentVita = getTotalVita();
        this.fight = fight;
    }
    
    public void startTurn(){
        canPlay = true;
        
        Fight.startTimer(new Runnable() {
            @Override
            public void run() {
                endTurn();
            }
        });
    }
    
    public void endTurn(){
        canPlay = false;
    }

    public byte getTeam() {
        return team;
    }

    public boolean canPlay() {
        return canPlay;
    }

    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
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

    @Override
    public byte getOrientation() {
        return 1;
    }

    @Override
    public String getGMData() {
        return GeneratorsRegistry.getFight().generateGMPacket(this);
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

    public short getNumPM() {
        return numPM;
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
}
