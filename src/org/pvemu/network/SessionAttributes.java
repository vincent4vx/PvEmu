/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.fight.PlayerFighter;
import org.pvemu.game.objects.player.Player;
import org.pvemu.models.Account;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class SessionAttributes<T> {
    static final public SessionAttributes<String> CONNEXION_KEY = new SessionAttributes<>();
    static final public SessionAttributes<Account> ACCOUNT = new SessionAttributes<>();
    static final public SessionAttributes<Player> PLAYER = new SessionAttributes<>();
    static final public SessionAttributes<PlayerFighter> FIGHTER = new SessionAttributes<>();
    
    /**
     * Constructeur privé
     */
    private SessionAttributes(){}
    
    /**
     * Retourne la valeur stocké dans la session
     * @param session la session courrante
     * @return l'objet stocké, null si inexistant
     */
    public T getValue(IoSession session){
        return (T)session.getAttribute(this);
    }
    
    /**
     * Stocke une valeur dans la session
     * @param value la valeur à stocker
     * @param session la session courante
     */
    public void setValue(T value, IoSession session){
        session.setAttribute(this, value);
    }
    
    /**
     * Remove an attribute stored in the session
     * @param session 
     */
    public void removeValue(IoSession session){
        session.removeAttribute(this);
    }
    
    /**
     * Teste si une valeur est stocké dans la session
     * @param session la session courante
     * @return true si l'information existe, false sinon
     */
    public boolean exists(IoSession session){
        return session.containsAttribute(this);
    }
}
