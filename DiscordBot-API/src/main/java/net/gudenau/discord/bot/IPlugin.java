package net.gudenau.discord.bot;

import net.gudenau.discord.bot.command.ICommandManager;

/**
 * Defines the entry point to a bot plugin.
 * */
public interface IPlugin{
    /**
     * Attempts to register this plugin with the bot.
     *
     * @param commandManager The @{link ICommandManager ICommandManager}
     *                       used to register commands.
     *
     * @return True on success, false on failure
     * */
    boolean register(ICommandManager commandManager);
    
    /**
     * Gets the name of this plugin.
     *
     * @return The plugin name
     * */
    String getName();
    
    /**
     * Is this plugin a secret?
     *
     * @return True if this plugin is a secret
     * */
    default boolean isSecret(){
        return false;
    }
}
