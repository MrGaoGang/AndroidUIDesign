package com.mrgao.androiduidesign.tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by mr.gao on 2018/4/24.
 * Package:    com.mrgao.androiduidesign.tab
 * Create Date:2018/4/24
 * Project Name:AndroidUIDesign
 * Description:
 */

public class SingleFragmentPagerAdapter extends FragmentPagerAdapter {
    List<NewFragment> mNewFragments;
    List<String> mStringList;

    public SingleFragmentPagerAdapter(FragmentManager fm, List<NewFragment> list, List<String> titles) {
        super(fm);
        mNewFragments = list;
        mStringList = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mNewFragments.get(position);
    }

    @Override
    public int getCount() {
        return mNewFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mStringList.get(position);
    }
}
