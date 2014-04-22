/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.generators;

import org.pvemu.models.Account;
import org.pvemu.models.Character;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class AccountGenerator {
    public String generateCharactersList(Account acc){
        if(acc.getCharacters().isEmpty())
            return "0";
        
        
        StringBuilder sb = new StringBuilder(160 * acc.getCharacters().size());
        sb.append(acc.getCharacters().size());
        
        for(Character c : acc.getCharacters().values()){
            sb.append('|');
            sb.append(c.id).append(';');
            sb.append(c.name).append(';');
            sb.append(c.level).append(';');
            sb.append(c.gfxid).append(';');
            sb.append((c.color1 != -1 ? Integer.toHexString(c.color1) : "-1")).append(';');
            sb.append((c.color2 != -1 ? Integer.toHexString(c.color2) : "-1")).append(';');
            sb.append((c.color3 != -1 ? Integer.toHexString(c.color3) : "-1")).append(';');
            sb.append(GeneratorsRegistry.getCharacter().generateAccessories(c)).append(';');
            sb.append(0).append(';');
            sb.append("1;");//ServerID
            sb.append(';');//DeathCount	this.deathCount;
            sb.append(';');//LevelMax
        }
        
        
        return sb.toString();
    }
    
    public String generateServerList(long premiumTime, byte serverID, int nbChar){
        return premiumTime + "|" + serverID + "," + nbChar;
    }
}
