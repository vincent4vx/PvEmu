/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.generators;

import org.pvemu.game.fight.Fighter;
import org.pvemu.game.fight.PlayerFighter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class FightGenerator {
    public String generateGMPacket(Fighter gmable){
        return "+" + gmable.getCellId() + ";" + gmable.getOrientation() + ";0;"
                + gmable.getID() + ";" + gmable.getName() + ";";
    }
    
    public String generateGMPacket(PlayerFighter fighter){
        return generateGMPacket((Fighter)fighter)
                + fighter.getPlayer().getClassID() + ";"
                + fighter.getPlayer().getGfxID() + "^100;"
                + fighter.getPlayer().getSexe() + ";"
                + fighter.getPlayer().getLevel()
                ;
    }
}
