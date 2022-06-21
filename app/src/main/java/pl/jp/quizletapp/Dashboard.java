package pl.jp.quizletapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import pl.jp.quizletapp.databinding.ActivityDashboardBinding;
import pl.jp.quizletapp.models.Lecture;
import pl.jp.quizletapp.services.LectureService;
import pl.jp.quizletapp.ui.DashboardPagerAdapter;
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

        LectureService service = quizletApp.getRetrofit().create(LectureService.class);
        Call<List<Lecture>> callAsync = service.getLectures();

        callAsync.enqueue(new Callback<List<Lecture>>() {

            @Override
            public void onResponse(Call<List<Lecture>> call, Response<List<Lecture>> response) {
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    final Fragment fragment = sectionsPagerAdapter.getItem(0);
                    ((QuizFragment) fragment).getLectureRecyclerViewAdapter().setLectures(response.body());
                    ((QuizFragment) fragment).getLectureRecyclerViewAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Lecture>> call, Throwable t) {
                Toast.makeText(Dashboard.this, "Nie można pobrać danych", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }

}