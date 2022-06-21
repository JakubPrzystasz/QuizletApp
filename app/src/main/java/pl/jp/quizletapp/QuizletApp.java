package pl.jp.quizletapp;

import android.app.Application;
import android.content.Context;

import okhttp3.OkHttpClient;
import pl.jp.quizletapp.models.User;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizletApp extends Application {

    private Retrofit retrofit;
    private User user;

    public static QuizletApp instance = null;

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

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    public void setRetrofit(String base_url){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        retrofit = new Retrofit.Builder()
                .baseUrl(String.valueOf(base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }

}
