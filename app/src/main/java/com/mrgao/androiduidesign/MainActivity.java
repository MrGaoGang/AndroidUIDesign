package com.mrgao.androiduidesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mrgao.androiduidesign.animation.AnimationActivity;
import com.mrgao.androiduidesign.pay.PaymentActivity;
import com.mrgao.androiduidesign.recyclerview.RecyclerChangeHeaderActivity;
import com.mrgao.androiduidesign.recyclerview.RecyclerStickHeaderActivity;
import com.mrgao.androiduidesign.tab.NavigationActivity;
import com.mrgao.androiduidesign.tab.TabDemoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.payBtn)
    Button payBtn;
    @BindView(R.id.reStickHeaderBtn)
    Button reStickHeaderBtn;
    @BindView(R.id.reChangeHeaderBtn)
    Button reChangeHeaderBtn;
    @BindView(R.id.tabUseBtn)
    Button tabUseBtn;
    @BindView(R.id.animationBtn)
    Button animationBtn;
    @BindView(R.id.naviBtn)
    Button naviBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.payBtn, R.id.reStickHeaderBtn, R.id.reChangeHeaderBtn, R.id.tabUseBtn, R.id.animationBtn, R.id.naviBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.payBtn:
                startActivity(new Intent(MainActivity.this, PaymentActivity.class));
                break;
            case R.id.reStickHeaderBtn:
                startActivity(new Intent(MainActivity.this, RecyclerStickHeaderActivity.class));

                break;
            case R.id.reChangeHeaderBtn:
                startActivity(new Intent(MainActivity.this, RecyclerChangeHeaderActivity.class));

                break;
            case R.id.tabUseBtn:
                startActivity(new Intent(MainActivity.this, TabDemoActivity.class));

                break;
            case R.id.animationBtn:
                startActivity(new Intent(MainActivity.this, AnimationActivity.class));

                break;
            case R.id.naviBtn:
                startActivity(new Intent(MainActivity.this, NavigationActivity.class));

                break;
        }
    }
}
