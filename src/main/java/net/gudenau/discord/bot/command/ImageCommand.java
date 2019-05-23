package net.gudenau.discord.bot.command;

import net.dv8tion.jda.core.entities.Message;
import net.gudenau.discord.bot.api.bing.Bing;
import net.gudenau.discord.bot.result.Result;
import net.gudenau.discord.bot.result.SlideshowResult;

import static net.gudenau.discord.bot.util.StringUtil.squashString;

/**
 * The `image` command for searching for an image.
 *
 * Most of the logic is elsewhere.
 * */
public class ImageCommand implements Command{
    static{
        // Make sure Bing is initialized.
        Bing.init();
    }
    
    @Override
    public Result execute(Message message, String... arguments){
        var query = squashString(arguments);
        var result = Bing.searchImage(query, !message.getTextChannel().isNSFW());
        if(result == null){
            return (textChannel, author)->textChannel.sendMessage("Failed").queue();
        }
        return new SlideshowResult(result::getImage, result.getLimit(), "Bing Image Results");
    }
    
    @Override
    public String getDescription(){
        return "Searches Bing for an image.";
    }
}
