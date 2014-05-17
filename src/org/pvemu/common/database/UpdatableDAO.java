/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.common.database;

/**
 * a DAO witch can be updated
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 * @param <T> the generaed model
 */
abstract public class UpdatableDAO<T extends Model> extends CreatableDAO<T> {
    /**
     * update the object into the db
     * @param obj the object to update
     * @return true on success
     */
    abstract public boolean update(T obj);
}
