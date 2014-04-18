/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.monster;

import java.util.List;
import org.pvemu.game.objects.map.GMable;
import org.pvemu.game.objects.map.GameMap;
import org.pvemu.game.objects.map.MapCell;
import org.pvemu.network.generators.GeneratorsRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class MonsterGroup implements GMable {
    final int id;
    final private List<MonsterTemplate> monsters;
    final private MapCell cell;
    final private GameMap map;

    MonsterGroup(int id, List<MonsterTemplate> monsters, MapCell cell, GameMap map) {
        this.monsters = monsters;
        this.cell = cell;
        this.map = map;
        this.id = id;
    }

    public List<MonsterTemplate> getMonsters() {
        return monsters;
    }

    public MapCell getCell() {
        return cell;
    }

    public GameMap getMap() {
        return map;
    }

    @Override
    public String getGMData() {
        return GeneratorsRegistry.getMonster().generateGM(this);
    }

    @Override
    public Integer getID() {
        return id;
    }

    @Override
    public short getCellId() {
        return cell.getID();
    }

    @Override
    public byte getOrientation() {
        return 2;
    }

    @Override
    public String getName() {
        StringBuilder sb = new StringBuilder();
        
        boolean first = true;
        for(MonsterTemplate monster : monsters){
            if(!first){
                sb.append(',');
            }
            
            sb.append(monster.getModel().id);
            first = false;
        }
        
        return sb.toString();
    }
    
    
    
}
