package net.gudenau.discord.bot.command;

/**
 * The interface for managing commands.
 * */
public interface ICommandManager{
    /**
     * Registers a new command.
     *
     * @param command The command instance
     * */
    void register(ICommand command);
}
