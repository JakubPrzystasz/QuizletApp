package pl.jp.quizletapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

import lombok.var;
import pl.jp.quizletapp.adapters.CustomViewPager;
import pl.jp.quizletapp.adapters.QuestionPagerAdapter;
import pl.jp.quizletapp.adapters.SwipeDirection;
import pl.jp.quizletapp.databinding.ActivitySessionBinding;
import pl.jp.quizletapp.models.Answer;
import pl.jp.quizletapp.models.Option;
import pl.jp.quizletapp.services.AnswerService;
import pl.jp.quizletapp.services.SessionService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Session extends AppCompatActivity {

    private QuizletApp quizletApp;
    private ActivitySessionBinding binding;
    private Integer currentPage;
    private QuestionFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySessionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        quizletApp = (QuizletApp) getApplicationContext();

        binding.progressBar.setMax(quizletApp.getSession().getAssignedQuestions().size());
        binding.tvProgress.setText(getString(R.string.progress_msg, 1, quizletApp.getSession().getAssignedQuestions().size()));
        binding.progressBar.setProgress(1);

        QuestionPagerAdapter questionPagerAdapter = new QuestionPagerAdapter(this, getSupportFragmentManager(), quizletApp.getSession().getAssignedQuestions());
        CustomViewPager viewPager = binding.viewPager;
        viewPager.setAllowedSwipeDirection(SwipeDirection.RIGHT);
        viewPager.setAdapter(questionPagerAdapter);
        fragment = (QuestionFragment) questionPagerAdapter.getItem(0);
        currentPage = 0;

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //Save previous fragment
                saveFragment();
                //Update UI
                binding.progressBar.setProgress(position + 1, true);
                binding.tvProgress.setText(getString(R.string.progress_msg, position + 1, quizletApp.getSession().getAssignedQuestions().size()));
                currentPage = position;
                fragment = (QuestionFragment) questionPagerAdapter.getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.btnSubmit.setOnClickListener(v -> {
            if (currentPage == quizletApp.getSession().getAssignedQuestions().size() - 1) {
                saveFragment();
                SessionService sessionService = quizletApp.getRetrofit().create(SessionService.class);
                Call<pl.jp.quizletapp.models.Session> callAsync = sessionService.getSessionResults(quizletApp.getSession().getId().toString());
                callAsync.enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<pl.jp.quizletapp.models.Session> call, Response<pl.jp.quizletapp.models.Session> response) {

                    }
                    @Override
                    public void onFailure(Call<pl.jp.quizletapp.models.Session> call, Throwable t) {
                    }
                });
                finish();
            } else {
                viewPager.setCurrentItem(currentPage + 1);
            }
        });
    }

    private void saveFragment() {
        var recycler = fragment.getOptionRecyclerViewAdapter();
        var opts = recycler.getCheckedOptions();
        AnswerService service = quizletApp.getRetrofit().create(AnswerService.class);
        for (Option opt : opts) {
            Call<Answer> callAsync = service.postAnswer(quizletApp.getSession().getId().intValue(), opt.getId().intValue());
            callAsync.enqueue(new Callback<Answer>() {
                @Override
                public void onResponse(Call<Answer> call, Response<Answer> response) {
                }

                @Override
                public void onFailure(Call<Answer> call, Throwable t) {
                }
            });
        }
    }
}