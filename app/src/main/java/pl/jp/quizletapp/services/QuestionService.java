package pl.jp.quizletapp.services;

import java.util.List;

import okhttp3.ResponseBody;
import pl.jp.quizletapp.models.Lecture;
import pl.jp.quizletapp.models.Session;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface QuestionService {
    @GET("images/{imageId}")
    Call<ResponseBody> getImage(@Path("imageId") String imageId);
}
