package com.mrgao.androiduidesign.recyclerview.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mrgao.androiduidesign.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTextView;

    public ItemViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.text_view);
    }

    public void bind(String label) {
        mTextView.setText(label);
    }
}
