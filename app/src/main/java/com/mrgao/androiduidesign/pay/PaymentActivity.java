package com.mrgao.androiduidesign.pay;

import android.annotation.SuppressLint;
import android.app.Service;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mrgao.androiduidesign.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentActivity extends AppCompatActivity {

    @BindView(R.id.main_top)
    LinearLayout mainTop;
    @BindView(R.id.toolbarNormal)
    RelativeLayout toolbarNormal;
    @BindView(R.id.toolbarColls)
    RelativeLayout toolbarColls;
    @BindView(R.id.collsLayout)
    CollapsingToolbarLayout collsLayout;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    List<String> list = new ArrayList<>();

    private PayAdapter mPayAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        initView();
        dealAppbar();
        moveItem();

    }

    private void initView() {

        for (int i = 0; i < 20; i++) {
            list.add("item " + i);
        }

        recyclerview.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerview.setAdapter(mPayAdapter = new PayAdapter(list));
    }

    /*通过监听appbar的拉伸，来显示和隐藏上方不同的toolbar*/
    private void dealAppbar() {

        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @SuppressLint("Range")
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset == 0) {//完全展开
                    toolbarNormal.setVisibility(View.VISIBLE);
                    toolbarColls.setVisibility(View.GONE);
                    toolbarNormal.setAlpha(255);
                } else if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {//完全折叠
                    toolbarNormal.setVisibility(View.GONE);
                    toolbarColls.setVisibility(View.VISIBLE);
                    toolbarColls.setAlpha(255);
                } else {
                    //上滑，改变可见度
                    if (toolbarNormal.getVisibility() == View.VISIBLE) {
                        toolbarNormal.setAlpha(255 - Math.abs(verticalOffset));
                    } else if (toolbarColls.getVisibility() == View.VISIBLE) {
                        //下滑，当达到一定的距离显示展开后的
                        if (Math.abs(verticalOffset) > 0 && Math.abs(verticalOffset) < 100) {
                            toolbarNormal.setVisibility(View.VISIBLE);
                            toolbarColls.setVisibility(View.GONE);
                            toolbarNormal.setAlpha(0);
                        }

                        int alpha = (int) (255 * (Math.abs(verticalOffset) / 100f));
                        toolbarColls.setAlpha(alpha);
                    }
                }

            }
        });

    }

    /*长按可以移动item*/
    private void moveItem() {
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {

            /*这个方法是设置是否滑动时间，以及拖拽的方向，
            所以在这里需要判断一下是列表布局还是网格布局，
            如果是列表布局的话则拖拽方向为DOWN和UP，
            如果是网格布局的话则是DOWN和UP和LEFT和RIGHT，*/
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }

            }


            /*而onMove（）方法则是我们在拖动的时候不断回调的方法，
            在这里我们需要将正在拖拽的item和集合的item进行交换元素，
            然后在通知适配器更新数据*/
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //得到当拖拽的viewHolder的Position
                int fromPosition = viewHolder.getAdapterPosition();
                //拿到当前拖拽到的item的viewHolder的position
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(list, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(list, i, i - 1);
                    }
                }
                mPayAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }


            /*onSwiped（）是替换后调用的方法，
            可以不用管。然后我们希望在拖拽的时候将被拖拽的Item高亮，
            这样用户体验要好很多，
            所以我们要重写CallBack对象中的onSelectedChanged（）和clearView（）方法，
            在选中的时候设置高亮背景色，在完成的时候移除高亮背景色*/
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(0);
            }
        });

        mItemTouchHelper.attachToRecyclerView(recyclerview);
        mPayAdapter.setOnItemClickListener(new PayAdapter.OnItemClickListener() {
            @Override
            public void onLongClick(int position, RecyclerView.ViewHolder holder) {
                mItemTouchHelper.startDrag(holder);
                //获取系统震动服务
                Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
                //震动70毫秒
                vib.vibrate(70);

            }
        });

    }

}
