/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.map;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public interface Environment {
    /**
     * Test if the cell exists an is walkable
     * @param cell the cell to test
     * @return true if is walkable
     */
    public boolean canWalk(short cell);
    
    
    /**
     * Get the map width
     * @return width of the map
     */
    public byte getWidth();
    
    /**
     * Get the number of cells into the map
     * @return the number of cells
     */
    public short size();
}
