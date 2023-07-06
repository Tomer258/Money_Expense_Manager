package com.example.moneyexpensemanager.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.moneyexpensemanager.fragments.Personal_Income_fragment;
import com.example.moneyexpensemanager.fragments.Personal_Outcome_fragment;
import com.google.android.material.tabs.TabLayout;

public class myViewpagerAdapter extends FragmentStateAdapter
{
    private TabLayout tabLayout;
    public myViewpagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
            {
                return new Personal_Income_fragment();
            }
            case 1:
            {
                return new Personal_Outcome_fragment();
            }
            default:
            {
                return null;
            }
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
