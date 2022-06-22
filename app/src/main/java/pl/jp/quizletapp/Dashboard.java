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

import pl.jp.quizletapp.adapters.DashboardPagerAdapter;
import pl.jp.quizletapp.databinding.ActivityDashboardBinding;
import pl.jp.quizletapp.services.SessionDTO;
import pl.jp.quizletapp.services.SessionService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    private QuizletApp quizletApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        quizletApp = (QuizletApp) getApplicationContext();

        DashboardPagerAdapter sectionsPagerAdapter = new DashboardPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        final Fragment fragment = sectionsPagerAdapter.getItem(0);
        ((LecturesFragment) fragment).getLectureRecyclerViewAdapter().setLectures(response.body());
        ((LecturesFragment) fragment).getLectureRecyclerViewAdapter().notifyDataSetChanged();


        {
            SessionService sessionService = quizletApp.getRetrofit().create(SessionService.class);
            Call<List<SessionDTO>> callAsync = sessionService.getSessions();
            callAsync.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<List<SessionDTO>> call, Response<List<SessionDTO>> response) {
                    if (response.code() == HttpsURLConnection.HTTP_OK) {
                        final Fragment fragment = sectionsPagerAdapter.getItem(1);
                        List<pl.jp.quizletapp.models.Session> sessionList = new ArrayList<>();
                        for(SessionDTO dto : response.body()){

                        }
                        ((ResultsFragment) fragment).getResultRecyclerViewAdapter().setSessions(sessionList);
                        ((ResultsFragment) fragment).getResultRecyclerViewAdapter().notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<SessionDTO>> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}