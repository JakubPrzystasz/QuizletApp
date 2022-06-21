package pl.jp.quizletapp.services;

import pl.jp.quizletapp.models.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {

    @GET("users/details/{login}")
    Call<User> getUser(@Path("login") String login);

    @POST("users/")
    Call<User> postUser(@Query("login") String login);
}