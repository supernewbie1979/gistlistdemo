package com.example.listgists.view.gitdetail;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listgists.R;
import com.example.listgists.view.data.DetailDisplayData;

import java.util.ArrayList;
import java.util.List;

public final class GistDetailAdapter extends RecyclerView.Adapter<GistDetailAdapter.ViewHolder> {


    private List<DetailDisplayData> mDisplayData;

    public GistDetailAdapter() {
        mDisplayData = new ArrayList<>();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mValue;
        TextView mLabel;

        public ViewHolder(View v) {
            super(v);
            mLabel = v.findViewById(R.id.text_detail_label);
            mValue = v.findViewById(R.id.text_detail_value);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.single_gist_detail_item,
                                           parent,
                                           false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.mLabel.getContext();
        holder.mLabel.setText(context.getString(mDisplayData.get(position).getResId()));
        if (TextUtils.isEmpty(mDisplayData.get(position).getValue())) {
            holder.mValue.setText(context.getString(R.string.empty));
        } else {
            holder.mValue.setText(mDisplayData.get(position).getValue());
        }
    }

    @Override
    public int getItemCount() {
        return mDisplayData.size();
    }

    public void setData(List<DetailDisplayData> dataList) {
        mDisplayData.clear();
        if (dataList != null) {
            mDisplayData.addAll(dataList);
        }
        notifyDataSetChanged();
    }
}