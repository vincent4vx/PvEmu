/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.jelly.filters;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public interface Filterable {
    /**
     * Teste la correspondance de l'objet courrant au filtre donné
     * @param filter
     * @return true si correspond, false sinon
     */
    public boolean corresponds(Filter filter);
}
