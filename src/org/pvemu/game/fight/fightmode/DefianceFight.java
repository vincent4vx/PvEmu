/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.fightmode;

import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.FightMap;
import org.pvemu.game.fight.FightTeam;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class DefianceFight extends Fight{

    public DefianceFight(int id, FightMap map, FightTeam[] teams, int initID) {
        super(id, map, teams, initID);
    }

    @Override
    public byte getType() {
        return 0;
    }

    @Override
    public int spec() {
        return 0;
    }

    @Override
    public boolean canReady() {
        return true;
    }

    @Override
    public boolean canCancel() {
        return true;
    }

    @Override
    public boolean isHonnorFight() {
        return false;
    }
    
}
