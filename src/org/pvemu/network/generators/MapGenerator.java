/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.generators;

import org.pvemu.game.objects.map.GMable;
import org.pvemu.game.objects.map.GameMap;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class MapGenerator {
    public String generateAllMapElem(GameMap map){
        StringBuilder sb = new StringBuilder(80 * map.getGMables().size());
        
        for(GMable ga : map.getGMables()){
            sb.append(generateSingleGM(ga));
        }
        
        return sb.toString();
    }
    
    public String generateSingleGM(GMable ga){
        return "|+" + ga.getGMData();
    }
}
