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
import pl.jp.quizletapp.adapters.LectureRecyclerViewAdapter;
import pl.jp.quizletapp.adapters.ResultRecyclerViewAdapter;
import pl.jp.quizletapp.models.Lecture;
import pl.jp.quizletapp.models.Session;
import pl.jp.quizletapp.services.SessionService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultsFragment extends Fragment {

    @Getter
    private final ResultRecyclerViewAdapter resultRecyclerViewAdapter;
    private QuizletApp quizletApp;

    public ResultsFragment() {
        this.resultRecyclerViewAdapter = new ResultRecyclerViewAdapter(new ResultRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Session session) {
//                SessionService service = quizletApp.getRetrofit().create(SessionService.class);
//                Call<pl.jp.quizletapp.models.Session> callAsync = service.postSession(lecture.getId().intValue(), quizletApp.getUser().getLogin());
//                callAsync.enqueue(new Callback<pl.jp.quizletapp.models.Session>() {
//                    @Override
//                    public void onResponse(Call<pl.jp.quizletapp.models.Session> call, Response<pl.jp.quizletapp.models.Session> response) {
//                        if (response.code() == HttpsURLConnection.HTTP_CREATED) {
//                            pl.jp.quizletapp.models.Session session = response.body();
//                            quizletApp.setSession(session);
//                            Intent intent = new Intent(getActivity().getApplicationContext(), Session.class);
//                            startActivity(intent);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<pl.jp.quizletapp.models.Session> call, Throwable throwable) {
//                    }
//                });
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
        View view = inflater.inflate(R.layout.fragment_result_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(resultRecyclerViewAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        }
        return view;
    }
}