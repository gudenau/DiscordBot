package net.gudenau.discord.bot;

import net.gudenau.discord.bot.command.ICommandManager;

public interface IPlugin{
    boolean register(ICommandManager commandManager);
    String getName();
}
