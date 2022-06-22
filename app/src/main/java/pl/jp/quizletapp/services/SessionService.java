package pl.jp.quizletapp.services;

import java.util.List;

import pl.jp.quizletapp.models.Session;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SessionService {
    @POST("sessions/")
    Call<SessionDTO> postSession(@Query("lectureId") Integer lectureId,
                              @Query("userId") String userId);

    @GET("sessions/{sessionId}/results")
    Call<SessionDTO> getSessionResults(@Path("sessionId") String sessionId);

    @GET("sessions/{sessionId}")
    Call<SessionDTO> getSession(@Path("sessionId") String sessionId);

    @GET("sessions/")
    Call<List<SessionDTO>> getSessions();
}
