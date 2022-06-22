package pl.jp.quizletapp.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import lombok.var;
import pl.jp.quizletapp.QuestionFragment;
import pl.jp.quizletapp.models.Question;

public class QuestionPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    private final List<Question> questions;
    private final List<QuestionFragment> fragments;

    public QuestionPagerAdapter(Context context, FragmentManager fm, List<Question> questions) {
        super(fm);
        this.mContext = context;
        this.questions = questions;
        this.fragments = new ArrayList<>();
        this.questions.forEach(v -> {
            var fragment = QuestionFragment.newInstance(v);
            this.fragments.add(fragment);
        });
    }

    @Override
    public Fragment getItem(int position) {
        if (position >= 0 && position <= this.getCount())
            return this.fragments.get(position);
        else
            return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

    @Override
    public int getCount() {
        return questions.size();
    }
}