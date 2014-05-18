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
public enum SyStats implements Translation{
    STATISTICS("Statistics :"),
    SOCKETS_INFO("Sockets informations"),
    RECV_PACKETS_NUM("Received packets number"),
    SENT_PACKETS_NUM("Sent packets number"),
    CONNEXIONS_NUM("Connexions number"),
    SYSTEM_INFO("System informations"),
    RAM_USAGE("RAM usage"),
    THREADS_NUMBER("Active threads number"),
    CPU_USAGE("CPU usage"),
    GAME_INFO("Game informations"),
    ONLINE_COUNT("Online players number"),
    UPTIME("Uptime"),
    ERRORS_COUNT("Errors count"),
    ;

    final private String tr;

    private SyStats(String tr) {
        this.tr = tr;
    }
        
    @Override
    public String defaultTranslation() {
        return tr;
    }
    
}
