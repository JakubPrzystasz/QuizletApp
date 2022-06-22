package pl.jp.quizletapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import pl.jp.quizletapp.adapters.OptionRecyclerViewAdapter;
import pl.jp.quizletapp.databinding.FragmentQuestionBinding;
import pl.jp.quizletapp.models.Option;
import pl.jp.quizletapp.models.Question;

public class QuestionFragment extends Fragment {

    @Getter
    private final OptionRecyclerViewAdapter optionRecyclerViewAdapter;
    private final Question question;
    private RecyclerView recyclerView;
    private FragmentQuestionBinding binding;
    @Getter
    private List<Option> selectedOptions;

    public QuestionFragment(Question question) {
        this.selectedOptions = new ArrayList<>();

        this.optionRecyclerViewAdapter = new OptionRecyclerViewAdapter(question, option -> {

        });
        this.question = question;
    }

    public static QuestionFragment newInstance(Question question) {
        return new QuestionFragment(question);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentQuestionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        binding.tvContent.setText(this.question.getContent());

        switch (question.getType()) {
            case MULTIPLE_ANSWER:
                binding.tvPrompt.setText("Zaznacz wszystkie poprawne odpowiedzi");
                break;
            case SINGLE_ANSWER:
                binding.tvPrompt.setText("Zaznacz tylko jedną odpowiedź");
                break;
            case TRUE_FALSE:
                binding.tvPrompt.setText("Zaznacz które zadnia są prawdziwe odpowiedzi");
                break;
        }

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(optionRecyclerViewAdapter);
    }
}