package com.mrgao.androiduidesign.tab;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.mrgao.androiduidesign.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabDemoActivity extends AppCompatActivity {

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    final List<String> titleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_demo);
        ButterKnife.bind(this);
        initData();

        initTab();
    }


    private void initData() {
        final List<NewFragment> fragments = new ArrayList<>();
        fragments.add(NewFragment.newInstance("第一个fragment"));
        fragments.add(NewFragment.newInstance("第二个fragment"));
        fragments.add(NewFragment.newInstance("第三个fragment"));
        fragments.add(NewFragment.newInstance("第三个fragment"));


        for (int i = 0; i < fragments.size(); i++) {
            titleList.add("第" + (i + 1) + "个");
        }

        viewPager.setAdapter(new SingleFragmentPagerAdapter(getSupportFragmentManager(),fragments,titleList));

        viewPager.setCurrentItem(0);

        tablayout.setupWithViewPager(viewPager);
    }


    /*实现在下方显示*/
    private void initTab() {

        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            TabLayout.Tab tab = tablayout.getTabAt(i);
            if (i == 0) {
                tab.setIcon(R.mipmap.shouyese);
            } else if (i == 1) {
                tab.setIcon(R.mipmap.yule);
            } else if (i == 2) {
                tab.setIcon(R.mipmap.xinxi);
            } else {
                tab.setIcon(R.mipmap.geren);
            }


        }

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                reset();
                select(tab);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void reset() {
        tablayout.getTabAt(0).setIcon(R.mipmap.souye);
        tablayout.getTabAt(1).setIcon(R.mipmap.yule);
        tablayout.getTabAt(2).setIcon(R.mipmap.xinxi);
        tablayout.getTabAt(3).setIcon(R.mipmap.geren);

    }

    private void select(TabLayout.Tab tab) {
        int i = tab.getPosition();
        if (i == 0) {
            tab.setIcon(R.mipmap.shouyese);
        } else if (i == 1) {
            tab.setIcon(R.mipmap.yule_se);
        } else if (i == 2) {
            tab.setIcon(R.mipmap.xinxise);
        } else {
            tab.setIcon(R.mipmap.gerense);
        }
    }

}
