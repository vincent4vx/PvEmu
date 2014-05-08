package org.pvemu.commands;

import org.pvemu.commands.askers.Asker;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.pvemu.commands.argument.ArgumentList;
import org.pvemu.commands.argument.CommandArgumentException;
import org.pvemu.commands.parser.CommandParser;
import org.pvemu.commands.parser.ParserError;
import org.pvemu.jelly.Loggin;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class CommandsHandler {
    final static private CommandsHandler instance = new CommandsHandler();
    final private HashMap<String, Command> commands = new HashMap<>();
    final private CommandParser parser = new CommandParser();
    final private ExecutorService service = Executors.newSingleThreadExecutor();

    private CommandsHandler() {
        registerCommand(new HelpCommand());
        registerCommand(new SendCommand());
        registerCommand(new AliasCommand());
        registerCommand(new ExitCommand());
        registerCommand(new ExecCommand());
        registerCommand(new ItemCommand());
        registerCommand(new DebugCommand());
        registerCommand(new SaveCommand());
        registerCommand(new AddCommand());
        registerCommand(new TeleportCommand());
        registerCommand(new EchoCommand());
        registerCommand(new DumpCommand());
    }
    
    /**
     * Register a new command
     * @param cmd command to register
     */
    final public void registerCommand(Command cmd){
        commands.put(cmd.name(), cmd);
    }
    
    /**
     * Get the list of registered commands
     * @return the list of registered commands
     */
    final public Collection<Command> getCommandList(){
        return commands.values();
    }

    public CommandParser getParser() {
        return parser;
    }
    
    /**
     * Return the command by its name
     * @param name the command's name
     * @return the command object
     */
    final public Command getCommandByName(String name){
        return commands.get(name);
    }
    
    /**
     * Execute a command
     * @param commandLine the command line
     * @param asker the Asker
     */
    final public void execute(final String commandLine, final Asker asker){
        service.submit(new Runnable() {
            @Override
            public void run(){
                try{
                    ArgumentList args = parser.parseCommand(commandLine, asker);
                    Command cmd = commands.get(args.getCommand());

                    if(cmd == null || !asker.corresponds(cmd.conditions())){
                        asker.writeError("Commande indisponible !");
                        return;
                    }

                    cmd.perform(args, asker);
                }catch(CommandArgumentException e){
                    asker.writeError(e.getMessage());
                }catch(ParserError e){
                    asker.writeError(e.getMessage());
                }catch(Exception e){
                    Loggin.error("Error during executing `" + commandLine + "`", e);
                }
            }
        });
    }
    
    /**
     * Get the instance
     * @return 
     */
    final static public CommandsHandler instance(){
        return instance;
    }
}
