/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight;

import org.apache.mina.core.session.IoSession;
import org.pvemu.actions.ActionsRegistry;
import org.pvemu.game.objects.dep.Creature;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.player.Player;
import org.pvemu.network.game.output.GameSendersRegistry;
import org.pvemu.network.generators.GeneratorsRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class PlayerFighter extends Fighter{
    final private Player player;

    PlayerFighter(Player player, Fight fight) {
        super(player.getTotalStats(), fight);
        this.player = player;
        cell = player.getCellId();
    }

    @Override
    public void enterFight() {
        super.enterFight();
        ActionsRegistry.getMap().removePlayer(player.getMap(), player);
        GameSendersRegistry.getFight().joinFightOk(player.getSession(), fight);
        GameSendersRegistry.getFight().fightPlaces(this);
        GameSendersRegistry.getFight().GMList(player.getSession(), fight);
        GameSendersRegistry.getFight().getAllTeams(player.getSession(), fight);
    }
    
    
    
    @Override
    public Integer getID() {
        return player.getID();
    }

    @Override
    public Creature getCreature() {
        return player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public int getTotalVita() {
        return player.getTotalStats().get(Stats.Element.VITA);
    }

    @Override
    public int getInitiative() {
        return player.getInitiative();
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public String getGMData() {
        return GeneratorsRegistry.getFight().generatePlayerGMPacket(this);
    }
    
    public IoSession getSession(){
        return player.getSession();
    }

    @Override
    public int getLevel() {
        return 1;
    }
    
    
}
