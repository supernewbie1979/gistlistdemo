package com.example.listgists.view.gistlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listgists.R;
import com.example.listgists.view.data.GistSimpleData;

import java.util.ArrayList;
import java.util.List;

public final class GistsListAdapter extends RecyclerView.Adapter<GistsListAdapter.ViewHolder> {

    public interface GistsListAdapterObserver {
        void gistItemIsSelected(GistSimpleData data);
    }
    private static final int MINIMUM_SHARED_COUNT = 5;
    private List<GistSimpleData> mGistSimpleDataList;
    private GistsListAdapterObserver mObserver;

    public GistsListAdapter(GistsListAdapterObserver observer) {
        mGistSimpleDataList = new ArrayList<>();
        mObserver = observer;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextViewGistId;
        TextView mTextViewGistUrl;
        TextView mTextViewGistFileName;
        TextView mTextViewSharedCount;
        AppCompatImageView mImageFavourite;

        public ViewHolder(View v) {
            super(v);
            mTextViewGistId = v.findViewById(R.id.text_gist_id);
            mTextViewGistUrl = v.findViewById(R.id.text_gist_url);
            mTextViewGistFileName = v.findViewById(R.id.text_gist_file_name);
            mTextViewSharedCount = v.findViewById(R.id.text_gist_shared_count);
            mImageFavourite = v.findViewById(R.id.image_gist_favourite);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_gist_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GistSimpleData data = mGistSimpleDataList.get(position);
        Context context = holder.mTextViewGistFileName.getContext();
        holder.mTextViewGistId.setText(context.getString(R.string.id, data.getId()));
        holder.mTextViewGistUrl.setText(context.getString(R.string.url, data.getUrl()));
        holder.mTextViewGistFileName.setText(context.getString(R.string.file_name,
                data.getFileName()));
        if (data.getSharedCount() >= MINIMUM_SHARED_COUNT) {
            holder.mTextViewSharedCount.setText(
                                context.getString(R.string.shared_count,
                                String.valueOf(data.getSharedCount())));
            holder.mTextViewSharedCount.setVisibility(View.VISIBLE);
        } else {
            holder.mTextViewSharedCount.setVisibility(View.GONE);
        }

        holder.mImageFavourite.setImageResource(
                data.getFavourite() ?
                        R.drawable.ic_icon_favourite:R.drawable.ic_icon_not_favourite);

        holder.itemView.setOnClickListener(v->{
            mObserver.gistItemIsSelected(data);
        });
    }

    @Override
    public int getItemCount() {
        return mGistSimpleDataList.size();
    }

    public void setData(List<GistSimpleData> dataList) {
        mGistSimpleDataList.clear();
        if (dataList != null) {
            mGistSimpleDataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }
}