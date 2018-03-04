package apps_x.com.newsapi.server;

import apps_x.com.newsapi.model.NewsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Server {

    @GET("/v2/everything/")
        // "everything" - return all news from server
    Call<NewsResponse> getNews(
            @Query("q") String term, // contains search word
            @Query("apiKey") String key); // contains user apiKey
}
