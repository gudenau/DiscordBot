package net.gudenau.discord.bot.implementation;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.guild.GuildReadyEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberNickChangeEvent;
import net.dv8tion.jda.core.hooks.SubscribeEvent;

/**
 * Just a thing to prevent people from changing the name of the bot.
 * */
public class NickManager{
    /**
     * Called for each guild, used to reset names if someone changed it
     * while the bot was offline.
     *
     * @param event The event
     * */
    @SubscribeEvent
    public void onReady(GuildReadyEvent event){
        var guild = event.getGuild();
        var member = guild.getSelfMember();
        if(member.getNickname() != null &&
           (member.hasPermission(Permission.NICKNAME_CHANGE) || member.hasPermission(Permission.NICKNAME_MANAGE))
        ){
            guild.getController().setNickname(member, null).queue();
        }
    }
    
    /**
     * Called when nicknames are changed, used to prevent changes while the
     * bot is online.
     *
     * @param event The event
     * */
    @SubscribeEvent
    public void onNickChanged(GuildMemberNickChangeEvent event){
        var guild = event.getGuild();
        var member = event.getMember();
        if(guild.getSelfMember().equals(member)){
            if(member.getNickname() != null &&
               (member.hasPermission(Permission.NICKNAME_CHANGE) || member.hasPermission(Permission.NICKNAME_MANAGE))
            ){
                guild.getController().setNickname(member, null).queue();
            }
        }
    }
}
