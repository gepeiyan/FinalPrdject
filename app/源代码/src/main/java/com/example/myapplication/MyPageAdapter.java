package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentManager;

public class MyPageAdapter extends FragmentPagerAdapter {
    public MyPageAdapter(FragmentManager manager){
        super(manager);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new homeFragment ();
        }else{
            return new listFragment ();
        }
    }
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "主页";
        }else{
            return "名家随笔";
        }
    }
    @Override
    public int getCount() {
        return 2;
    }
}
