/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
class FighterList implements Collection<Fighter>{
    final private List<Fighter> fighters = new ArrayList<>();
    private int currentKey = -1;
    private Fighter current = null;

    @Override
    public Iterator<Fighter> iterator() {
        return fighters.iterator();
    }
    
    Fighter getNext(){
        if(fighters.size() < 1)
            return null;
        
        do{
            currentKey = (currentKey + 1) % fighters.size();
            current = fighters.get(currentKey);
        }while(!current.isAlive());
        
        return current;
    }

    Fighter getCurrent() {
        return current;
    }

    @Override
    public boolean isEmpty() {
        return fighters.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return fighters.contains(o);
    }

    @Override
    public Object[] toArray() {
        return fighters.toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return fighters.toArray(ts);
    }

    @Override
    public boolean add(Fighter fighter) {
        synchronized(fighters){
            fighters.add(fighter);
            for(int i = fighters.size() - 2; i >= 0 && fighter.getInitiative() > fighters.get(i).getInitiative(); --i){
                Fighter tmp = fighters.get(i);
                fighters.set(i, fighter);
                fighters.set(i + 1, tmp);
            }
        }
        
        return true;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsAll(Collection<?> clctn) {
        return fighters.containsAll(clctn);
    }

    @Override
    public boolean addAll(Collection<? extends Fighter> clctn) {
        for(Fighter fighter : clctn){
            add(fighter);
        }
        
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> clctn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean retainAll(Collection<?> clctn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        fighters.clear();
        current = null;
        currentKey = -1;
    }

    @Override
    public int size() {
        return fighters.size();
    }
    
    boolean isAllReady(){
        for(Fighter fighter : fighters){
            if(!fighter.isReady())
                return false;
        }
        return true;
    }
    
}
