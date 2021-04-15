package raf.rs.projekat1.boris_stojanovic_rn3518.view.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import raf.rs.projekat1.boris_stojanovic_rn3518.R;
import raf.rs.projekat1.boris_stojanovic_rn3518.view.viewpager.TabbedPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class TabbedListsFragment extends Fragment {

    public TabbedListsFragment() {
        super(R.layout.fragment_tabbed_lists);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view){
        initTabs(view);
    }

    private void initTabs(View view) {
        ViewPager viewPager = view.findViewById(R.id.tabbedViewPager);
        viewPager.setAdapter(new TabbedPagerAdapter(getChildFragmentManager()));
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
