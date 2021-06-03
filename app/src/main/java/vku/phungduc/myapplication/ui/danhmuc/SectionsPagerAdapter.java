package vku.phungduc.myapplication.ui.danhmuc;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import static vku.phungduc.myapplication.constant.danhmucs;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private final Context mContext;
    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position );
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return danhmucs.get(position).getTenDanhmuc() ;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return danhmucs.size();
    }
}
