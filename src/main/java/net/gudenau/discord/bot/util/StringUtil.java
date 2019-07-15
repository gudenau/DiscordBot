package net.gudenau.discord.bot.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Misc string stuff.
 * */
public class StringUtil{
    public static String squashString(String[] array){
        return squashString(0, array);
    }
    
    public static String squashString(int offset, String[] array){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = offset; i < array.length; i++){
            stringBuilder.append(array[i]).append(' ');
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
    
    public static String toString(Throwable throwable){
        var stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
    
    public static String truncate(String value, int limit){
        if(value.length() > limit){
            return value.substring(0, limit);
        }else{
            return value;
        }
    }
}
