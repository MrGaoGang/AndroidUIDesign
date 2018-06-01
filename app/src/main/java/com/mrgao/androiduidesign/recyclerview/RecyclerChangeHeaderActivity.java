package com.mrgao.androiduidesign.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mrgao.androiduidesign.R;
import com.mrgao.androiduidesign.recyclerview.adapter.ComplexAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerChangeHeaderActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_tab_name)
    TextView mTvTabName;
    private ComplexAdapter mComplexAdapter;

    private List<String> mContentList = new ArrayList<>();
    private View mFakeTabView;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_change_header);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 40; i++) {
            mContentList.add("Item " + i);
        }
    }

    private void initView() {

        mFakeTabView = findViewById(R.id.fake_tab_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mComplexAdapter = new ComplexAdapter(mContentList);
        mRecyclerView.setAdapter(mComplexAdapter);

        /**
         * 实现的思路简单是：
         * recyclerview中添加一个headerview，此headerview和悬浮的是一样，当向上滑动的时候，判断第一个可见的位置，
         * 如果第一个可见的位置为数据位置，那么此时headerview已经在屏幕之外，则显示与recyclerview同级的且和headerview的view。
         * 监听recyclerview的滑动，
         */
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int firstPosition = mLayoutManager.findFirstVisibleItemPosition();
                if (firstPosition >= 2) {
                    mFakeTabView.setVisibility(View.VISIBLE);
                } else {
                    mFakeTabView.setVisibility(View.GONE);
                }
                int tabIndex = (firstPosition - 2);
                mTvTabName.setText("Tab " + tabIndex);
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }
}

