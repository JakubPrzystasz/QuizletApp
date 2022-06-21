package pl.jp.quizletapp.ui;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import pl.jp.quizletapp.QuizFragment;
import pl.jp.quizletapp.R;

public class DashboardPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;
    private QuizFragment quizFragment;
    private QuizFragment fragment;

    public DashboardPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        quizFragment = new QuizFragment();
        fragment = new QuizFragment();

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return  quizFragment;
            case 1:
                return fragment;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}