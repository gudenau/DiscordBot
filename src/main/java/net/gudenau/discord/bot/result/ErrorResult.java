package net.gudenau.discord.bot.result;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.gudenau.discord.bot.util.StringUtil;

/**
 * A result that contains a {@link java.lang.Throwable Throwable}.
 * */
public class ErrorResult implements Result{
    private final Throwable throwable;
    
    /**
     * Creates a new ErrorResult with the provided {@link java.lang.Throwable Throwable}
     *
     * @param throwable The throwable
     * */
    public ErrorResult(Throwable throwable){
        this.throwable = throwable;
    }
    
    @Override
    public void post(TextChannel textChannel, Member author){
        textChannel.sendMessage(new MessageBuilder()
            .setEmbed(new EmbedBuilder()
                .setAuthor(author.getEffectiveName(), null, author.getUser().getAvatarUrl())
                .setTitle("An exception occurred:")
                .setDescription(StringUtil.truncate(throwable.getMessage(), MessageEmbed.TEXT_MAX_LENGTH))
                .setColor(ColoredTextResult.COLOR_ERROR)
                .build()
            )
            .build()).queue();
    }
}
