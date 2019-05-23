package net.gudenau.discord.bot.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * Just a stupid GSON wrapper.
 * */
@SuppressWarnings("WeakerAccess")
public class Json{
    private static final Gson GSON = new GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .setPrettyPrinting()
        .create();
    
    public static <T> T read(File file, Class<T> type) throws IOException{
        try(var stream = new FileInputStream(file)){
            return read(stream, type);
        }
    }
    
    public static <T> T read(InputStream stream, Class<T> type) throws IOException{
        try(var reader = new InputStreamReader(stream, StandardCharsets.UTF_8)){
            return read(reader, type);
        }
    }
    
    public static <T> T read(Reader reader, Class<T> type){
        return GSON.fromJson(reader, type);
    }
    
    public static void write(File file, Object object) throws IOException{
        try(var stream = new FileOutputStream(file)){
            write(stream, object);
        }
    }
    
    public static void write(OutputStream stream, Object object) throws IOException{
        try(var writer = new OutputStreamWriter(stream, StandardCharsets.UTF_8)){
            write(writer, object);
        }
    }
    
    public static void write(Writer writer, Object object){
        GSON.toJson(object, writer);
    }
}
