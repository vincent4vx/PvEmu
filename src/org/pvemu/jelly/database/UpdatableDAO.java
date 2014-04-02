/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.jelly.database;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class UpdatableDAO<T extends Model> extends CreatableDAO<T> {
    abstract public boolean update(T obj);
}
