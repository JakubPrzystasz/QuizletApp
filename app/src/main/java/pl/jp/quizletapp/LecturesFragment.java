package pl.jp.quizletapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.net.ssl.HttpsURLConnection;

import lombok.Getter;
import pl.jp.quizletapp.models.Lecture;
import pl.jp.quizletapp.models.Session;
import pl.jp.quizletapp.models.User;
import pl.jp.quizletapp.services.SessionService;
import pl.jp.quizletapp.ui.LectureRecyclerViewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LecturesFragment extends Fragment {

    @Getter
    private final LectureRecyclerViewAdapter lectureRecyclerViewAdapter;
    private QuizletApp quizletApp;

    public LecturesFragment() {
        this.lectureRecyclerViewAdapter = new LectureRecyclerViewAdapter(new LectureRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Lecture lecture) {
                SessionService service = quizletApp.getRetrofit().create(SessionService.class);
                Call<Session> callAsync = service.postSession(lecture.getId().intValue(), quizletApp.getUser().getLogin());
                callAsync.enqueue(new Callback<Session>() {
                    @Override
                    public void onResponse(Call<Session> call, Response<Session> response) {
                        if (response.code() == HttpsURLConnection.HTTP_CREATED) {
                            Session session = response.body();
                            quizletApp.setSession(session);
                            Intent intent = new Intent(getActivity().getApplicationContext(), Quiz.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Session> call, Throwable throwable) {
                    }
                });
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.quizletApp = (QuizletApp) getActivity().getApplicationContext();
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