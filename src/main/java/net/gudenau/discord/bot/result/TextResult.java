package net.gudenau.discord.bot.result;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * A simple text result.
 * */
public class TextResult implements Result{
    /**
     * The message to send.
     * */
    private final String message;
    
    /**
     * Creates a new text result and treats the String as a
     * printf formatted string.
     *
     * @param format The format of the message
     * @param args The args for the message
     * */
    public TextResult(String format, Object... args){
        this(String.format(format, args));
    }
    
    /**
     * Creates a new text result.
     *
     * @param message The message to send
     * */
    public TextResult(String message){
        this.message = message;
    }
    
    @Override
    public void post(TextChannel textChannel, Member author){
        textChannel.sendMessage(new MessageBuilder()
            .setEmbed(new EmbedBuilder()
                .setAuthor(author.getEffectiveName(), null, author.getUser().getAvatarUrl())
                .setTitle(message)
                .build()
            )
        .build()).queue();
    }
}
