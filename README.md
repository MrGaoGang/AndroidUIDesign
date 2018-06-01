# AndroidUIDesign
一些界面的设计（模仿支付宝的界面，以及Recyclerview的拖动，Recyclerview顶部悬停）



## 一些效果的实现

### 1. 模仿支付宝主界面上划将部分功能缩小至顶部标题栏，（PaymentActivity）

  主要实现是使用appbarlayout+CollapsingToolbarLayout+toolbar，并监听appbarlayout的缩放状态，来控制需要显示的view。<br>
  
```Java

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

```

### 2. 实现recyclerview每一个Item拖拽移动，（PaymentActivity）
  主要自定义一个ItemTouchHelper。<br>
  
```Java
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

```


### 3. 实现Android底部bar切换fragment（使用bottom-navigation-bar）（NavigationActivity）
  主要使用这个库哦：    compile 'com.ashokvarma.android:bottom-navigation-bar:1.2.0'

### 4. tablayout+viewpager实现底部bar切换fragment。（TabDemoActivity）
  主要是结合了tablayout和viewpage实现切换fragment。  
  （1）viewpage绑定pageradapter  
  （2）tablayout绑定viewpager  
  （3）为每一个tablayout设置icon   
  （4）监听selectlistener，动态改变icon  

### 5. 使用recyclerview实现顶部悬停效果。（RecyclerStickHeaderActivity）
  主要使用：将recyclerview和悬浮的view使用framelayout包裹起来，再监听recyclerview的滑动，判断当前滑动第一个可见的itemview，并是将每一个Item视图
  的资源显示到悬停的view上。

```Java

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


```

### 6. 使用recyclerview上划悬停部分组件效果。（与5 类似。）

