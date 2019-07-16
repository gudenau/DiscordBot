package net.gudenau.discord.bot.implementation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.dv8tion.jda.core.entities.Message;
import net.gudenau.discord.bot.IPlugin;
import net.gudenau.discord.bot.command.ICommand;
import net.gudenau.discord.bot.command.ICommandManager;
import net.gudenau.discord.bot.result.ColoredTextResult;

/**
 * Manages commands, execution and registration.
 * */
public class CommandManager implements ICommandManager{
    /**
     * The map of all commands.
     * */
    private final Map<String, Command> commands = new HashMap<>();
    
    /**
     * For an empty ping.
     * */
    private final String[] EMPTY_COMMAND_SPLIT = { "", "help" };
    
    /**
     * Our prefix for command execution.
     * */
    private String commandPrefix;
    
    /**
     * Currently registering plugin, null is for the core.
     * */
    private IPlugin currentPlugin;
    
    @Override
    public void register(ICommand command){
        var lowerName = command.getName().toLowerCase();
        if(commands.containsKey(lowerName)){
            System.err.printf(
                "Command %s already registered",
                command.getName()
            );
        }else{
            commands.put(lowerName, new Command(command, currentPlugin));
        }
    }
    
    /**
     * Handles a received message, and executes the relevant command.
     *
     * @param message A message
     * */
    void handleMessage(Message message){
        var content = message.getContentRaw().trim();
        if(!content.startsWith(commandPrefix)){
            return;
        }
        
        var split = content.split("\\s+");
        if(split.length == 1){
            split = EMPTY_COMMAND_SPLIT;
        }
        
        var command = commands.get(split[1].toLowerCase());
        if(command == null || command.command.shouldHide(message.getTextChannel(), message.getAuthor())){
            new ColoredTextResult(
                ColoredTextResult.COLOR_ERROR,
                "Unknown command \"%s\"",
                split[1]
            ).post(message.getTextChannel(), message.getMember());
            return;
        }
        
        message.getTextChannel().sendTyping().queue();
        var arguments = new String[split.length - 2];
        if(arguments.length != 0){
            System.arraycopy(split, 2, arguments, 0, arguments.length);
        }
        
        var result = command.command.execute(message, arguments);
        if(result != null){
            result.post(message.getTextChannel(), message.getMember());
        }
    }
    
    /**
     * Sets the bot's prefix.
     *
     * @param prefix The new prefix
     * */
    void setPrefix(String prefix){
        commandPrefix = prefix;
    }
    
    /**
     * Gets all of the registered commands.
     *
     * @return Registered commands
     * */
    public Map<String, Command> getCommands(){
        return Collections.unmodifiableMap(commands);
    }
    
    void setCurrentPlugin(IPlugin plugin){
        currentPlugin = plugin;
    }
    
    public static class Command{
        public final ICommand command;
        public final IPlugin owner;
    
        Command(ICommand command, IPlugin owner){
            this.command = command;
            this.owner = owner;
        }
    }
}
