/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.endactions.defiance;

import org.pvemu.game.fight.endactions.FighterEndActions;
import org.pvemu.game.fight.fightertype.PlayerFighter;
import org.pvemu.game.fight.fightmode.DefianceFight;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class PlayerDefianceEndActions implements FighterEndActions<DefianceFight, PlayerFighter>{

    @Override
    public Class<PlayerFighter> getFighterClass() {
        return PlayerFighter.class;
    }

    @Override
    public void apply(DefianceFight fight, PlayerFighter fighter, boolean isWinner) {
    }
    
}
