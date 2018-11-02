package com.expensetracker.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.expensetracker.Fragment.CategoryFragment;
import com.expensetracker.Fragment.MonthlyFragment;
import com.expensetracker.Fragment.YearlyFragment;

/**
 * Created by shishir.suvarna on 10/31/2018.
 */

public class Tabadapter extends FragmentPagerAdapter {

    int totalTabs;
    private Context myContext;

    public Tabadapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MonthlyFragment monthlyFragment = new MonthlyFragment();
                return monthlyFragment;

            case 1:
                YearlyFragment yearlyFragment = new YearlyFragment();
                return yearlyFragment;

            case 2:
                CategoryFragment categoryFragment = new CategoryFragment();
                return categoryFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
