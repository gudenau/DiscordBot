package net.gudenau.discord.bot.api.bing;

import com.microsoft.azure.cognitiveservices.search.imagesearch.BingImageSearchAPI;
import com.microsoft.azure.cognitiveservices.search.imagesearch.BingImageSearchManager;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.ImagesModel;
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.SafeSearch;
import net.gudenau.discord.bot.util.Configuration;

/**
 * Primary place to put Bing stuff.
 *
 * Used for the search commands.
 * */
public class Bing{
    /**
     * Just a thing to make sure this is only initialized once.
     * */
    private static volatile boolean init = false;
    
    /**
     * The search client for the image command.
     * */
    private static BingImageSearchAPI imageClient;
    
    /**
     * Initialize the search clients.
     * */
    public static void init(){
        if(init){
            return;
        }
        init = true;
        imageClient = BingImageSearchManager.authenticate(Configuration.getConfiguration().bingImageKey);
    }
    
    /**
     * Searches for an image, but without wrapping it in a @{link ImageResult ImageResult}
     *
     * @param query The search query
     * @param safeSearch Weather or not to enable safe search
     * @param offset The offset into the results to search
     *
     * @return The raw @{link ImagesModel ImagesModel} from the Bing API
     * */
    static ImagesModel rawImageSearch(String query, boolean safeSearch, long offset){
        return imageClient.bingImages().search()
            .withQuery(query)
            .withSafeSearch(safeSearch ? SafeSearch.STRICT : SafeSearch.OFF)
            .withOffset(offset)
            .execute();
    }
    
    /**
     * Searches for an image using Bing.
     *
     * @param query The search query
     * @param safeSearch Weather or not to enable safe search
     *
     * @return The search results wrapped in @{link ImageResult ImageResult}
     * */
    public static ImageResult searchImage(String query, boolean safeSearch){
        var images = imageClient.bingImages().search()
            .withQuery(query)
            .withSafeSearch(safeSearch ? SafeSearch.STRICT : SafeSearch.OFF)
            .execute();
        
        if(images == null){
            return null;
        }
        
        return new ImageResult(query, safeSearch, images);
    }
}
