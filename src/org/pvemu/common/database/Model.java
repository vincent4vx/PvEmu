package org.pvemu.common.database;

/**
 * Object representation of a table record
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public interface Model {

    /**
     * The primary key value
     * @return 
     */
    public abstract int getPk();

    /**
     * Clear the object after deleting into db
     */
    public abstract void clear();
}
