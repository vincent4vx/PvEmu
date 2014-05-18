/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.common.systats;

import org.pvemu.common.Shell;
import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.SyStats;
import org.pvemu.game.World;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class OnlineCount implements StatsLine{

    @Override
    public String label() {
        return I18n.tr(SyStats.ONLINE_COUNT);
    }

    @Override
    public void displayValue() {
        Shell.println(String.valueOf(World.instance().getOnlinePlayers().size()), Shell.GraphicRenditionEnum.GREEN);
    }
    
}
