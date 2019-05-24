package net.gudenau.discord.bot.result;

import java.awt.image.BufferedImage;
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
    
    /**
     * Interface to implement if this result has an image.
     *
     * Used for "piping" image commands into each other.
     * */
    @FunctionalInterface
    interface Image{
        BufferedImage getImage();
    }
    
    /**
     * Interface to implement if this result has an image URL.
     *
     * Used for "piping" image commands into each other.
     * */
    @FunctionalInterface
    interface ImageURL{
        String getImage();
    }
}
