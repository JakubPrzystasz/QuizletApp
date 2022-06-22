package pl.jp.quizletapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import pl.jp.quizletapp.ui.question.question;

public class Quiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, question.newInstance())
                    .commitNow();
        }
    }
}