package com.example.assigmentgd1.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.assigmentgd1.fragment.IntroFragment1;
import com.example.assigmentgd1.fragment.IntroFragment2;
import com.example.assigmentgd1.fragment.IntroFragment3;


public class ViewPager2Adapter extends FragmentStateAdapter {

    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position) {
            default:
            case 0:
                fragment = new IntroFragment1();
                break;
            case 1:
                fragment = new IntroFragment2();
                break;
            case 2:
                fragment = new IntroFragment3();
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
