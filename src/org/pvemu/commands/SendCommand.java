package org.pvemu.commands;

import java.util.List;
import org.pvemu.commands.argument.ArgumentList;
import org.pvemu.commands.argument.CommandArgumentException;
import org.pvemu.commands.askers.Asker;
import org.pvemu.commands.parser.ParserError;
import org.pvemu.commands.parser.variable.VariableUtils;
import org.pvemu.game.objects.player.Player;
import org.pvemu.common.filters.Filter;
import org.pvemu.common.filters.FilterFactory;
import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.Commands;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class SendCommand extends Command {

    @Override
    public String name() {
        return "send";
    }

    @Override
    public void perform(ArgumentList args, Asker asker) throws CommandArgumentException{
        String packet = args.getArgument(1);
        List<Player> players;
        
        try {
            players = args.getPlayerList(2, VariableUtils.getMe(asker));
        } catch (ParserError ex) {
            asker.writeError(ex.getMessage());
            return;
        }
        
        for(Player player : players){
            player.getSession().write(packet);
            asker.write(I18n.tr(Commands.PACKET_SENT, player.getName()));
        }
    }

    @Override
    public Filter conditions() {
        return FilterFactory.adminConsoleFilter();
    }
    
}
