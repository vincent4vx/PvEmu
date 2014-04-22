/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.gameaction.game;

import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.FightFactory;
import org.pvemu.game.gameaction.GameAction;
import org.pvemu.game.gameaction.GameActionData;
import org.pvemu.game.objects.player.Player;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class JoinFightAction implements GameAction<Player>{
    final static public char CHALLENGE_FULL            = 'c',
                             TEAM_FULL                 = 't',
                             TEAM_DIFFERENT_ALIGNMENT  = 'a',
                             CANT_DO_BECAUSE_GUILD     = 'g',
                             CANT_DO_TOO_LATE          = 'l',
                             CANT_U_ARE_MUTANT         = 'm',
                             CANT_BECAUSE_MAP          = 'p',
                             CANT_BECAUSE_ON_RESPAWN   = 'r',
                             CANT_YOU_R_OCCUPED        = 'o',
                             CANT_YOU_OPPONENT_OCCUPED = 'z',
                             CANT_FIGHT                = 'h',
                             CANT_FIGHT_NO_RIGHTS      = 'i',
                             ERROR_21                  = 's',
                             SUBSCRIPTION_OUT          = 'n',
                             A_NOT_SUBSCRIB            = 'b',
                             TEAM_CLOSED               = 'f',
                             NO_ZOMBIE_ALLOWED         = 'd'
    ;

    @Override
    public short id() {
        return GameActionsRegistry.JOIN_FIGHT;
    }

    @Override
    public void start(GameActionData<Player> data) {
        int fightID;
        int teamID;
        
        try{
            fightID = Integer.parseInt(data.getArgument(0));
            teamID = Integer.parseInt(data.getArgument(1));
        }catch(NumberFormatException e){
            GameSendersRegistry.getFight().joinFightError(
                    data.getPerformer().getSession(), 
                    data.getPerformer().getID(),
                    CANT_FIGHT
            );
            return;
        }
        
        Fight fight = data.getPerformer().getMap().getFight(fightID);
        
        if(fight == null){
            GameSendersRegistry.getFight().joinFightError(
                    data.getPerformer().getSession(), 
                    data.getPerformer().getID(),
                    CANT_FIGHT
            );
            return;
        }
        
        fight.addToTeamById(FightFactory.newFighter(data.getPerformer(), fight), teamID);
    }

    @Override
    public void end(GameActionData data, boolean success, String[] args) {
    }
    
}
