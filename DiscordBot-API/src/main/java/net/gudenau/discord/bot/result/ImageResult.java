package net.gudenau.discord.bot.result;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * Uploads an image to Discord and displays it in an embed.
 * */
public class ImageResult implements Result, Result.Image{
    /**
     * The message for the embed, can be null.
     * */
    private final String message;
    
    /**
     * The image for the embed.
     * */
    private final BufferedImage image;
    
    /**
     * Creates an image result without a message.
     *
     * @param image The image to upload
     * */
    public ImageResult(BufferedImage image){
        this(null, image);
    }
    
    /**
     * Creates an image result with a message.
     *
     * @param message The message for the embed
     * @param image The image to upload
     * */
    public ImageResult(String message, BufferedImage image){
        this.message = message;
        this.image = image;
    }
    
    @Override
    public void post(TextChannel textChannel, Member author){
        var buffer = new ByteArrayOutputStream();
        try{
            ImageIO.write(image, "PNG", buffer);
        }catch(IOException e){
            throw new RuntimeException("Failed to write PNG", e);
        }
        textChannel.sendFile(buffer.toByteArray(), "image.png", new MessageBuilder()
            .setEmbed(new EmbedBuilder()
                .setAuthor(author.getEffectiveName(), null, author.getUser().getEffectiveAvatarUrl())
                .setTitle(message)
                .setImage("attachment://image.png")
                .build())
            .build()
        ).queue();
    }
    
    @Override
    public BufferedImage getImage(){
        return image;
    }
}
