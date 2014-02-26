/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.item;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public enum ItemPositionsEnum {
    NOT_EQUIPED(-1),
    AMULETTE(0),
    ARME(1),
    ANNEAU1(2),
    CEINTURE(3),
    ANNEAU2(4),
    BOTTES(5),
    COIFFE(6),
    CAPE(7),
    FAMILIER(8),
    DOFUS1(9),
    DOFUS2(10),
    DOFUS3(11),
    DOFUS4(12),
    DOFUS5(13),
    DOFUS6(14),
    BOUCLIER(15),
    DRAGODINDE(16),
    CANDY(20),
    CANDY1(21),
    CANDY2(22),
    CANDY3(23),
    CANDY4(24),
    CANDY5(25),
    CANDY6(26),
    CANDY7(27),
    ;
    
    int posID;

    private ItemPositionsEnum(int posID) {
        this.posID = posID;
    }

    public int getPosID() {
        return posID;
    }
}
