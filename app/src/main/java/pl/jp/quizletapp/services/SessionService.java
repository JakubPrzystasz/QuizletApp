package pl.jp.quizletapp.services;

import pl.jp.quizletapp.models.Session;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SessionService {
    @POST("sessions/")
    Call<Session> postSession(@Query("lectureId") Integer lectureId,
                           @Query("userId") String userId);
}
