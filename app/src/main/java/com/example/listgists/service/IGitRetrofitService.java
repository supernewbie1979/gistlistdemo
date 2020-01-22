package com.example.listgists.service;

import com.example.listgists.repository.data.PublicGistData;
import com.example.listgists.service.data.UserGistRawData;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IGitRetrofitService {
    String END_POINT = "https://api.github.com/";
    @GET("gists/public?since")
    Single<List<PublicGistData>> getPublicGists();

    @GET("users/{username}/gists")
    Single<List<UserGistRawData>> getUserGists(@Path("username") String userName);
}
