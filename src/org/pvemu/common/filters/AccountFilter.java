/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.common.filters;

import org.pvemu.common.filters.comparators.Comparator;
import org.pvemu.common.filters.comparators.YesComparator;
import org.pvemu.models.Account;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class AccountFilter extends Filter<Account> {
    
    private Comparator<Integer> id = new YesComparator<>();
    private Comparator<Byte> level = new YesComparator<>();
    private Comparator<String> name = new YesComparator<>();
    private Comparator<String> pseudo = new YesComparator<>();

    /**
     * Set the value of pseudo
     *
     * @param pseudo new value of pseudo
     */
    public void setPseudo(Comparator<String> pseudo) {
        this.pseudo = pseudo;
    }


    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(Comparator<String> name) {
        this.name = name;
    }


    /**
     * Set the value of level
     *
     * @param level new value of level
     */
    public void setLevel(Comparator<Byte> level) {
        this.level = level;
    }


    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(Comparator<Integer> id) {
        this.id = id;
    }


    @Override
    public boolean corresponds(Account acc){
        return id.compare(acc.id)
                && pseudo.compare(acc.pseudo)
                && level.compare(acc.level)
                && name.compare(acc.account);
    }
}
