package net.gudenau.discord.bot.api.bing;

import com.microsoft.azure.cognitiveservices.search.imagesearch.models.ImagesModel;

/**
 * Results of a Bind image search.
 * */
public class ImageResult{
    /**
     * The query that was used, for going between pages.
     * */
    private final String query;
    
    /**
     * Was this a safe search?
     * */
    private final boolean safeSearch;
    
    /**
     * The results from Bing.
     * */
    private ImagesModel images;
    
    /**
     * The offset into the Bing image results.
     * */
    private long offset;
    
    /**
     * How many images there are.
     * */
    private long limit;
    
    ImageResult(String query, boolean safeSearch, ImagesModel images){
        this.query = query;
        this.safeSearch = safeSearch;
        this.images = images;
        offset = 0;
        limit = images.totalEstimatedMatches();
    }
    
    /**
     * Gets the URL for an image, re-searches using Bing if needed.
     *
     * @param index The index of the image
     *
     * @return The URL of the image
     * */
    public String getImage(long index){
        if(
            index >= offset + images.value().size() ||
            index < offset
        ){
            offset = index - 10;
            if(offset < 0){
                offset = 0;
            }else if(offset > limit - 20){
                offset = limit - 20;
            }
            images = Bing.rawImageSearch(query, safeSearch, offset);
            limit = images.totalEstimatedMatches();
        }
        
        return images.value().get((int)(index - offset)).contentUrl();
    }
    
    /**
     * Gets the amount of images this search returned.
     *
     * @return The image count
     * */
    public long getLimit(){
        return limit;
    }
}
