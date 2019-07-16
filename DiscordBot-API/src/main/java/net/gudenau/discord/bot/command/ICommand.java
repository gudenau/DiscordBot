package net.gudenau.discord.bot.command;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
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
     * Checks if this command should be hidden from users.
     *
     * @return Should this command be hidden?
     * */
    default boolean shouldHide(TextChannel channel, User user){
        return isNSFW() && !channel.isNSFW();
    }
    
    /**
     * Gets the name of this command.
     *
     * @return The name of the plugin
     * */
    String getName();
    
    /**
     * A way to add a command with a lambda.
     * */
    class Lambda implements ICommand{
        private final String name;
        private final String description;
        private final Callback callback;
        private final boolean nsfw;
    
        /**
         * @param description The description of the command
         * @param callback The lambda to execute
         * */
        public Lambda(String name, String description, Callback callback){
            this(name, description, false, callback);
        }
    
        /**
         * @param description The description of the command
         * @param nsfw Is the command NSFW?
         * @param callback The lambda to execute
         * */
        public Lambda(String name, String description, boolean nsfw, Callback callback){
            this.name = name;
            this.description = description;
            this.nsfw = nsfw;
            this.callback = callback;
        }
    
        @Override
        public Result execute(Message message, String... arguments){
            return callback.invoke(message, arguments);
        }
    
        @Override
        public String getDescription(){
            return description;
        }
    
        @Override
        public boolean isNSFW(){
            return nsfw;
        }
    
        @Override
        public String getName(){
            return name;
        }
    
        @FunctionalInterface
        public interface Callback{
            Result invoke(Message message, String... args);
        }
    }
}
