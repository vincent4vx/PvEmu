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
public enum Commons implements Translation{
    LOADED_IN("%s loaded in %s ms"),
    SAVING("Saving..."),
    SAVING_OK("Saving completed"),
    BY(" by "),
    VERSION("Version %s"),
    FOR_DOFUS("For Dofus "),
    SERVER("Server"),
    STOPING("Stoping server..."),
    STOPED("Server stoped"),
    DESTROYING_WORLD("Destroying world..."),
    DISCONECTING("Disconecting clients : "),
    SAVING_PLAYERS("Saving players..."),
    DB_INIT("Database initialization : "),
    STOPING_REALM("Stoping realm : "),
    LAUNCHING_REALM("Launching realm : "),
    STOPING_GAME("Stoping game : "),
    LAUNCHING_GAME("Launching game : "),
    CANT_LAUNCH("Can't launch %s server at port %d"),
    PORT(" Port : %d"),
    LOADING("Loading %s : "),
    CONFIG("configuration"),
    INVALID_CONFIG_VALUE("Incorrect value '%s' for item %s"),
    PRELOADING("====> Preloading <===="),
    SPELLS("spells"),
    SPELLS_LOADED("%d spells loaded"),
    MONSTERS("monsters"),
    MONSTERS_LOADED("%d monsters loaded"),
    NPCS("npcs"),
    NPCS_LOADED("%d npcs loaded"),
    QUESTIONS("questions"),
    QUESTIONS_LOADED("%d questions loaded"),
    TRIGGERS("triggers"),
    TRIGGERS_LOADED("%d triggers loaded"),
    MAPS("maps"),
    MAPS_LOADED("%s maps loaded"),
    SCRIPTS("scripts"),
    STATS("stats"),
    STATS_LOADED("%d stats loaded"),
    ;

    final private String tr;

    private Commons(String tr) {
        this.tr = tr;
    }
        
    @Override
    public String defaultTranslation() {
        return tr;
    }
    
}
