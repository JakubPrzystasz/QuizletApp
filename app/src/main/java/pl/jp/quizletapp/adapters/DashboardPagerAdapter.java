package pl.jp.quizletapp.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import pl.jp.quizletapp.LecturesFragment;
import pl.jp.quizletapp.R;
import pl.jp.quizletapp.ResultsFragment;

public class DashboardPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;
    private LecturesFragment lecturesFragment;
    private ResultsFragment resultsFragment;

    public DashboardPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        lecturesFragment = new LecturesFragment();
        resultsFragment = new ResultsFragment();

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return lecturesFragment;
            case 1:
                return resultsFragment;
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