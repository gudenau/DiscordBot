package net.gudenau.discord.bot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.AnnotatedEventManager;
import net.dv8tion.jda.core.hooks.SubscribeEvent;
import net.gudenau.discord.bot.command.CommandManager;
import net.gudenau.discord.bot.command.EmojiCommand;
import net.gudenau.discord.bot.command.HelpCommand;
import net.gudenau.discord.bot.command.ImageCommand;
import net.gudenau.discord.bot.command.InviteCommand;
import net.gudenau.discord.bot.result.SlideshowResult;
import net.gudenau.discord.bot.util.Configuration;

/**
 * The entry point of the bot.
 * */
public class DiscordBot{
    /**
     * Handles JDA init, configuration and command registration.
     * */
    public static void main(String[] arguments) throws Throwable{
        Configuration.load();
        
        CommandManager.register("help", new HelpCommand());
        CommandManager.register("invite", new InviteCommand());
        CommandManager.register("image", new ImageCommand());
        CommandManager.register("emoji", new EmojiCommand());
        
        new JDABuilder(AccountType.BOT)
            .setToken(Configuration.getConfiguration().discordKey)
            .setCompressionEnabled(true)
            .setEventManager(new AnnotatedEventManager())
            .addEventListener(new DiscordBot())
            .addEventListener(SlideshowResult.createListener())
            .addEventListener(new NickManager())
            .build();
    }
    
    /**
     * Called when the bot is ready to rumble.
     *
     * @param event The event
     * */
    @SubscribeEvent
    public void onReady(ReadyEvent event){
        var jda = event.getJDA();
        CommandManager.setPrefix(jda.getSelfUser().getAsMention());
    }
    
    /**
     * Called on each event, just give it to the command manager.
     *
     * @param event The event
     * */
    @SubscribeEvent
    public void onMessageReceived(MessageReceivedEvent event){
        CommandManager.handleMessage(event.getMessage());
    }
}
