package net.gudenau.discord.bot.result;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * The interface for a command result.
 * */
@FunctionalInterface
public interface Result{
    /**
     * Invoked to post the result of a command.
     * */
    void post(TextChannel textChannel, Member author);
}
