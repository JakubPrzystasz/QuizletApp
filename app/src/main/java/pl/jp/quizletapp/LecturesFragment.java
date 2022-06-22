package pl.jp.quizletapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.net.ssl.HttpsURLConnection;

import lombok.Getter;
import pl.jp.quizletapp.models.Lecture;
import pl.jp.quizletapp.services.SessionDTO;
import pl.jp.quizletapp.services.SessionMapper;
import pl.jp.quizletapp.services.SessionService;
import pl.jp.quizletapp.adapters.LectureRecyclerViewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LecturesFragment extends Fragment {

    @Getter
    private final LectureRecyclerViewAdapter lectureRecyclerViewAdapter;
    private QuizletApp quizletApp;

    public LecturesFragment() {
        this.lectureRecyclerViewAdapter = new LectureRecyclerViewAdapter(lecture -> {
            SessionService service = quizletApp.getRetrofit().create(SessionService.class);
            Call<SessionDTO> callAsync = service.postSession(lecture.getId().intValue(), quizletApp.getUser().getLogin());
            callAsync.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<SessionDTO> call, Response<SessionDTO> response) {
                    if (response.code() == HttpsURLConnection.HTTP_CREATED) {
                        SessionDTO session = response.body();
                        quizletApp.setSession(SessionMapper.toDto(session));
                        Intent intent = new Intent(getActivity().getApplicationContext(), Session.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<SessionDTO> call, Throwable throwable) {
                }
            });
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