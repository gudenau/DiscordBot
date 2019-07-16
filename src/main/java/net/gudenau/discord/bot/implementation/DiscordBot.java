package net.gudenau.discord.bot.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.AnnotatedEventManager;
import net.dv8tion.jda.core.hooks.SubscribeEvent;
import net.gudenau.discord.bot.IPlugin;
import net.gudenau.discord.bot.commands.*;
import net.gudenau.discord.bot.plugin.PluginLoader;
import net.gudenau.discord.bot.result.SlideshowResult;
import net.gudenau.discord.bot.json.Configuration;

/**
 * The entry point of the bot.
 * */
public class DiscordBot{
    private static CommandManager commandManager;
    
    private static List<PluginHolder> loadedPlugins = new ArrayList<>();
    
    /**
     * Handles JDA init, configuration and command registration.
     * */
    public static void main(String[] arguments) throws Throwable{
        Configuration.load();
        
        commandManager = new CommandManager();
        
        commandManager.register(new HelpCommand(commandManager));
        commandManager.register(new InviteCommand());
        var pluginCommand = new PluginCommand();
        commandManager.register(pluginCommand);
        
        var pluginLayer = PluginLoader.loadPlugins();
        
        if(pluginLayer != null){
            var plugins = ServiceLoader.load(pluginLayer, IPlugin.class);
            plugins.forEach((plugin)->{
                commandManager.setCurrentPlugin(plugin);
                var status = PluginHolder.Status.LOADED;
                try{
                    if(!plugin.register(commandManager)){
                        System.err.printf(
                            "Plugin %s failed to register\n",
                            plugin.getName()
                        );
                        status = PluginHolder.Status.ERRORED;
                    }
                }catch(Throwable t){
                    System.err.printf(
                        "Plugin %s failed to register\n",
                        plugin.getName()
                    );
                    t.printStackTrace();
                    status = PluginHolder.Status.CRASHED;
                }
                loadedPlugins.add(new PluginHolder(plugin, status));
            });
        }
        
        pluginCommand.setPlugins(loadedPlugins);
        
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
        commandManager.setPrefix(jda.getSelfUser().getAsMention());
    }
    
    /**
     * Called on each event, just give it to the command manager.
     *
     * @param event The event
     * */
    @SubscribeEvent
    public void onMessageReceived(MessageReceivedEvent event){
        commandManager.handleMessage(event.getMessage());
    }
}
