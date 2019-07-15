package net.gudenau.discord.test;

import net.gudenau.discord.bot.IPlugin;
import net.gudenau.discord.bot.command.ICommand;
import net.gudenau.discord.bot.command.ICommandManager;
import net.gudenau.discord.bot.result.TextResult;

public class TestPlugin implements IPlugin{
    @Override
    public boolean register(ICommandManager commandManager){
        commandManager.register(
            "pluginTest",
            new ICommand.Lambda("This is from a plugin!", (message, params)->
                new TextResult("This is from a plugin!")
            )
        );
        
        return true;
    }
    
    @Override
    public String getName(){
        return "Test Plugin";
    }
}
