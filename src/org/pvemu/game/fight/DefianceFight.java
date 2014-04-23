/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight;

import org.pvemu.actions.ActionsRegistry;
import org.pvemu.game.objects.player.Player;
import org.pvemu.network.game.output.GameSendersRegistry;

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
    public boolean isDuel() {
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

    @Override
    protected void endRewards(byte winners) {
        GameSendersRegistry.getFight().gameEnd(this, winners);
    }
}
