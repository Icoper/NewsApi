package apps_x.com.newsapi.model;

import com.google.gson.annotations.SerializedName;

/*
 * This class is a model for presenting data about news received from the server
 */
public class NewsData {
    @SerializedName("author")
    private String author;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("url")
    private String url;

    @SerializedName("urlToImage")
    private String urlToImage;


    public NewsData() {
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

}
