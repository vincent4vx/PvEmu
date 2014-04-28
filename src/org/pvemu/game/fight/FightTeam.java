/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class FightTeam {
    final private byte number;
    final private int id;
    final private short cell;
    final private Map<Integer, Fighter> fighters = new HashMap<>();
    final private List<Short> places;
    private int teamLevel = 0;
    private int levelMax = 0;

    public FightTeam(byte number, int id, short cell, List<Short> places) {
        if(id > 0) //set the id negative
            id = -id;
        
        this.number = number;
        this.id = id;
        this.cell = cell;
        this.places = places;
    }
    
    synchronized public void addFighter(Fighter fighter){
        fighters.put(fighter.getID(), fighter);
        fighter.setTeam(this);
        GameSendersRegistry.getFight().addToTeam(fighter.getFight().getMap().getMap(), fighter);
        fighter.setCell(fighter.getFight().getMap().getFreeRandomCell(places));
        GameSendersRegistry.getFight().GMToFight(fighter.getFight(), fighter);
        teamLevel += fighter.getLevel();
        
        if(fighter.getLevel() > levelMax)
            levelMax = fighter.getLevel();
    }

    public int getId() {
        return id;
    }

    public byte getNumber() {
        return number;
    }

    public Map<Integer, Fighter> getFighters() {
        return fighters;
    }
    
    public boolean isEmpty(){
        return fighters.isEmpty();
    }
    
    public boolean isFull(){
        return fighters.size() >= places.size();
    }

    public List<Short> getPlaces() {
        return places;
    }

    public short getCell() {
        return cell;
    }
    
    /**
     * @todo sub classes for defiance / agro / pvm
     * @return 
     */
    public byte getAlignement(){
        return -1;
    }
    
    abstract public byte getType();
    
    public boolean isAllDead(){
        for(Fighter fighter : fighters.values()){
            if(fighter.isAlive())
                return false;
        }
        return true;
    }
    
    public int getTeamLevel(){
        return teamLevel;
    }

    public int getLevelMax() {
        return levelMax;
    }
    
    public int size(){
        return fighters.size();
    }
}
