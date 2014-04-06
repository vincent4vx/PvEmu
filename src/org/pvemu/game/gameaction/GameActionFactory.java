/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.gameaction;

import org.pvemu.game.objects.player.Player;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class GameActionFactory {
    static public GameActionData newCinematic(Player player, Byte cinematic){
        return new GameActionData(player, (short)2, new String[]{cinematic.toString()});
    }
}
