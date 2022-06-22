package pl.jp.quizletapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import pl.jp.quizletapp.adapters.QuestionPagerAdapter;
import pl.jp.quizletapp.databinding.ActivitySessionBinding;

public class Session extends AppCompatActivity {

    private QuizletApp quizletApp;
    private ActivitySessionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySessionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        quizletApp = (QuizletApp) getApplicationContext();

        QuestionPagerAdapter questionPagerAdapter = new QuestionPagerAdapter(this, getSupportFragmentManager(),quizletApp.getSession().getAssignedQuestions());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(questionPagerAdapter);

    }
}