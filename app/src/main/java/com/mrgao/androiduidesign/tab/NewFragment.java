package com.mrgao.androiduidesign.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mrgao.androiduidesign.R;

/**
 * Created by mr.gao on 2018/4/24.
 * Package:    com.mrgao.androiduidesign.tab
 * Create Date:2018/4/24
 * Project Name:AndroidUIDesign
 * Description:
 */

public class NewFragment extends Fragment {

    public static NewFragment sNewFragment;
    public static NewFragment newInstance(String id){
        NewFragment fragment=new NewFragment();
        Bundle bundle=new Bundle();
        bundle.putString("id",id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_layout, container, false);

        TextView textView=(TextView)view.findViewById(R.id.text);
        textView.setText(getArguments().getString("id"));
        return view;
    }
}
