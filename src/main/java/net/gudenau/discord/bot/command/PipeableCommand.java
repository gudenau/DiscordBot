package net.gudenau.discord.bot.command;

import java.awt.image.BufferedImage;
import net.dv8tion.jda.core.entities.Message;
import net.gudenau.discord.bot.result.Result;

/**
 * A command that can be used in a pipeline.
 * */
public interface PipeableCommand extends Command{
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
}
