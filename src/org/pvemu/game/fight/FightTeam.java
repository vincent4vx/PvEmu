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
public class FightTeam {
    final private byte number;
    final private int id;
    final private short cell;
    final private Map<Integer, Fighter> fighters = new HashMap<>();
    final private List<Short> places;

    FightTeam(byte number, int id, short cell, List<Short> places) {
        if(id > 0) //set the id negative
            id = -id;
        
        this.number = number;
        this.id = id;
        this.cell = cell;
        this.places = places;
    }
    
    public void addFighter(Fighter fighter){
        fighters.put(fighter.getID(), fighter);
        fighter.setTeam(this);
        GameSendersRegistry.getFight().addToTeam(fighter.getFight().getMap().getMap(), fighter);
        fighter.setCell(fighter.getFight().getMap().getFreeRandomCell(places));
        GameSendersRegistry.getFight().GMToFight(fighter.getFight(), fighter);
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
    
    /**
     * @todo sub classes
     * @return 
     */
    public byte getType(){
        return 0;
    }
    
    public boolean isAllDead(){
        for(Fighter fighter : fighters.values()){
            if(fighter.isAlive())
                return false;
        }
        return true;
    }
    
    public int getTeamLevel(){
        int level = 0;
        
        for(Fighter fighter : fighters.values())
            level += fighter.getLevel();
        
        return level;
    }
    
}
