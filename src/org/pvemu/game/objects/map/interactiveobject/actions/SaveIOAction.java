package org.pvemu.game.objects.map.interactiveobject.actions;

import org.pvemu.game.objects.map.interactiveobject.InteractiveObject;
import org.pvemu.game.objects.player.Player;
import org.pvemu.jelly.filters.Filter;
import org.pvemu.jelly.filters.FilterFactory;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class SaveIOAction implements InteractiveObjectAction{

    @Override
    public int id() {
        return 44;
    }

    @Override
    public void startAction(InteractiveObject IO, Player player) {
        player.setStartPos(IO.getMap(), IO.getCell());
        GameSendersRegistry.getInformativeMessage().info(player.getSession(), 6);
    }

    @Override
    public Filter condition() {
        return FilterFactory.yesFilter();
    }

    @Override
    public void onError(InteractiveObject IO, Player player) {
    }
    
}
