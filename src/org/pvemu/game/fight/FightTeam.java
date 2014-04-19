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
    final private byte id;
    final private Map<Integer, Fighter> fighters = new HashMap<>();
    final private List<Short> places;

    FightTeam(byte id, List<Short> places) {
        this.id = id;
        this.places = places;
    }
    
    public void addFighter(Fighter fighter){
        fighters.put(fighter.getID(), fighter);
        fighter.setTeam(this);
        GameSendersRegistry.getFight().addToTeam(fighter.getFight(), fighter);
        fighter.setCell(fighter.getFight().getMap().getFreeRandomCell(places));
        GameSendersRegistry.getFight().GMToFight(fighter.getFight(), fighter);
    }

    public byte getId() {
        return id;
    }

    public Map<Integer, Fighter> getFighters() {
        return fighters;
    }
    
    public boolean isEmpty(){
        return fighters.isEmpty();
    }

    public List<Short> getPlaces() {
        return places;
    }
    
}
