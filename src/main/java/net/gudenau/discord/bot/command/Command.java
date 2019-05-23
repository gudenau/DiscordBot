package net.gudenau.discord.bot.command;

import net.dv8tion.jda.core.entities.Message;
import net.gudenau.discord.bot.result.Result;

/**
 * The interface to implement commands.
 * */
public interface Command{
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
}
