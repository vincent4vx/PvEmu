/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.jelly.filters;

import org.pvemu.models.Account;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class AccountFilter extends Filter<Account> {
    
    private Integer id = null;

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    private Byte level = null;

    /**
     * Set the value of level
     *
     * @param level new value of level
     */
    public void setLevel(Byte level) {
        this.level = level;
    }

    private String name = null;

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    private String current_ip = null;

    /**
     * Set the value of current_ip
     *
     * @param current_ip new value of current_ip
     */
    public void setCurrent_ip(String current_ip) {
        this.current_ip = current_ip;
    }

    private String pseudo = null;

    /**
     * Set the value of pseudo
     *
     * @param pseudo new value of pseudo
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    @Override
    public boolean corresponds(Account acc){
        return (pseudo == null || acc.pseudo.equalsIgnoreCase(pseudo))
                || (name == null || acc.account.equalsIgnoreCase(name))
                || (level == null || level <= acc.level)
                || (current_ip == null || current_ip.equals(acc.getCurrent_ip()))
                || (id != null || id == acc.id);
    }
}
