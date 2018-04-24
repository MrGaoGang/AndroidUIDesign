package com.mrgao.androiduidesign.tab;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.mrgao.androiduidesign.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.navBottom)
    BottomNavigationBar bottomNavigationBar;
    final List<String> titleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        initView();
        initData();


    }

    private void initView() {


        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC
                );
        bottomNavigationBar.setBarBackgroundColor(R.color.blue);//set background color for navigation bar
        bottomNavigationBar.setInActiveColor(R.color.white);//unSelected icon color


        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.souye, "首页")
                        .setActiveColorResource(R.color.green))
                .addItem(new BottomNavigationItem(R.mipmap.yule, "娱乐")
                        .setActiveColorResource(R.color.orange))
                .addItem(new BottomNavigationItem(R.mipmap.xinxi, "消息")
                        .setActiveColorResource(R.color.lime))
                .addItem(new BottomNavigationItem(R.mipmap.geren, "个人")
                        .setBadgeItem(new BadgeItem().setText("10")))
                .setFirstSelectedPosition(0)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);
    }


    private void initData() {
        final List<NewFragment> fragments = new ArrayList<>();
        fragments.add(NewFragment.newInstance("第一个fragment"));
        fragments.add(NewFragment.newInstance("第二个fragment"));
        fragments.add(NewFragment.newInstance("第三个fragment"));
        fragments.add(NewFragment.newInstance("第四个fragment"));


        for (int i = 0; i < fragments.size(); i++) {
            titleList.add("第" + (i + 1) + "个");
        }

        viewPager.setAdapter(new SingleFragmentPagerAdapter(getSupportFragmentManager(),
                fragments, titleList));

        viewPager.setCurrentItem(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationBar.selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onTabSelected(int position) {
        viewPager.setCurrentItem(position);


    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
