/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.fightmode;

import org.pvemu.actions.ActionsRegistry;
import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.FightMap;
import org.pvemu.game.fight.FightTeam;
import org.pvemu.game.fight.Fighter;
import org.pvemu.game.fight.fightertype.PlayerFighter;
import org.pvemu.game.objects.player.Player;

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

    @Override
    protected void endAction(Fighter fighter, boolean isWinner) {
        if(fighter instanceof PlayerFighter){
            Player player = ((PlayerFighter)fighter).getPlayer();
            ActionsRegistry.getMap().addPlayer(player.getMap(), player);
        }
    }
    
}
