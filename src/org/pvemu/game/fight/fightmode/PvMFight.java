package org.pvemu.game.fight.fightmode;

import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.FightMap;
import org.pvemu.game.fight.FightTeam;
import org.pvemu.game.fight.Fighter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class PvMFight extends Fight{

    public PvMFight(int id, FightMap map, FightTeam[] teams, int initID) {
        super(id, map, teams, initID);
    }

    @Override
    public byte getType() {
        return 4;
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
        return false;
    }

    @Override
    public boolean isHonnorFight() {
        return false;
    }
    
}
