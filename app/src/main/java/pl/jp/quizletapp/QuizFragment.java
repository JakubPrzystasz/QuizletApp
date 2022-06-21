package pl.jp.quizletapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import lombok.Getter;
import pl.jp.quizletapp.models.Lecture;
import pl.jp.quizletapp.ui.LectureRecyclerViewAdapter;

public class QuizFragment extends Fragment {

    private QuizletApp quizletApp;
    @Getter
    private final LectureRecyclerViewAdapter lectureRecyclerViewAdapter;

    public QuizFragment() {
        this.lectureRecyclerViewAdapter = new LectureRecyclerViewAdapter(new LectureRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Lecture lecture) {
                //TODO: Open new Session from lecture

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lecture_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(lectureRecyclerViewAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        }
        return view;
    }
}