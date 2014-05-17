/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.models;

import org.pvemu.common.database.Model;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ItemSet implements Model{
    public int id;
    public String name;
    public String items;
    public String bonus;
    
    @Override
    public int getPk() {
        return id;
    }

    @Override
    public void clear() {
        id = 0;
        items = null;
        bonus = null;
    }

    @Override
    public String toString() {
        return "ItemSet{" + "id=" + id + ", name=" + name + ", items=" + items + ", bonus=" + bonus + '}';
    }
    
}
