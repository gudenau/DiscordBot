package net.gudenau.discord.bot.command;

import net.dv8tion.jda.core.entities.Message;
import net.gudenau.discord.bot.result.Result;

/**
 * The interface to implement commands.
 * */
public interface ICommand{
    /**
     * Executes the command.
     *
     * @param message The original message
     * @param arguments The arguments the user provided
     *
     * @return The result of the command
     * */
    Result execute(Message message, String ... arguments);
    
    /**
     * Returns the description of this command.
     *
     * @return The description of this command
     * */
    String getDescription();
    
    /**
     * Return true if this is a NSFW only command.
     *
     * @return Is this NSFW?
     * */
    default boolean isNSFW(){
        return false;
    }
    
    /**
     * A way to add a command with a lambda.
     * */
    class Lambda implements ICommand{
        private final String description;
        private final Callback callback;
    
        /**
         * @param description The description of the command
         * @param calllback The lambda to execute
         * */
        public Lambda(String description, Callback calllback){
            this.description = description;
            this.callback = calllback;
        }
    
        @Override
        public Result execute(Message message, String... arguments){
            return callback.invoke(message, arguments);
        }
    
        @Override
        public String getDescription(){
            return description;
        }
        
        @FunctionalInterface
        public interface Callback{
            Result invoke(Message message, String... args);
        }
    }
}
