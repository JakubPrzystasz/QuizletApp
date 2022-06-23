package pl.jp.quizletapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import lombok.SneakyThrows;
import lombok.var;
import pl.jp.quizletapp.adapters.DashboardPagerAdapter;
import pl.jp.quizletapp.databinding.ActivityDashboardBinding;
import pl.jp.quizletapp.models.Lecture;
import pl.jp.quizletapp.models.User;
import pl.jp.quizletapp.services.LectureService;
import pl.jp.quizletapp.services.SessionService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    private QuizletApp quizletApp;
    private DashboardPagerAdapter sectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        quizletApp = (QuizletApp) getApplicationContext();

        sectionsPagerAdapter = new DashboardPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    @SneakyThrows
    private void update() {
        LectureService lectureService = quizletApp.getRetrofit().create(LectureService.class);
        SessionService sessionService = quizletApp.getRetrofit().create(SessionService.class);

        {
            Call<List<Lecture>> callAsync = lectureService.getLectures();

            callAsync.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<List<Lecture>> call, Response<List<Lecture>> response) {
                    if (response.code() == HttpsURLConnection.HTTP_OK) {
                        final Fragment fragment = sectionsPagerAdapter.getItem(0);
                        ((LecturesFragment) fragment).getLectureRecyclerViewAdapter().setLectures(response.body());
                        ((LecturesFragment) fragment).getLectureRecyclerViewAdapter().notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<Lecture>> call, Throwable t) {

                }
            });
        }
        {
            Call<List<pl.jp.quizletapp.models.Session>> callAsync = sessionService.getSessions();
            callAsync.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<List<pl.jp.quizletapp.models.Session>> call, Response<List<pl.jp.quizletapp.models.Session>> response) {
                    if (response.code() == HttpsURLConnection.HTTP_OK) {
                        final Fragment fragment = sectionsPagerAdapter.getItem(1);
                        List<pl.jp.quizletapp.models.Session> sessionList = new ArrayList(response.body());
                        for (int i = 0; i < sessionList.size(); i++) {
                            Call<Lecture> callAsync = sessionService.getLecture(sessionList.get(i).getId().toString());
                            int finalI = i;
                            callAsync.enqueue(new Callback<Lecture>() {
                                @Override
                                public void onResponse(Call<Lecture> call, Response<Lecture> response) {
                                    if (response.code() == HttpsURLConnection.HTTP_OK) {
                                        sessionList.get(finalI).setLecture(response.body());
                                        ((ResultsFragment) fragment).getResultRecyclerViewAdapter().setSessions(sessionList);
                                        ((ResultsFragment) fragment).getResultRecyclerViewAdapter().notifyDataSetChanged();
                                    }
                                }
                                @Override
                                public void onFailure(Call<Lecture> call, Throwable throwable) {
                                }
                            });
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<pl.jp.quizletapp.models.Session>> call, Throwable t) {
                }
            });

        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}