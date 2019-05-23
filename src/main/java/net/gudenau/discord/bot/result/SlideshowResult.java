package net.gudenau.discord.bot.result;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.SubscribeEvent;
import net.gudenau.discord.bot.util.LambdaTimerTask;

/**
 * A command result that provides an embeded slideshow.
 * */
public class SlideshowResult implements Result{
    /**
     * The timeout value before the slideshow is no longer active.
     * */
    private static final long TIMEOUT = 1000 * 30;
    
    /**
     * The handler to get new images.
     * */
    private final SlideshowHandler handler;
    /**
     * The slideshow slide limit.
     * */
    private final long limit;
    /**
     * The current slideshow page.
     * */
    private long current = 0;
    /**
     * The title of the slideshow
     * */
    private final String title;
    
    /**
     * A timer for cleaning up the slideshow.
     * */
    private static final Timer timer = new Timer();
    /**
     * The timer task for cleaning up the slideshow.
     * */
    private TimerTask task;
    
    /**
     * The user that requested the slideshow, only they can
     * command it.
     * */
    private Member author;
    /**
     * The message that contains the slideshow.
     * */
    private Message message;
    
    /**
     * Creates a new slideshow.
     *
     * @param handler The handler that provides image URLs
     * @param limit The max amount of slideshow pages
     * @param title The tile of the slideshow
     * */
    public SlideshowResult(SlideshowHandler handler, long limit, String title){
        this.handler = handler;
        this.limit = limit;
        timer.schedule(task = new LambdaTimerTask(this::tick), TIMEOUT);
        this.title = title;
    }
    
    /**
     * Executed to clean up the slideshow.
     * */
    private void tick(){
        listener.remove(message.getId());
        message.addReaction("\uD83D\uDED1").queue();
    }
    
    /**
     * Called when the user adds an emote to the slideshow message
     *
     * @param emote The emote
     * */
    private void handleEmote(String emote){
        // ◀ ▶ ⏹ ⏪ ⏩
        switch(emote){
            // Go back one slide
            case "◀":{
                task.cancel();
                timer.schedule(task = new LambdaTimerTask(this::tick), TIMEOUT);
                page(current - 1);
            } break;
            
            // Go forward one slide
            case "▶":{
                task.cancel();
                timer.schedule(task = new LambdaTimerTask(this::tick), TIMEOUT);
                page(current + 1);
            } break;
    
            // Go back ten slides
            case "⏪":{
                task.cancel();
                timer.schedule(task = new LambdaTimerTask(this::tick), TIMEOUT);
                page(current - 10);
            } break;
    
            // Go forward ten slides
            case "⏩":{
                task.cancel();
                timer.schedule(task = new LambdaTimerTask(this::tick), TIMEOUT);
                page(current + 10);
            } break;
            
            // Stop the slideshow and clean up early
            case "\u23F9":{
                task.run();
                task.cancel();
            } break;
        }
    }
    
    /**
     * Changes the slideshow page.
     *
     * @param index The new slideshow page
     * */
    private void page(long index){
        if(index < 0){
            index = 0;
        }else if(index >= limit){
            index = limit - 1;
        }
        if(index != current){
            current = index;
            message.editMessage(generateEmbed()).queue();
        }
    }
    
    /**
     * Posts the slideshow message and does some setup.
     *
     * @param textChannel The channel the command was executed in
     * @param author The author of the command
     * */
    @Override
    public void post(TextChannel textChannel, Member author){
        this.author = author;
        
        textChannel.sendMessage(
            new MessageBuilder()
                .setEmbed(generateEmbed())
            .build()
        ).queue((message)->{
            // ◀ ▶ ⏹ ⏪ ⏩
            SlideshowResult.this.message = message;
            listener.add(message.getId(), SlideshowResult.this);
            message.addReaction("⏪").queue();
            message.addReaction("◀").queue();
            message.addReaction("\u23F9").queue();
            message.addReaction("▶").queue();
            message.addReaction("⏩").queue();
        });
    }
    
    /**
     * Generates the embed for the slideshow.
     *
     * @return The embed for the current page
     * */
    private MessageEmbed generateEmbed(){
        return new EmbedBuilder()
            .setAuthor(author.getEffectiveName(), null, author.getUser().getAvatarUrl())
            .setTitle(title)
            .setImage(handler.invoke(current))
            .setFooter(String.format(
                "Image %d of %d",
                current + 1,
                limit
            ), null)
            .build();
    }
    
    /**
     * The interface for providing slide URLs.
     * */
    @FunctionalInterface
    public interface SlideshowHandler{
        /**
         * Gets the requested slide URL
         * */
        String invoke(long page);
    }
    
    /**
     * The listener instance for JDA
     * */
    private static EmoteListener listener;
    
    /**
     * Creates the listener for JDA.
     *
     * @return The emote listener
     * */
    public static Object createListener(){
        if(listener == null){
            listener = new EmoteListener();
        }
        return listener;
    }
    
    /**
     * The listener implementation.
     * */
    private static class EmoteListener{
        /**
         * All of the current slideshows, accessed with message IDs
         * */
        private final Map<String, SlideshowResult> resultMap = new HashMap<>();
        
        /**
         * Handle the emote events, update the relevant slideshow.
         *
         * @param event The event
         * */
        @SubscribeEvent
        public void onEmoteEvent(MessageReactionAddEvent event){
            var result = resultMap.get(event.getMessageId());
            if(result != null){
                if(result.author.equals(event.getMember())){
                    result.handleEmote(event.getReactionEmote().getName());
                    event.getReaction().removeReaction(event.getUser()).queue();
                }
            }
        }
    
        /**
         * Removes a message from monitoring.
         *
         * @param id The id of the message
         * */
        void remove(String id){
            resultMap.remove(id);
        }
    
        /**
         * Adds a message to be monitored.
         *
         * @param id The id of the message
         * @param result The slideshow of the message
         * */
        void add(String id, SlideshowResult result){
            resultMap.put(id, result);
        }
    }
}
