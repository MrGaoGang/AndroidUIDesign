package com.mrgao.androiduidesign.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.mrgao.androiduidesign.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnimationActivity extends AppCompatActivity {

    @BindView(R.id.scaleBtn)
    Button scaleBtn;
    @BindView(R.id.transalteBtn)
    Button transalteBtn;
    @BindView(R.id.rotateBtn)
    Button rotateBtn;
    @BindView(R.id.alphaBtn)
    Button alphaBtn;
    @BindView(R.id.animationSetBtn)
    Button animationSetBtn;

    ScaleAnimation mScaleAnimation;
    TranslateAnimation mTranslateAnimation;
    AlphaAnimation mAlphaAnimation;
    RotateAnimation mRotateAnimation;
    AnimationSet mAnimationSet;
    @BindView(R.id.image)
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.scaleBtn, R.id.transalteBtn, R.id.rotateBtn, R.id.alphaBtn, R.id.animationSetBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.scaleBtn:
                initScaleJava();
                break;
            case R.id.transalteBtn:
                initTranslatejava();
                break;
            case R.id.rotateBtn:
                initRotateJAVA();
                break;
            case R.id.alphaBtn:
                initAlphaJava();
                break;
            case R.id.animationSetBtn:
                initAnimationJava();
                break;
        }
    }


    /*
    * 缩放动画：
    * 图片从原始图片的0.5倍，缩放到4倍*/
    private void initScaleXML() {
        mScaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.scale);
        image.startAnimation(mScaleAnimation);

    }

    private void initScaleJava() {

        mScaleAnimation = new ScaleAnimation(0.5f, 4f, 0.5f, 4f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);

        mScaleAnimation.setDuration(3000);
        image.startAnimation(mScaleAnimation);
    }

    /*
    * 移动动画，从底部移动至默认位置*/
    private void initTranslateXML() {

        mTranslateAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.translatexml);
        mTranslateAnimation.setDuration(3000);
        image.startAnimation(mTranslateAnimation);
    }

    private void initTranslatejava() {

        mTranslateAnimation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF,
                0.5f,
                TranslateAnimation.ABSOLUTE,
                0f,
                TranslateAnimation.RELATIVE_TO_PARENT,
                1f,
                TranslateAnimation.ABSOLUTE,
                0f
        );
        mTranslateAnimation.setDuration(3000);
        image.startAnimation(mTranslateAnimation);
    }

    private void initAlphaXML() {
        mAlphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(this, R.anim.alphaxml);
        image.startAnimation(mAlphaAnimation);
    }


    private void initAlphaJava() {

        mAlphaAnimation = new AlphaAnimation(1, 0.1f);
        mAlphaAnimation.setDuration(3000);
        mAlphaAnimation.setFillAfter(true);
        image.startAnimation(mAlphaAnimation);
    }

    private void initRotateXML() {
        mRotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(this, R.anim.rotatexml);
        image.startAnimation(mRotateAnimation);
    }

    private void initRotateJAVA() {

        mRotateAnimation = new RotateAnimation(60, 420, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setDuration(3000);
        mRotateAnimation.setFillAfter(true);
        image.startAnimation(mRotateAnimation);
    }

    private void initAnimationXML() {

        mAnimationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.animationset);
        image.startAnimation(mAnimationSet);
    }

    private void initAnimationJava() {
        /*
        * <set xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="3000"
    android:fillAfter="true">

    <!--缩放动画-->
    <scale
        android:fromXScale="2"
        android:fromYScale="2"
        android:toXScale="0.5"
        android:toYScale="0.5"
        android:pivotY="50%"
        android:pivotX="50%"
        />


    <!--<translate android:fromXDelta="0"-->
               <!--android:fromYDelta="100%p"-->
               <!--android:toYDelta="0"-->
               <!--android:toXDelta="20"/>-->


    <rotate android:fromDegrees="20"
            android:pivotX="50%"
            android:pivotY="50%"
            android:toDegrees="360"/>


    <alpha android:fromAlpha="0"
           android:toAlpha="1"/>
</set>*/

        ScaleAnimation scaleAnimation=new ScaleAnimation(2f,0.5f,
                2f,0.5f,
                ScaleAnimation.RELATIVE_TO_SELF,0.5f,
                ScaleAnimation.RELATIVE_TO_SELF,0.5f);

        RotateAnimation rotateAnimation=new RotateAnimation(20,360,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,0.5f);

        AlphaAnimation alphaAnimation=new AlphaAnimation(0,1f);
        mAnimationSet=new AnimationSet(true);
        mAnimationSet.addAnimation(scaleAnimation);
        mAnimationSet.addAnimation(rotateAnimation);
        mAnimationSet.addAnimation(alphaAnimation);
        mAnimationSet.setDuration(3000);
        mAnimationSet.setFillAfter(true);
        image.startAnimation(mAnimationSet);

    }


}
