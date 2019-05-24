package net.gudenau.discord.bot.command;

import net.dv8tion.jda.core.entities.Message;
import net.gudenau.discord.bot.result.ColoredTextResult;
import net.gudenau.discord.bot.result.Result;

/**
 * A test of NSFW command filtering.
 * */
@SuppressWarnings("unused")
public class NsfwTestCommand implements Command{
    @Override
    public Result execute(Message message, String... arguments){
        return new ColoredTextResult(0xFF_69_B4, "0w0");
    }
    
    @Override
    public String getDescription(){
        return "A simple test command.";
    }
    
    @Override
    public boolean isNSFW(){
        return true;
    }
}
