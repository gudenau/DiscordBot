package net.gudenau.discord.bot.command;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import net.dv8tion.jda.core.entities.Message;
import net.gudenau.discord.bot.result.ColoredTextResult;
import net.gudenau.discord.bot.result.Result;
import net.gudenau.discord.bot.util.ChannelHelper;

/**
 * A command that can be used in a pipeline.
 * */
public interface IPipeableCommand extends ICommand{
    /**
     * Executes the command.
     *
     * @param image The image to process
     * @param message The original message
     * @param arguments The arguments the user provided
     *
     * @return The result of the command
     * */
    Result execute(BufferedImage image, Message message, String ... arguments);
    
    @Override
    default Result execute(Message message, String ... arguments){
        if(arguments.length == 1){
            try{
                var url = new URL(arguments[0]);
                try(var stream = url.openStream()){
                    return execute(ImageIO.read(stream), message, arguments);
                }catch(IOException e){
                    e.printStackTrace();
                    return new ColoredTextResult(
                        ColoredTextResult.COLOR_ERROR,
                        "Failed to download image"
                    );
                }
            }catch(MalformedURLException ignored){
                return new ColoredTextResult(
                    ColoredTextResult.COLOR_ERROR,
                    "Malformed URL"
                );
            }
        }else if(arguments.length == 0){
            try{
                var url = ChannelHelper.getLastImage(message.getTextChannel());
                if(url == null){
    
                    return new ColoredTextResult(
                        ColoredTextResult.COLOR_ERROR,
                        "Failed to find an image"
                    );
                }
                try(var stream = new URL(url).openStream()){
                    return execute(ImageIO.read(stream), message, arguments);
                }
            }catch(IOException e){
                e.printStackTrace();
                return new ColoredTextResult(
                    ColoredTextResult.COLOR_ERROR,
                    "Failed to download image"
                );
            }
        }else{
            return new ColoredTextResult(
                ColoredTextResult.COLOR_ERROR,
                "Too many arguments: %s [URL]",
                getName()
            );
        }
    }
}
