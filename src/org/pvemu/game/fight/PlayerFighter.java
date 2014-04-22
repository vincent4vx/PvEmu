/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.gameaction.ActionPerformer;
import org.pvemu.game.gameaction.GameActionsManager;
import org.pvemu.game.gameaction.fight.FightActionsRegistry;
import org.pvemu.game.objects.dep.Creature;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.player.Player;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.output.GameSendersRegistry;
import org.pvemu.network.generators.GeneratorsRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class PlayerFighter extends Fighter implements ActionPerformer{
    final private Player player;
    final private GameActionsManager actionsManager = new GameActionsManager(FightActionsRegistry.instance());

    PlayerFighter(Player player, Fight fight) {
        super(player.getTotalStats(), fight);
        this.player = player;
        cell = player.getCellId();
    }

    @Override
    public void enterFight() {
        super.enterFight();
        player.getActionsManager().setBusy(true);
        player.getActionsManager().setInFight(true);
        SessionAttributes.FIGHTER.setValue(this, player.getSession());
        GameSendersRegistry.getMap().removeGMable(player.getMap(), player);
        player.getMap().removeGMable(player);
        GameSendersRegistry.getFight().joinFightOk(player.getSession(), fight);
        GameSendersRegistry.getFight().fightPlaces(this);
        GameSendersRegistry.getFight().GMList(player.getSession(), fight);
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
    
    @Override
    public IoSession getSession(){
        return player.getSession();
    }

    @Override
    public GameActionsManager getActionsManager() {
        return actionsManager;
    }

    @Override
    public int getLevel() {
        return 1;
    }
    
    
}