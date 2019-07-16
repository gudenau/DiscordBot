package net.gudenau.discord.bot.commands;

import net.dv8tion.jda.core.entities.Message;
import net.gudenau.discord.bot.command.ICommand;
import net.gudenau.discord.bot.implementation.CommandManager;
import net.gudenau.discord.bot.result.Result;
import net.gudenau.discord.bot.result.TextResult;

/**
 * Prints a list of commands, with descriptions.
 * */
public class HelpCommand implements ICommand{
    private final CommandManager commandManager;
    
    public HelpCommand(CommandManager commandManager){
        this.commandManager = commandManager;
    }
    
    @Override
    public Result execute(Message message, String... arguments){
        StringBuilder stringBuilder = new StringBuilder();
        
        for(var entry : commandManager.getCommands().entrySet()){
            var wrapper = entry.getValue();
            var command = wrapper.command;
            
            if(command.shouldHide(message.getTextChannel(), message.getAuthor())){
                continue;
            }
            
            var plugin = wrapper.owner;
            
            if(plugin == null){
                stringBuilder
                    .append(entry.getKey())
                    .append("\n\t")
                    .append(command.getDescription())
                    .append("\n");
            }else{
                stringBuilder
                    .append(entry.getKey())
                    .append(" (")
                    .append(plugin.getName())
                    .append(")\n\t")
                    .append(command.getDescription())
                    .append("\n");
            }
        }
        
        stringBuilder.setLength(stringBuilder.length() - 1);
        
        return new TextResult(stringBuilder);
    }
    
    @Override
    public String getDescription(){
        return "Lists commands.";
    }
    
    @Override
    public String getName(){
        return "help";
    }
}
