package net.gudenau.discord.bot.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * An adapter to allow GSON to read and write enumerations.
 * */
public class EnumAdapter<T extends Enum<T> & EnumAdapter.AdaptableEnum> extends TypeAdapter<T>{
    private final Map<String, T> values;
    
    public EnumAdapter(Class<T> type){
        values = new HashMap<>();
        for(var value : type.getEnumConstants()){
            values.put(value.getValue(), value);
        }
    }
    
    @Override
    public void write(JsonWriter out, T value) throws IOException{
        if(value == null){
            out.nullValue();
        }else{
            out.value(value.getValue());
        }
    }
    
    @Override
    public T read(JsonReader in) throws IOException{
        return values.get(in.nextString());
    }
    
    public interface AdaptableEnum{
        String getValue();
    }
}
