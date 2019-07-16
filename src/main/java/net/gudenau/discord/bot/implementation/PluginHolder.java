package net.gudenau.discord.bot.implementation;

import net.gudenau.discord.bot.IPlugin;

public class PluginHolder{
    public final IPlugin plugin;
    public final Status status;
    
    PluginHolder(IPlugin plugin, Status status){
        this.plugin = plugin;
        this.status = status;
    }
    
    public enum Status{
        LOADED,
        ERRORED,
        CRASHED
    }
}
