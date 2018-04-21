package com.mrgao.androiduidesign.pay;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mrgao.androiduidesign.R;

import java.util.List;

/**
 * Created by mr.gao on 2018/4/21.
 * Package:    com.mrgao.androiduidesign.pay
 * Create Date:2018/4/21
 * Project Name:AndroidUIDesign
 * Description:
 */

public class PayAdapter  extends RecyclerView.Adapter<PayAdapter.PayViewHolder>{

    private List<String> mStrings;
    private OnItemClickListener mOnItemClickListener;

    public PayAdapter(List<String> strings) {
        mStrings = strings;
    }

    @Override
    public PayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.pay_center_item,parent,false);
        return new PayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PayViewHolder holder, final int position) {
        holder.mTextView.setText(mStrings.get(position));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onLongClick(position,holder);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }

    class PayViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;
        public PayViewHolder(View itemView) {
            super(itemView);
            mTextView=(TextView) itemView.findViewById(R.id.text);
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onLongClick(int position, RecyclerView.ViewHolder holder);
    }
}
