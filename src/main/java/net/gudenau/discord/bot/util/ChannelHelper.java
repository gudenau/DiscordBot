package net.gudenau.discord.bot.util;

import net.dv8tion.jda.core.entities.TextChannel;

/**
 * A utility class to help with channel stuff.
 * */
public class ChannelHelper{
    /**
     * Gets the last image posted in a channel.
     *
     * Covers attachments as well as embeds.
     *
     * This is blocking.
     *
     * @param channel The channel to look in
     *
     * @return The proxy URL for the image, or null
     * */
    public static String getLastImage(TextChannel channel){
        var messages = channel.getIterableHistory().complete();
    
        for(var message : messages){
            var attachments = message.getAttachments();
            if(!attachments.isEmpty()){
                var attachment = attachments.get(0);
                if(attachment.isImage()){
                    return attachment.getProxyUrl();
                }
            }
            
            var embeds = message.getEmbeds();
            if(!embeds.isEmpty()){
                var embed = embeds.get(0);
                
                var image = embed.getImage();
                if(image != null && image.getProxyUrl() != null){
                    return image.getProxyUrl();
                }
                
                var thumbnail = embed.getThumbnail();
                if(thumbnail != null && thumbnail.getProxyUrl() != null){
                    return thumbnail.getProxyUrl();
                }
            }
        }
        
        return null;
    }
}
