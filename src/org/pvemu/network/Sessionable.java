/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network;

import org.apache.mina.core.session.IoSession;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public interface Sessionable {
    /**
     * @return the current mina session
     */
    public IoSession getSession();
}
