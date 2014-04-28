/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.teamtype;

import java.util.List;
import org.pvemu.game.fight.FightTeam;
import org.pvemu.game.fight.Fighter;
import org.pvemu.game.fight.fightertype.MonsterFighter;
import org.pvemu.game.objects.monster.MonsterTemplate;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class MonsterTeam extends FightTeam{
    private long groupXp;
    private int minKamas, maxKamas;

    public MonsterTeam(byte number, int id, short cell, List<Short> places) {
        super(number, id, cell, places);
    }

    @Override
    public synchronized void addFighter(Fighter fighter) {
        super.addFighter(fighter);
        
        if(fighter instanceof MonsterFighter){ //should always be true !
            MonsterTemplate monster = ((MonsterFighter)fighter).getMonster();
            groupXp += monster.getXp();
            minKamas += monster.getModel().minKamas;
            maxKamas += monster.getModel().maxKamas;
        }
    }
    
    

    @Override
    public byte getType() {
        return 1;
    }

    public long getGroupXp() {
        return groupXp;
    }

    public int getMinKamas() {
        return minKamas;
    }

    public int getMaxKamas() {
        return maxKamas;
    }
}
