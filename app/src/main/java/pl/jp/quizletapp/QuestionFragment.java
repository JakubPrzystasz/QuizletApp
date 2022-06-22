package pl.jp.quizletapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lombok.var;
import pl.jp.quizletapp.adapters.OptionRecyclerViewAdapter;
import pl.jp.quizletapp.models.Question;

public class QuestionFragment extends Fragment {

    private RecyclerView recyclerView;

    private QuizletApp quizletApp;

    private Question question;

    public QuestionFragment(Question question) {
        this.question = question;
    }

    public static QuestionFragment newInstance(Question question) {
        return new QuestionFragment(question);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        quizletApp = (QuizletApp) getActivity().getApplicationContext();
        var session = quizletApp.getSession();
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new OptionRecyclerViewAdapter(this.question));
    }
}