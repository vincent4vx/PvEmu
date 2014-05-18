/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.common.i18n.translation;

import org.pvemu.common.i18n.Translation;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public enum Scripting implements Translation{
    EXEC_ERROR("Error during execution of '%s'"),
    CANT_LOAD("Can't load script file '%s'"),
    ;
        
    final private String tr;

    private Scripting(String tr) {
        this.tr = tr;
    }

    @Override
    public String defaultTranslation() {
        return tr;
    }
    
}
