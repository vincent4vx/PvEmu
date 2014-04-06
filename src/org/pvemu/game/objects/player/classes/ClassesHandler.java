/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.player.classes;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class ClassesHandler {
    public static final byte CLASS_FECA = 1;
    public static final byte CLASS_OSAMODAS = 2;
    public static final byte CLASS_ENUTROF = 3;
    public static final byte CLASS_SRAM = 4;
    public static final byte CLASS_XELOR = 5;
    public static final byte CLASS_ECAFLIP = 6;
    public static final byte CLASS_ENIRIPSA = 7;
    public static final byte CLASS_IOP = 8;
    public static final byte CLASS_CRA = 9;
    public static final byte CLASS_SADIDA = 10;
    public static final byte CLASS_SACRIEUR = 11;
    public static final byte CLASS_PANDAWA = 12;
    public static final byte SEX_MALE = 0;
    public static final byte SEX_FEMALE = 1;
    
    
    //stats
    public static final short BASE_PA = 6;
    public static final short BONUS_PA_LVL100 = 1;
    public static final short BASE_PM = 3;
    public static final short BASE_PROS = 100;
    public static final short PROS_BONUS_ENU = 20;
    public static final short BASE_PODS = 1000;
    public static final short BASE_INVOC = 1;
    public static final short BASE_INIT = 100;
    public static final short BASE_VITA = 50;
    public static final short VITA_PER_LVL = 5;
    
    final static private ClassesHandler instance = new ClassesHandler();
    final private Map<Byte, ClassData> classes = new HashMap<>(12);

    private ClassesHandler() {
        registerClass(new Feca());
        registerClass(new Osamodas());
        registerClass(new Enutrof());
        registerClass(new Sram());
        registerClass(new Xelor());
        registerClass(new Ecaflip());
        registerClass(new Eniripsa());
        registerClass(new Iop());
        registerClass(new Cra());
        registerClass(new Sadida());
        registerClass(new Sacrieur());
        registerClass(new Pandawa());
    }
    
    public void registerClass(ClassData classData){
        classes.put(classData.id(), classData);
    }
    
    public ClassData getClass(byte id){
        return classes.get(id);
    }

    public static ClassesHandler instance() {
        return instance;
    }
}
