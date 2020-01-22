package com.example.listgists.service;

import androidx.annotation.WorkerThread;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.listgists.service.IGitRetrofitService.END_POINT;

/**
 * Class to create and maintain the retrofit service
 */
public final class GitRetrofitServiceProvider {
    private static final int CONNECTION_TIME_OUT = 5; //5s
    private static final int READ_TIME_OUT = 5; //5s
    private static IGitRetrofitService gitRetrofitService;
    private static GitRetrofitServiceProvider mInstance;

    @WorkerThread
    public static GitRetrofitServiceProvider getInstance() {
        if (mInstance == null) {
            synchronized (GitRetrofitServiceProvider.class) {
                if (mInstance == null) {
                    mInstance = new GitRetrofitServiceProvider();
                }
            }
        }
        return mInstance;
    }

    private GitRetrofitServiceProvider() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .build();

        final Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(END_POINT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync()) // Use OkHttp's internal thread pool.
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        gitRetrofitService = retrofit.create(IGitRetrofitService.class);

    }

    public IGitRetrofitService getGitRetrofitService() {
        return gitRetrofitService;
    }
}
