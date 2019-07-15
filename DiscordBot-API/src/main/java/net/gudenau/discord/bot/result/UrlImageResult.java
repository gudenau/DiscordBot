package net.gudenau.discord.bot.result;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * An image result that uses an existing image at a URL.
 * */
public class UrlImageResult implements Result, Result.ImageURL{
    /**
     * The message for the embed, can be null.
     * */
    private final String message;
    
    /**
     * The image for the embed.
     * */
    private final String image;
    
    /**
     * Creates an image result without a message.
     *
     * @param image The URL for the image
     * */
    public UrlImageResult(String image){
        this(null, image);
    }
    
    /**
     * Creates an image result with a message.
     *
     * @param message The message for the embed
     * @param image The URL for the image
     * */
    public UrlImageResult(String message, String image){
        this.message = message;
        this.image = image;
    }
    
    @Override
    public void post(TextChannel textChannel, Member author){
        textChannel.sendMessage(new MessageBuilder()
            .setEmbed(new EmbedBuilder()
                .setAuthor(author.getEffectiveName(), null, author.getUser().getEffectiveAvatarUrl())
                .setTitle(message)
                .setImage(image)
            .build())
        .build()).queue();
    }
    
    @Override
    public String getImage(){
        return image;
    }
}
