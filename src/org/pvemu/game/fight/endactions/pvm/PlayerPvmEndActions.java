/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.endactions.pvm;

import org.pvemu.game.fight.endactions.FighterEndActions;
import org.pvemu.game.fight.fightertype.PlayerFighter;
import org.pvemu.game.fight.fightmode.PvMFight;
import org.pvemu.game.objects.map.GameMap;
import org.pvemu.game.objects.map.MapCell;
import org.pvemu.game.objects.map.MapFactory;
import org.pvemu.game.objects.player.Player;
import org.pvemu.jelly.Loggin;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class PlayerPvmEndActions implements FighterEndActions<PvMFight, PlayerFighter>{

    @Override
    public Class<PlayerFighter> getFighterClass() {
        return PlayerFighter.class;
    }

    @Override
    public void apply(PvMFight fight, PlayerFighter fighter, boolean isWinner) {
        Player player = fighter.getPlayer();
        if(!isWinner){
            try{
                GameMap map = MapFactory.getById(player.getCharacter().startMap);
                MapCell cell = map.getCellById(player.getCharacter().startCell);
                player.setMap(map);
                player.setCell(cell);
            }catch(NullPointerException e){
                Loggin.error("Invalide start position for " + player.getCharacter(), e);
            }
        }
        
        player.setCurrentVita(fighter.getCurrentVita());
    }
    
}
