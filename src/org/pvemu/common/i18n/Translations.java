/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.common.i18n;

import org.pvemu.common.utils.IPair;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public interface Translations {
    /**
     * Get the language of this translations
     * @return string representation of language
     */
    public String lang();
    
    /**
     * the list of translations representing by a IPair
     * The first element is the element to translate, the second is the translation string
     * @return 
     */
    public IPair<Translation, String>[] entries();
}
