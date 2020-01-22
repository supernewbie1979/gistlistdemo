package com.example.listgists.view.gistlist;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.listgists.GistApplication;
import com.example.listgists.R;
import com.example.listgists.view.base.AbstractFragment;
import com.example.listgists.view.data.GistSimpleData;
import com.example.listgists.view.data.GistUiDataWrapper;
import com.example.listgists.viewmodel.GistViewModelFactory;
import com.example.listgists.viewmodel.GistsListFragmentViewModel;

import java.util.List;

public class GistsListFragment extends AbstractFragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = "GistsListFragment";
    public interface GistsListFragmentObserver {
        void gistItemIsSelected(GistSimpleData data);
    }

    SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private TextView mMessage;
    private GistsListAdapter mAdapter;
    private GistsListFragmentViewModel mViewModel;
    private GistsListFragmentObserver mObserver;

    public static GistsListFragment newInstance() {
        GistsListFragment fragment = new GistsListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this,
                new GistViewModelFactory(
                        GistApplication.get().getRepository()))
                .get(GistsListFragmentViewModel.class);
    }

    public void updateData(@NonNull GistSimpleData simpleData) {
        mViewModel.updateData(simpleData);
    }

    @Override
    protected void init(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mMessage = view.findViewById(R.id.text_message);
        mRecyclerView = view.findViewById(R.id.recycler_view_gists);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), RecyclerView.VERTICAL, false));

        mAdapter = new GistsListAdapter(data -> mObserver.gistItemIsSelected(data));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void listenDataChange() {
        mViewModel.mUiDataMutableLiveData.observe(this, data->{
            handleResponse(data);
        });
    }

    private void handleResponse(GistUiDataWrapper<List<GistSimpleData>> wrapper) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (wrapper.isSuccess()) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mAdapter.setData(wrapper.getGistUiData());
            mMessage.setVisibility(View.GONE);
        } else {
            mAdapter.setData(null);
            mRecyclerView.setVisibility(View.GONE);
            mMessage.setVisibility(View.VISIBLE);
            mMessage.setText(wrapper.getErrorMessage());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gistslist;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        mViewModel.mUiDataMutableLiveData.removeObservers(this);
    }

    @Override
    public void onRefresh() {
        mViewModel.getGistsList();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GistsListFragmentObserver) {
            mObserver = (GistsListFragmentObserver)context;
        } else {
            throw new RuntimeException("the parent must implement GistsListFragmentObserver");
        }
    }
}
