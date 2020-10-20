package com.cao.commons.widget;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPageAdapter extends FragmentPagerAdapter {

    public List<Fragment> fragmentsList;
    public List<String> tabTitleList;


    public ViewPageAdapter(FragmentManager fm, List<Fragment> fragments, List<String> tabTitleList) {
        super(fm);
        this.fragmentsList = fragments;
        this.tabTitleList = tabTitleList;
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    @Override
    public Fragment getItem(int arg0) {
        return fragmentsList.get(arg0);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (tabTitleList != null && tabTitleList.size() > 0) ? tabTitleList.get(position) : "";
    }

}
