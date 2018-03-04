package apps_x.com.newsapi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
 * This class is a model for representing data about the status of receiving data from the server.
 * Contains a link to NewsData.class which in turn stores information about the news
 */
public class NewsResponse {
    @SerializedName("status")
    String status;

    @SerializedName("totalResults")
    int totalResults;

    @SerializedName("articles")
    List<NewsData> newsDataList;

    public List<NewsData> getNewsDataList() {
        return newsDataList;
    }

}
