/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.common.systats;

import org.pvemu.common.Shell;
import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.SyStats;
import org.pvemu.common.utils.Utils;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class Uptime implements StatsLine{

    @Override
    public String label() {
        return I18n.tr(SyStats.UPTIME);
    }

    @Override
    public void displayValue() {
        Shell.println(Utils.getUptime(), Shell.GraphicRenditionEnum.YELLOW, Shell.GraphicRenditionEnum.BOLD);
    }
    
}
