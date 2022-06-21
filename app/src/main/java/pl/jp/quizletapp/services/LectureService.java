package pl.jp.quizletapp.services;

import java.util.List;

import pl.jp.quizletapp.models.Lecture;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LectureService {

    @GET("lectures/")
    Call<List<Lecture>> getLectures();

    @GET("lectures/{lectureId}")
    Call<Lecture> getLecture(@Path("lectureId") String id);
}