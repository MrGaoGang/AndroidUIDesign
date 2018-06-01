package com.mrgao.androiduidesign.tab;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mr.gao on 2018/1/27.
 * Package:    gao.employhelp.mrgao.base.activity
 * Create Date:2018/1/27
 * Project Name:Employhelp
 * Description:
 */

public abstract class BaseFragment extends Fragment {

    public static String FRAGMENT_ID = "fragment_id";

    public Activity mBaseActivity = null;
    /**
     * 根view
     */
    public View mRootView;

    /**
     * 是否对用户可见
     */
    protected boolean mIsVisible;
    /**
     * 是否加载完成
     * 当执行完oncreatview,View的初始化方法后方法后即为true
     */
    protected boolean mIsPrepare = false;

    private boolean mIsFirstLoading = true;

    public boolean mActivityCreate = false;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mBaseActivity = (Activity) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataInCreate(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//
//        if (mRootView != null) {
//            ViewGroup parent = (ViewGroup) mRootView.getParent();
//            if (parent != null) {
//                parent.removeView(mRootView);
//            }
//            return mRootView;
//        }
        if (mRootView == null) {
            mRootView = inflater.inflate(setLayoutResouceId(), container, false);
        }


        initView(mRootView);
        doSomethingInCreateView();
        mIsPrepare = true;
        mIsFirstLoading = true;
        onVisibleToUser();
        return mRootView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.mIsVisible = isVisibleToUser;
        if (isVisibleToUser) {
            onVisibleToUser();
        }
    }


    /**
     * 用户可见时执行的操作
     */
    public void onVisibleToUser() {
        if (mIsPrepare && mIsVisible && mBaseActivity != null) {
            onLazyLoad();
        }

        if (mIsPrepare && mIsVisible && mIsFirstLoading && mBaseActivity != null) {
            firstLazyLoad();
            mIsFirstLoading = false;
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivityCreate = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsPrepare = false;
        mIsFirstLoading = false;


    }


    /**
     * 简化findViewById
     *
     * @param resourceId
     * @param <T>
     * @return
     */
    public <T extends View> T $(int resourceId) {
        if (mRootView == null) {
            return null;
        }
        return (T) mRootView.findViewById(resourceId);
    }


    /**
     * @param rootView
     * @param resourceId
     * @param <T>
     * @return
     */
    public <T extends View> T $(View rootView, int resourceId) {
        return (T) rootView.findViewById(resourceId);
    }

    public View getRootView() {
        return mRootView;
    }

    /**
     * 设置资源id
     *
     * @return
     */
    public abstract int setLayoutResouceId();

    /**
     * 懒加载，只有当Fragment对用户可见的时候才加载数据
     */
    public abstract void onLazyLoad();


    public abstract void firstLazyLoad();

    /**
     * 初始化一些view
     */
    public abstract void initView(View rootView);

    /**
     * 在onCreate中加载数据
     *
     * @param arguments
     */
    public abstract void initDataInCreate(Bundle arguments);

    /***
     * 在onCreateView中做其他的事情
     */
    public abstract void doSomethingInCreateView();
}
