/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class DefianceFight extends Fight{

    public DefianceFight(FightMap map, FightTeam team1, FightTeam team2) {
        super(map, team1, team2);
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
    public boolean isDuel() {
        return true;
    }

    @Override
    public boolean canCancel() {
        return true;
    }
    
    
}
