package net.gudenau.discord.bot.command;

import net.dv8tion.jda.core.entities.Message;
import net.gudenau.discord.bot.result.ColoredTextResult;
import net.gudenau.discord.bot.result.Result;
import net.gudenau.discord.bot.result.UrlImageResult;

/**
 * Gets the image for a custom Discord emoji.
 * */
public class EmojiCommand implements Command{
    @Override
    public Result execute(Message message, String... arguments){
        var emotes = message.getEmotes();
        if(emotes.size() != 1 || arguments.length != 1 || !arguments[0].matches("<a?:[a-zA-Z_]{2,}:[0-9]+>")){
            return new ColoredTextResult(
                ColoredTextResult.COLOR_ERROR,
                "Usage: %s emoji (custom emoji)",
                message.getGuild().getSelfMember().getEffectiveName()
            );
        }
        
        var emote = emotes.get(0);
        return new UrlImageResult(emote.getImageUrl());
    }
    
    @Override
    public String getDescription(){
        return "Gets the image for a custom emoji.";
    }
}
