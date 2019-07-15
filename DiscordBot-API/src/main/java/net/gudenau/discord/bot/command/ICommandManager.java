package net.gudenau.discord.bot.command;

/**
 * The interface for managing commands.
 * */
public interface ICommandManager{
    /**
     * Registers a new command.
     *
     * @param name The name of the command
     * @param command The command instance
     * */
    void register(String name, ICommand command);
}
