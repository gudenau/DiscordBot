package net.gudenau.discord.bot.commands;

import net.dv8tion.jda.core.entities.Message;
import net.gudenau.discord.bot.command.ICommand;
import net.gudenau.discord.bot.result.Result;
import net.gudenau.discord.bot.result.TextResult;

/**
 * Creates an invite for the current bot with all permissions.
 *
 * Yes all of them.
 * */
public class InviteCommand implements ICommand{
    @Override
    public Result execute(Message message, String... arguments){
        var jda = message.getJDA();
        var self = jda.getSelfUser();
        return new TextResult(
            "Thanks for the interest, here is my invite link:\nhttps://discordapp.com/oauth2/authorize?client_id=%s&permissions=-1&scope=bot",
            self.getId()
        );
    }
    
    @Override
    public String getDescription(){
        return "Generates an invite for this bot.";
    }
    
    @Override
    public String getName(){
        return "invite";
    }
}
