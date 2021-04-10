package com.example.rafwalletproject.view.viewpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.rafwalletproject.view.fragments.AccountFragment;
import com.example.rafwalletproject.view.fragments.FinancesAddFragment;
import com.example.rafwalletproject.view.fragments.TabbedListsFragment;
import com.example.rafwalletproject.view.fragments.UserProfileFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private final int ITEM_COUNT = 4;
    public static final int FRAGMENT_1 = 0;
    public static final int FRAGMENT_2 = 1;
    public static final int FRAGMENT_3 = 2;
    public static final int FRAGMENT_4 = 3;

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case FRAGMENT_1: fragment = new AccountFragment(); break;
            case FRAGMENT_2: fragment = new FinancesAddFragment(); break;
            case FRAGMENT_3: fragment = new TabbedListsFragment(); break;
            default: fragment = new UserProfileFragment();break;
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
            case FRAGMENT_1: return "Account";
            case FRAGMENT_2: return "Add";
            case FRAGMENT_3: return "List";
            default: return "Profile";
        }
    }
}
