package raf.rs.projekat1.boris_stojanovic_rn3518.view.viewpager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import raf.rs.projekat1.boris_stojanovic_rn3518.view.fragments.FinancesRecyclerViewFragment;

public class TabbedPagerAdapter extends FragmentPagerAdapter {
    private final int ITEM_COUNT = 2;
    public static final int FRAGMENT_1 = 0;

    public TabbedPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        fragment = new FinancesRecyclerViewFragment();
        Bundle bundle = new Bundle();
        if (position == FRAGMENT_1)
            bundle.putBoolean("isIncome", true);
        else
            bundle.putBoolean("isIncome", false);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case FRAGMENT_1: return "Income";
            default: return "Expense";
        }
    }
}
