package net.gudenau.discord.bot.result;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * A simple text result.
 * */
public class ColoredTextResult implements Result{
    /**
     * Standardized error color for the bot.
     * */
    public static final int COLOR_ERROR = 0x80_00_00;
    
    /**
     * The message to send.
     * */
    private final String message;
    
    /**
     * The color of the embed.
     * */
    private final int color;
    
    /**
     * Creates a new text result and treats the String as a
     * printf formatted string.
     *
     * @param color The color of the embed
     * @param format The format of the message
     * @param args The args for the message
     * */
    public ColoredTextResult(int color, String format, Object... args){
        this(color, String.format(format, args));
    }
    
    /**
     * Creates a new text result.
     *
     * @param color The color of the embed
     * @param message The message to send
     * */
    public ColoredTextResult(int color, String message){
        this.color = color;
        this.message = message;
    }
    
    @Override
    public void post(TextChannel textChannel, Member author){
        textChannel.sendMessage(new MessageBuilder()
            .setEmbed(new EmbedBuilder()
                .setAuthor(author.getEffectiveName(), null, author.getUser().getAvatarUrl())
                .setTitle(message)
                .setColor(color)
                .build()
            )
            .build()).queue();
    }
}
