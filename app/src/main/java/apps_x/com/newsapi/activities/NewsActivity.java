package apps_x.com.newsapi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import apps_x.com.newsapi.R;
import apps_x.com.newsapi.adapter.NewsViewAdapter;
import apps_x.com.newsapi.model.NewsData;
import apps_x.com.newsapi.model.NewsResponse;
import apps_x.com.newsapi.server.Server;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsActivity extends AppCompatActivity {
    // Tag this class of the log
    private static final String LOG_TAG = "NewsActivity";
    // URL of the news service
    private static final String API_URL = "https://newsapi.org";
    // Private user api key, used for authorization
    private static final String API_KEY = "e53c68d2f0334ffb99f84e7b5edbedc0";

    private Retrofit retrofit;

    // Initialize view elements
    @BindView(R.id.an_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.an_progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        // Initialize to work with view elements declared in this class
        ButterKnife.bind(this);
        // Show that the download has started
        progressBar.setVisibility(View.VISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // Set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // Add your other interceptors …
        // Add logging as last interceptor
        httpClient.addInterceptor(logging);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        // Initialize retrofit client from newsapi.org
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL) // Server url
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        //
        getNews();
    }

    /**
     * This method is called once after receiving data from the server with a retrofit
     *
     * @param newsList contains a list of news models
     */
    private void addDataToUI(ArrayList<NewsData> newsList) {
        // Hide progress bar
        progressBar.setVisibility(View.GONE);
        // Initialize and setup Recycler View
        NewsViewAdapter newsViewAdapter = new NewsViewAdapter(newsList);
        recyclerView.setAdapter(newsViewAdapter);
        recyclerView.setHasFixedSize(true);
    }

    /**
     * This method is called once when the activation is started in the onCreate()
     * In this method, we access the server and get a response with the news list.
     */
    public void getNews() {
        // Used as a search term for a query
        String searchWord = "world";
        Server service = retrofit.create(Server.class);

        // Getting data from the server
        Call<NewsResponse> call = service.getNews(searchWord, API_KEY);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    // everything went well
                    // form and transfer data to addDataToUII()
                    // method for further work with them
                    NewsResponse newsResponse = response.body();
                    ArrayList<NewsData> newsData = new ArrayList<>();

                    newsData.addAll(newsResponse.getNewsDataList());
                    addDataToUI(newsData);
                    // it's coll, all right
                    Log.d(LOG_TAG + "getNews", String.valueOf(response.code()));
                } else {
                    // server return an error
                    Log.d(LOG_TAG + "getNews", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                // error at runtime
                Log.d(LOG_TAG + "singIn", t.getMessage());
                Toast.makeText(getApplicationContext(), getString(R.string.unknown_error),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
