package net.gudenau.discord.bot.util;

import com.google.gson.annotations.Expose;
import java.io.File;
import java.io.IOException;

/**
 * Global configuration stuff.
 * */
public class Configuration{
    /**
     * The key used for logging into Discord as a bot.
     * */
    @Expose
    public String discordKey = "Get this from https://discordapp.com/developers/applications/";
    /**
     * The key for Bing's image search APi.
     * */
    @Expose
    public String bingImageKey = "Get this from Bing, it's a process....";
    
    private static Configuration configuration;
    
    /**
     * Gets the current configuration values.
     *
     * @return The configuration
     * */
    public static Configuration getConfiguration(){
        return configuration;
    }
    
    /**
     * Loads the configuration from disk, exiting with a message if it does not exist.
     * */
    public static void load() throws IOException{
        File configFile = new File("./config.json");
        if(configFile.exists()){
            configuration = Json.read(configFile, Configuration.class);
        }else{
            configuration = new Configuration();
            save();
            System.err.println("New config.json file, please set keys are relaunch!");
            System.exit(0);
        }
        save();
    }
    
    /**
     * Saves the current config to disk.
     * */
    private static void save() throws IOException{
        Json.write(new File("./config.json"), configuration);
    }
}
