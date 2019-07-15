package net.gudenau.discord.bot.commands;

import java.awt.Color;
import java.awt.image.BufferedImage;
import net.dv8tion.jda.core.entities.Message;
import net.gudenau.discord.bot.command.ICommand;
import net.gudenau.discord.bot.result.ImageResult;
import net.gudenau.discord.bot.result.Result;

/**
 * Tests the ImageResult result by posting a test square.
 * */
@SuppressWarnings("unused")
public class ImageResultTestCommand implements ICommand{
    @Override
    public Result execute(Message message, String... arguments){
        var image = new BufferedImage(1024, 1024, BufferedImage.TYPE_4BYTE_ABGR);
        var graphics = image.createGraphics();
        try{
            graphics.setColor(Color.BLACK);
            graphics.fillRect(0, 0, 1024, 1024);
            graphics.setColor(Color.MAGENTA);
            graphics.fillRect(0, 0, 512, 512);
            graphics.fillRect(512, 512, 1024, 1024);
        }finally{
            graphics.dispose();
        }
        
        return new ImageResult(image);
    }
    
    @Override
    public String getDescription(){
        return "Uploads a test image.";
    }
}
