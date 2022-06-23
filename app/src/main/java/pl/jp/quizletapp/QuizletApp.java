package pl.jp.quizletapp;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.jp.quizletapp.models.Session;
import pl.jp.quizletapp.models.User;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizletApp extends Application {

    public static QuizletApp instance = null;
    private Retrofit retrofit;
    @Getter @Setter
    private User user;
    @Getter @Setter
    private Session session;
    @Getter
    private String baseurl;

    public static Context getInstance() {
        if (null == instance) {
            instance = new QuizletApp();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void setRetrofit(String base_url) {
        this.baseurl = base_url;
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        retrofit = new Retrofit.Builder()
                .baseUrl(String.valueOf(base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

}
