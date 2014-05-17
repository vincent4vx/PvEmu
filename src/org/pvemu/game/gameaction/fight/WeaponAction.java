/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.gameaction.fight;

import java.util.List;
import org.pvemu.game.fight.fightertype.PlayerFighter;
import org.pvemu.game.gameaction.GameAction;
import org.pvemu.game.gameaction.GameActionData;
import org.pvemu.game.objects.item.GameItem;
import org.pvemu.game.objects.item.ItemPosition;
import org.pvemu.game.objects.item.factory.ItemsFactory;
import org.pvemu.game.objects.item.types.Weapon;
import org.pvemu.common.Loggin;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class WeaponAction implements GameAction<PlayerFighter>{

    @Override
    public short id() {
        return FightActionsRegistry.WEAPON;
    }

    @Override
    public void start(GameActionData<PlayerFighter> data) {
        if(!data.getPerformer().canPlay()){
            GameSendersRegistry.getGameAction().error(data.getPerformer().getSession());
            return;
        }
        
        short cell;
        
        try{
            cell = Short.parseShort(data.getArgument(0));
        }catch(NumberFormatException e){
            GameSendersRegistry.getGameAction().error(data.getPerformer().getSession());
            return;
        }
        
        Loggin.debug("%s use weapon on cell %d", data.getPerformer().getName(), cell);
        
        List<GameItem> list = data.getPerformer().getPlayer().getInventory().getItemsOnPos(ItemPosition.WEAPON);
        
        if(list.isEmpty()){
            list.add(ItemsFactory.getPunch());
        }
        
        Weapon weapon = (Weapon)list.get(0);
        
        
        GameSendersRegistry.getGameAction().gameActionStartToFight(
                data.getPerformer().getFight(), 
                data.getPerformer().getID()
        );
        
        if(!data.getPerformer().useWeapon(weapon, cell)){
            GameSendersRegistry.getGameAction().error(data.getPerformer().getSession());
        }
        
        GameSendersRegistry.getGameAction().gameActionFinishToFight(
                data.getPerformer().getFight(), 
                0, 
                data.getPerformer().getID()
        );
    }

    @Override
    public void end(GameActionData<PlayerFighter> data, boolean success, String[] args) {
    }
    
}
