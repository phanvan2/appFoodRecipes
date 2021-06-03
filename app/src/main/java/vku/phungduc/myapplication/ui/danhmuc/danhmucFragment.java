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
