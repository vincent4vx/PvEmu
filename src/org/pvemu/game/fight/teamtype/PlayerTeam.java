/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.teamtype;

import java.util.List;
import org.pvemu.game.fight.FightTeam;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class PlayerTeam extends FightTeam{

    public PlayerTeam(byte number, int id, short cell, List<Short> places) {
        super(number, id, cell, places);
    }

    @Override
    public byte getType() {
        return 0;
    }
}
