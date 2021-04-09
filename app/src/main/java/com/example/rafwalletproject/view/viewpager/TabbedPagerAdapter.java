package com.example.rafwalletproject.view.viewpager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.rafwalletproject.view.fragments.FinancesRecyclerViewFragment;

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
        if (position == FRAGMENT_1) {
            fragment = new FinancesRecyclerViewFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean("isIncome", true);
            fragment.setArguments(bundle);
        } else {
            fragment = new FinancesRecyclerViewFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean("isIncome", false);
            fragment.setArguments(bundle);
        }
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
            case FRAGMENT_1: return "1";
            default: return "2";
        }
    }
}
