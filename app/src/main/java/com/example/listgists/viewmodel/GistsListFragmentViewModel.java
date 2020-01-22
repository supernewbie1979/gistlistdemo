package com.example.listgists.viewmodel;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.listgists.repository.IRepository;
import com.example.listgists.repository.data.PublicGistData;
import com.example.listgists.view.data.GistDetailData;
import com.example.listgists.view.data.GistSimpleData;
import com.example.listgists.view.data.GistUiDataWrapper;
import com.example.listgists.viewmodel.base.AbstractViewModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class GistsListFragmentViewModel extends AbstractViewModel {
    public MutableLiveData<GistUiDataWrapper<List<GistSimpleData>>> mUiDataMutableLiveData;
    private static final int SHARED_ACCOUNT_NOT_FOUND = -1;
    private IRepository mRepository;


    public GistsListFragmentViewModel(IRepository repository) {
        mRepository = repository;
        mUiDataMutableLiveData = new MutableLiveData<>();
    }

    public void getGistsList() {
        GistUiDataWrapper wrapper = new GistUiDataWrapper();
        compositeDisposable.add(mRepository
                .loadUserDataList()
                .flattenAsObservable(list->list)
                .flatMap(publicGistData->mRepository
                                      .getUserGistsNumberFromNetwork(
                                              publicGistData.getOwner().getLogin())
                                      .toObservable()
                                      .onErrorResumeNext(Observable.just(SHARED_ACCOUNT_NOT_FOUND)),
                        (publicGistData, sharedCount) -> handlePublicGistData(publicGistData, sharedCount))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleDataList->{
                    wrapper.setSuccess(true);
                    wrapper.setGistUiData(simpleDataList);
                    mUiDataMutableLiveData.setValue(wrapper);
                }, throwable->{
                    wrapper.setSuccess(false);
                    wrapper.setErrorMessage(throwable.getMessage());
                    mUiDataMutableLiveData.setValue(wrapper);
                }));
    }

    private GistSimpleData handlePublicGistData(@NonNull PublicGistData publicGistData, int sharedAccount) {
        GistDetailData uiData = new GistDetailData(publicGistData.getId());

        // update the data
        uiData.setFileName(publicGistData.getFileName());
        uiData.setSharedCount(sharedAccount);
        uiData.setUrl(publicGistData.getUrl());
        uiData.setSharedCount(sharedAccount);
        uiData.setFavourite(isFavourite(publicGistData.getId()));
        return uiData;
    }

    // NOT A GOOD WAY to loop the list to find the favourite value.
    // FOR DEMO PURPOSE, WE DO IT LIKE THIS.
    private boolean isFavourite(String id) {
        GistUiDataWrapper<List<GistSimpleData>> wrapper = mUiDataMutableLiveData.getValue();

        List<GistSimpleData> list = wrapper != null ? wrapper.getGistUiData() : null;
        if (list != null) {
            for (GistSimpleData simpleData : list) {
                if (TextUtils.equals(simpleData.getId(), id)) {
                    return simpleData.getFavourite();
                }
            }
        }
        return false;
    }

    // for demo purpose, we just reload the whole list even one item is changed
    public void updateData(@NonNull GistSimpleData simpleData) {
        GistUiDataWrapper<List<GistSimpleData>> wrapper
                                            = mUiDataMutableLiveData.getValue();
        List<GistSimpleData> gistSimpleDataList = wrapper.getGistUiData();

        if (simpleData != null && gistSimpleDataList != null) {
            for (GistSimpleData data : gistSimpleDataList) {
                if (TextUtils.equals(data.getId(), simpleData.getId())) {
                    data.setFavourite(simpleData.getFavourite());
                    wrapper.setGistUiData(gistSimpleDataList);
                    mUiDataMutableLiveData.setValue(wrapper);
                    break;
                }
            }
        }
    }
}
