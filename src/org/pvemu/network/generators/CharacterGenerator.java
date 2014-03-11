/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.generators;

import java.util.HashMap;
import org.pvemu.game.objects.item.ItemPosition;
import org.pvemu.models.Character;
import org.pvemu.models.dao.DAOFactory;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class CharacterGenerator {
    public String generateAccessories(Character c){
        StringBuilder sb = new StringBuilder(20);
        
        HashMap<Byte, Integer> accessories = DAOFactory.inventory().getAccessoriesByPlayerId(c.id);
        
        for(ItemPosition pos : ItemPosition.getAccessoriePositions()){
            for(byte p : pos.getPosIds()){
                if(accessories.containsKey(p)){
                    sb.append(Integer.toHexString(accessories.get(p)));
                }
                sb.append(',');
            }
        }
        
        return sb.toString();
    }
}
