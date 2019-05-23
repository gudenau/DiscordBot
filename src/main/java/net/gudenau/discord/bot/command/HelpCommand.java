package net.gudenau.discord.bot.command;

import net.dv8tion.jda.core.entities.Message;
import net.gudenau.discord.bot.result.Result;
import net.gudenau.discord.bot.result.TextResult;

/**
 * Prints a list of commands, with descriptions.
 * */
public class HelpCommand implements Command{
    @Override
    public Result execute(Message message, String... arguments){
        StringBuilder stringBuilder = new StringBuilder();
        
        boolean nsfw = message.getTextChannel().isNSFW();
        
        for(var entry : CommandManager.getCommands().entrySet()){
            var command = entry.getValue();
            
            if(!nsfw && command.isNSFW()){
                continue;
            }
            
            stringBuilder
                .append(entry.getKey())
                .append("\n\t")
                .append(command.getDescription())
                .append("\n");
        }
        
        stringBuilder.setLength(stringBuilder.length() - 1);
        
        return new TextResult(stringBuilder.toString());
    }
    
    @Override
    public String getDescription(){
        return "Lists commands";
    }
}
