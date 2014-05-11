/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.item;

import java.util.HashMap;
import java.util.Map;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.models.ItemTemplate;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class WeaponData {
    final private short PACost;
    final private short POMin;
    final private short POMax;
    final private byte criticalRate;
    final private byte failRate;
    final private short criticalBonus;
    final private boolean isTwoHanded;

    final static private Map<Integer, WeaponData> datasByItem = new HashMap<>();

    public WeaponData(short PACost, short POMin, short POMax, byte criticalRate, byte failRate, short criticalBonus, boolean isTwoHanded) {
        this.PACost = PACost;
        this.POMin = POMin;
        this.POMax = POMax;
        this.criticalRate = criticalRate;
        this.failRate = failRate;
        this.criticalBonus = criticalBonus;
        this.isTwoHanded = isTwoHanded;
    }

    public short getPACost() {
        return PACost;
    }

    public short getPOMin() {
        return POMin;
    }

    public short getPOMax() {
        return POMax;
    }

    public byte getCriticalRate() {
        return criticalRate;
    }

    public byte getFailRate() {
        return failRate;
    }

    public short getCriticalBonus() {
        return criticalBonus;
    }

    public boolean isIsTwoHanded() {
        return isTwoHanded;
    }

    public static Map<Integer, WeaponData> getDatasByItem() {
        return datasByItem;
    }
    
    /**
     * Parse weapon data
     * @param template the item template
     * @return 
     */
    static public WeaponData getWeaponDataByTemplate(ItemTemplate template){
        if(!datasByItem.containsKey(template.id)){
            try{
                String[] infos = Utils.split(template.weaponData, ";");
                short PACost = Short.parseShort(infos[0]);
                short POMin = Short.parseShort(infos[1]);
                short POMax = Short.parseShort(infos[2]);
                byte criticalRate = Byte.parseByte(infos[3]);
                byte failRate = Byte.parseByte(infos[4]);
                short criticalBonus = Short.parseShort(infos[5]);
                boolean isTwoHanded = infos[6].equals("1");
            
                datasByItem.put(template.id, new WeaponData(PACost, POMin, POMax, criticalRate, failRate, criticalBonus, isTwoHanded));
            }catch(Exception e){
                Loggin.error("Invalid weapon data for item " + template, e);
                return new WeaponData((short)0, (short)0, (short)0, (byte)0, (byte)0, (short)0, true);
            }
        }
        
        return datasByItem.get(template.id);
    }
}
