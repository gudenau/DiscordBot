package net.gudenau.discord.bot.util;

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
}
