/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.output;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.gameaction.GameActionsRegistry;
import org.pvemu.game.gameaction.JoinFightAction;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class DefianceSender {
    protected void defianceError(IoSession session, int playerID, char errType){
        GameSendersRegistry.getGameAction().unidentifiedGameAction(
                session, 
                GameActionsRegistry.JOIN_FIGHT,
                playerID,
                errType
        );
    }
    
    public void defianceCasterError(IoSession session, int playerID){
        defianceError(session, playerID, JoinFightAction.CANT_YOU_R_OCCUPED);
    }
    
    public void defianceTargetBusyError(IoSession session, int playerID){
        defianceError(session, playerID, JoinFightAction.CANT_YOU_OPPONENT_OCCUPED);
    }
    
    public void cancelDefiance(IoSession session, int player1, int player2){
        GameSendersRegistry.getGameAction().unidentifiedGameAction(
                session, 
                GameActionsRegistry.CANCEL_DEFIANCE, 
                player1,
                player2
        );
    }
}
