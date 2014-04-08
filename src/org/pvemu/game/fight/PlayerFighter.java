/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight;

import org.pvemu.game.objects.dep.Creature;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.player.Player;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class PlayerFighter extends Fighter{
    final private Player player;

    public PlayerFighter(Player player, byte team) {
        super(player.getTotalStats(), team);
        this.player = player;
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
    
}
