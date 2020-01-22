package com.example.listgists.view.gitdetail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listgists.GistApplication;
import com.example.listgists.R;
import com.example.listgists.view.base.AbstractFragment;
import com.example.listgists.view.data.GistDetailData;
import com.example.listgists.view.data.GistSimpleData;
import com.example.listgists.viewmodel.GistDetailFragmentViewModel;
import com.example.listgists.viewmodel.GistViewModelFactory;

public class GistDetailFragment extends AbstractFragment {
    public static final String TAG = "GistDetailFragment";
    private static final String GIST_SIMPLE_DATA = "GIST_SIMPLE_DATA";
    private RecyclerView mRecyclerView;
    private Button mBtnChangeFavourite;
    private GistDetailAdapter mAdapter;
    private GistDetailFragmentViewModel mViewModel;

    public static GistDetailFragment newInstance(GistSimpleData data) {
        GistDetailFragment fragment = new GistDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(GIST_SIMPLE_DATA, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // FOR DEMO PURPOSE, WE JUST USE DI for VM
        mViewModel = ViewModelProviders.of(this,
                new GistViewModelFactory(
                        GistApplication.get().getRepository()))
                .get(GistDetailFragmentViewModel.class);
    }

    @Override
    protected void init(View view) {
        mBtnChangeFavourite = view.findViewById(R.id.button_change_favourite);
        mBtnChangeFavourite.setOnClickListener(
                v->{
                    mViewModel.changeFavouriteState();
                }
        );
        mRecyclerView = view.findViewById(R.id.recycler_view_gist_detail);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), RecyclerView.VERTICAL, false));

        mAdapter = new GistDetailAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gistdetail;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    protected void listenDataChange() {
        mViewModel.mDetailDisplayData.observe(
                this,
                      dataWrapper -> {
                          if (dataWrapper.isSuccess()) {
                              mAdapter.setData(dataWrapper.getGistUiData());
                          } else {
                              Toast.makeText(getContext(),
                                             dataWrapper.getErrorMessage(),
                                             Toast.LENGTH_LONG).show();
                          }

                      });

        Bundle bundle = getArguments();
        GistSimpleData simpleData = bundle != null ? bundle.getParcelable(GIST_SIMPLE_DATA) : null;

        if (simpleData != null) {
            mViewModel.fetchGistDetail(simpleData);
        } else {
            Toast.makeText(getContext(), "No stock detail is found", Toast.LENGTH_LONG).show();
        }

    }

    public GistDetailData getDetailData() {
        return mViewModel.getGistDetailData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewModel.mDetailDisplayData.removeObservers(this);
    }

}
