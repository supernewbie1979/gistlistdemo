package com.example.listgists.repository;

import com.example.listgists.repository.data.PublicGistData;

import java.util.List;

import io.reactivex.Single;

public interface IRepository {
    /**
     * load the user data, for demo purpose, it is always loaded from the network.
     * @return List<PublicGistData> list of public gist data
     */
    Single<List<PublicGistData>> loadUserDataList();

    /**
     * get the gist from the cache
     * @return List<PublicGistData> list of public gist data
     */
    Single<PublicGistData> getGistFromCache(String gistId);

    /**
     * get the number of user gist
     * @param userName
     * @return
     */
    Single<Integer> getUserGistsNumberFromNetwork(String userName);
}
