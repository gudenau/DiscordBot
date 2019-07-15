package net.gudenau.discord.bot.util;

import java.util.TimerTask;

/**
 * Just a wrapper for TimerTask to allow lambda usage.
 * */
public class LambdaTimerTask extends TimerTask{
    private final Runnable runnable;
    
    public LambdaTimerTask(Runnable runnable){
        this.runnable = runnable;
    }
    
    @Override
    public void run(){
        runnable.run();
    }
}
