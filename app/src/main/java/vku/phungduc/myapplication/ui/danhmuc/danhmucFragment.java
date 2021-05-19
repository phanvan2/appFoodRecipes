package vku.phungduc.myapplication.ui.danhmuc;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import vku.phungduc.myapplication.R;

import static vku.phungduc.myapplication.constant.danhmucs;

public class danhmucFragment extends Fragment {
  //  public List<String> tabs_danhmuc = danhmucs  ;
    private TabLayout tabLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_danhmuc, container, false);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(root.getContext(), getChildFragmentManager());
        ViewPager viewPager = root.findViewById(R.id.view_danhmc);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabDanhmuc);
        tabs.setupWithViewPager(viewPager);

        return root;
    }

}
class SectionsPagerAdapter extends FragmentPagerAdapter {
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
