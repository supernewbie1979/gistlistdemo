package com.example.listgists.repository;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.example.listgists.service.GitRetrofitServiceProvider;
import com.example.listgists.service.IGitRetrofitService;
import com.example.listgists.repository.data.PublicGistData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.schedulers.Schedulers;

class RepositoryImpl implements IRepository {
    private static RepositoryImpl mInstance;
    private Map<String, PublicGistData> mPublicGistList;
    private IGitRetrofitService mGitRetrofitService;

    public RepositoryImpl() {
        mPublicGistList = new HashMap<>();
    }

    private IGitRetrofitService getGitRetrofitService() {
        if (mGitRetrofitService == null) {
            mGitRetrofitService = GitRetrofitServiceProvider.getInstance().getGitRetrofitService();
        }
        return mGitRetrofitService;
    }

    // load the data from network and save it to cache
    public synchronized Single<List<PublicGistData>> loadUserDataList() {
        return loadUserDataListFromNetwork()
                .map(inputData->{
                    saveToCache(inputData);
                    return inputData;
                });
    }

    private void saveToCache(@NonNull List<PublicGistData> inputs) {
        if (inputs != null) {
            mPublicGistList.clear();
            for (PublicGistData data: inputs) {
                mPublicGistList.put(data.getId(), data);
            }
        }
    }

    public synchronized Single<List<PublicGistData>> loadUserDataListFromNetwork() {
        return getGitRetrofitService().getPublicGists()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public synchronized Single<PublicGistData> getGistFromCache(String gistId) {
        // For demo purpose, just return empty object if cache is not found
        PublicGistData data = null;
        if (mPublicGistList != null) {
            data = mPublicGistList.get(gistId);
        }

        return Single.just(data != null ? data : new PublicGistData());
    }

    public synchronized Single<Integer> getUserGistsNumberFromNetwork(String userName) {
        return getGitRetrofitService().getUserGists(userName)
                .subscribeOn(Schedulers.io())
                .flatMap(gists-> Single.just(gists.size()));
    }

}
