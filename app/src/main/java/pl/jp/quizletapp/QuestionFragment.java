package pl.jp.quizletapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import okhttp3.ResponseBody;
import pl.jp.quizletapp.adapters.OptionRecyclerViewAdapter;
import pl.jp.quizletapp.databinding.FragmentQuestionBinding;
import pl.jp.quizletapp.models.Option;
import pl.jp.quizletapp.models.Question;
import pl.jp.quizletapp.services.LectureService;
import pl.jp.quizletapp.services.QuestionService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionFragment extends Fragment {

    @Getter
    private final OptionRecyclerViewAdapter optionRecyclerViewAdapter;
    private final Question question;
    private RecyclerView recyclerView;
    private FragmentQuestionBinding binding;
    private QuizletApp quizletApp;

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
        quizletApp = (QuizletApp) getActivity().getApplicationContext();

        if(question.getImageId() != null) {
            //Picasso.with(getActivity()).load(quizletApp.getBaseurl()+"images/"+question.getImageId()).into(binding.ivQuestion);
            QuestionService api = quizletApp.getRetrofit().create(QuestionService.class);
            Call<ResponseBody> call = api.getImage(question.getImageId().toString());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            // display the image data in a ImageView or save it
                            Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                           // binding.ivQuestion.setImageBitmap(bmp.createScaledBitmap(bmp,120,256,false));                            binding.ivQuestion.setImageBitmap(bmp.createScaledBitmap(bmp,120,256,false));
                            binding.ivQuestion.setImageBitmap(bmp);
                            //binding.ivQuestion.setImageBitmap(bmp.createScaledBitmap(bmp,120,256,false));

                        } else {
                            // TODO
                        }
                    } else {
                        // TODO
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // TODO
                }
            });
        }else{
            binding.ivQuestion.setVisibility(View.GONE);
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        binding.tvContent.setText(this.question.getContent());

        switch (question.getType()) {
            case MULTIPLE_ANSWER:
                binding.tvPrompt.setText("Zaznacz tylko poprawne odpowiedzi");
                break;
            case SINGLE_ANSWER:
                binding.tvPrompt.setText("Zaznacz jedną odpowiedź");
                break;
            case TRUE_FALSE:
                binding.tvPrompt.setText("Zaznacz poprawne zdania");
                break;
        }

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(optionRecyclerViewAdapter);
    }
}