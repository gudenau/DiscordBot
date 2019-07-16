package net.gudenau.discord.bot.commands;

import java.util.List;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.gudenau.discord.bot.command.ICommand;
import net.gudenau.discord.bot.implementation.PluginHolder;
import net.gudenau.discord.bot.result.ColoredTextResult;
import net.gudenau.discord.bot.result.Result;
import net.gudenau.discord.bot.result.TextResult;

/**
 * Prints a list of commands, with some extra info for the owner.
 * */
public class PluginCommand implements ICommand{
    private List<PluginHolder> plugins;
    
    @Override
    public Result execute(Message message, String... arguments){
        var builder = new StringBuilder();
        
        var jda = message.getJDA();
        var owner = findOwner(jda);
        var author = message.getAuthor();
        
        var reportErrors = owner.equals(author);
        for(var holder : plugins){
            var plugin = holder.plugin;
            var secret = plugin.isSecret();
            
            if(!reportErrors && secret){
                continue;
            }
            
            builder.append(plugin.getName());
            if(reportErrors){
                var status = holder.status;
                if(secret){
                    if(status != PluginHolder.Status.LOADED){
                        builder.append(" (SECRET | ").append(status.name()).append(")");
                    }else{
                        builder.append(" (SECRET)");
                    }
                }else{
                    if(status != PluginHolder.Status.LOADED){
                        builder.append(" (").append(status.name()).append(")");
                    }
                }
            }
            builder.append("\n");
        }
        
        if(builder.length() != 0){
            builder.setLength(builder.length() - 1);
            
            return new TextResult(builder);
        }else{
            return new ColoredTextResult(ColoredTextResult.COLOR_ERROR, "No plugins are present!");
        }
    }
    
    /**
     * The owner of this bot.
     * */
    private static User OWNER = null;
    
    /**
     * Gets the owner of this bot.
     *
     * @return The owner of the bot
     * */
    private static User findOwner(JDA jda){
        if(OWNER == null){
            OWNER = jda.asBot().getApplicationInfo().complete().getOwner();
        }
        return OWNER;
    }
    
    @Override
    public String getDescription(){
        return "Lists plugins.";
    }
    
    public void setPlugins(List<PluginHolder> plugins){
        this.plugins = plugins;
    }
}
