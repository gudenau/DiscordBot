package net.gudenau.discord.bot.command;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.dv8tion.jda.core.entities.Message;
import net.gudenau.discord.bot.result.ColoredTextResult;

/**
 * Manages commands, execution and registration.
 * */
public class CommandManager{
    /**
     * The map of all commands.
     * */
    private static final Map<String, Command> commands = new HashMap<>();
    
    /**
     * For an empty ping.
     * */
    private static final String[] EMPTY_COMMAND_SPLIT = { "", "help" };
    
    /**
     * Our prefix for command execution.
     * */
    private static String commandPrefix;
    
    /**
     * Registers a new command.
     *
     * @param name The name of the command
     * @param command The command instance
     * */
    public static void register(String name, Command command){
        var lowerName = name.toLowerCase();
        if(commands.containsKey(lowerName)){
            System.err.printf(
                "Command %s already registered",
                name
            );
        }else{
            commands.put(lowerName, command);
        }
    }
    
    /**
     * Handles a received message, and executes the relevant command.
     *
     * @param message A message
     * */
    public static void handleMessage(Message message){
        var content = message.getContentRaw().trim();
        if(!content.startsWith(commandPrefix)){
            return;
        }
        
        var split = content.split("\\s+");
        if(split.length == 1){
            split = EMPTY_COMMAND_SPLIT;
        }
        
        var command = commands.get(split[1].toLowerCase());
        if(command == null || (command.isNSFW() && !message.getTextChannel().isNSFW())){
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
        
        var result = command.execute(message, arguments);
        if(result != null){
            result.post(message.getTextChannel(), message.getMember());
        }
    }
    
    /**
     * Sets the bot's prefix.
     *
     * @param prefix The new prefix
     * */
    public static void setPrefix(String prefix){
        commandPrefix = prefix;
    }
    
    /**
     * Gets all of the registered commands.
     *
     * @return Registered commands
     * */
    static Map<String, Command> getCommands(){
        return Collections.unmodifiableMap(commands);
    }
}
