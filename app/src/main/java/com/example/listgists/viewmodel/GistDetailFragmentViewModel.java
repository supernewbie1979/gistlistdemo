package com.example.listgists.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.listgists.repository.IRepository;
import com.example.listgists.repository.data.PublicGistData;
import com.example.listgists.view.data.DetailDataMapper;
import com.example.listgists.view.data.DetailDisplayData;
import com.example.listgists.view.data.GistDetailData;
import com.example.listgists.view.data.GistSimpleData;
import com.example.listgists.view.data.GistUiDataWrapper;
import com.example.listgists.viewmodel.base.AbstractViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class GistDetailFragmentViewModel extends AbstractViewModel {
    public MutableLiveData<GistUiDataWrapper<List<DetailDisplayData>>> mDetailDisplayData;
    private GistDetailData mGistDetailData;
    private DetailDataMapper mDetailDataMapper;
    private IRepository mRepository;


    public GistDetailFragmentViewModel(IRepository repository) {
        this.mRepository = repository;
        mDetailDisplayData = new MutableLiveData<>();

        mDetailDataMapper = new DetailDataMapper();
    }

    public void fetchGistDetail(GistSimpleData simpleData) {
        GistUiDataWrapper<List<DetailDisplayData>> wrapper = new GistUiDataWrapper();

        compositeDisposable.add(mRepository.getGistFromCache(simpleData.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(publicGistData->{
                    mGistDetailData = convertFrom(publicGistData, simpleData);
                    wrapper.setGistUiData(mDetailDataMapper.getDataToDisplay(mGistDetailData));
                    wrapper.setSuccess(true);
                    mDetailDisplayData.setValue(wrapper);
                }, throwable -> {
                    wrapper.setSuccess(false);
                    wrapper.setErrorMessage(throwable.getMessage());
                    mDetailDisplayData.setValue(wrapper);
                }));
    }

    private GistDetailData convertFrom(PublicGistData publicGistData,
                                       GistSimpleData simpleData) {
        GistDetailData detailData = new GistDetailData(publicGistData.getId());

        detailData.setForksUrl(publicGistData.getForksUrl());
        detailData.setCommitsUrl(publicGistData.getCommitsUrl());
        detailData.setDescription(publicGistData.getDescription());
        detailData.setComments(publicGistData.getComments());
        detailData.setFileName(publicGistData.getFileName());

        detailData.setFavourite(simpleData.getFavourite());
        detailData.setSharedCount(simpleData.getSharedCount());
        return detailData;
    }

    public void changeFavouriteState() {
        boolean favourite = mGistDetailData.getFavourite();
        mGistDetailData.setFavourite(!favourite);

        // for DEMO, we always refresh the whole item even the favourite state is changed
        GistUiDataWrapper<List<DetailDisplayData>> wrapper = mDetailDisplayData.getValue();
        wrapper.setGistUiData(mDetailDataMapper.getDataToDisplay(mGistDetailData));
        mDetailDisplayData.setValue(wrapper);
    }

    public GistDetailData getGistDetailData() {
        return mGistDetailData;
    }
}
