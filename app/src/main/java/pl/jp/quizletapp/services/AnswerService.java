package pl.jp.quizletapp.services;

import java.util.List;

import pl.jp.quizletapp.models.Answer;
import pl.jp.quizletapp.models.Session;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AnswerService {

    @GET("answers/")
    Call<List<Answer>> getAnswers();

    @GET("answers/{answerId}")
    Call<Answer> getAnswer(@Path("answerId") Integer id);

    @POST("answers/")
    Call<Answer> postAnswer(@Query("sessionId") Integer sessionId,
                             @Query("optionId") Integer optionId);

}
