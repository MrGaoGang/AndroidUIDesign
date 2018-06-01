package com.mrgao.androiduidesign.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mrgao.androiduidesign.R;
import com.mrgao.androiduidesign.recyclerview.adapter.FeedAdapter;

/**
 * 使用自：https://blog.csdn.net/findsafety/article/details/76585361
 */
public class RecyclerStickHeaderActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RelativeLayout mSuspensionBar;
    private TextView mSuspensionTv;
    private ImageView mSuspensionIv;
    private int mCurrentPosition = 0;

    private int mSuspensionHeight;
    private LinearLayoutManager linearLayoutManager;
    private FeedAdapter adapter;
    private ScaleAnimation mScaleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_stick_header);

        mSuspensionBar = (RelativeLayout) findViewById(R.id.suspension_bar);
        mSuspensionTv = (TextView) findViewById(R.id.tv_nickname);
        mSuspensionIv = (ImageView) findViewById(R.id.iv_avatar);
        mScaleAnimation = new ScaleAnimation(0.5f, 1f, 0.5f, 1f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        mScaleAnimation.setDuration(1000);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.item_jump) {
                    Intent intent = new Intent(RecyclerStickHeaderActivity.this, MultiActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        linearLayoutManager = new LinearLayoutManager(this);
        adapter = new FeedAdapter();

        mRecyclerView = (RecyclerView) findViewById(R.id.feed_list);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mSuspensionHeight = mSuspensionBar.getHeight();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取到下一个即悬停的view视图。

                View view = linearLayoutManager.findViewByPosition(mCurrentPosition + 1);
                if (view != null) {
                    //如果即将悬停的视图顶部的位置小于已经悬停位置的view的高度
                    if (view.getTop() <= mSuspensionHeight) {
                        //随着即将悬停view向上滑动，慢慢的将已经悬停的view的Y轴坐标变小。达到一个替换的效果
                        mSuspensionBar.setY(-(mSuspensionHeight - view.getTop()));
                    } else {
                        //表示已经还没有滑动到需要悬停的位置
                        mSuspensionBar.setY(0);
                    }
                }

                /**
                 * 保证当前需要悬停的view，为recyclerview第一个可见的。
                 */
                if (mCurrentPosition != linearLayoutManager.findFirstVisibleItemPosition()) {
                    mCurrentPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    updateSuspensionBar();
                    mSuspensionBar.setY(0);
                }
            }
        });


        updateSuspensionBar();
    }


    private void updateSuspensionBar() {

        if (adapter.getItemCount() == 0) {
            mSuspensionBar.setVisibility(View.INVISIBLE);
            return;
        }
        if (mSuspensionBar.getVisibility() == View.INVISIBLE) {
            mSuspensionBar.setVisibility(View.VISIBLE);

        }

        /**
         * 为什么使用recyclerview.post
         * 是为了避免activity在初始化的时候，第一次调用此方法
         * 如果recyclerview还没有加载完成，则调用findViewHolderForAdapterPosition
         * 则会造成空指针异常。无法获取到Holder和view。
         */
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                //获取到当前view的holder视图的背景图片和名字
                FeedAdapter.FeedHolder holder = (FeedAdapter.FeedHolder) mRecyclerView.findViewHolderForAdapterPosition(mCurrentPosition);
                View view = holder.itemView;
                ImageView imageView = (ImageView) view.findViewById(R.id.iv_avatar);
                TextView textView = (TextView) view.findViewById(R.id.tv_nickname);
                mSuspensionIv.setImageDrawable(imageView.getDrawable());
                //使用动画：去掉imageview替换图片造成的闪烁问题
                mSuspensionIv.startAnimation(mScaleAnimation);
                mSuspensionTv.setText(textView.getText());
            }
        });
    }


}
