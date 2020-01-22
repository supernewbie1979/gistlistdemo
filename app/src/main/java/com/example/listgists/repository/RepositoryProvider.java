package com.example.listgists.repository;

import androidx.annotation.WorkerThread;

import com.example.listgists.service.IGitRetrofitService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.listgists.service.IGitRetrofitService.END_POINT;

public final class RepositoryProvider {
    private static IRepository repository;
    private static RepositoryProvider mInstance;

    public static RepositoryProvider getInstance() {
        if (mInstance == null) {
            synchronized (RepositoryProvider.class) {
                if (mInstance == null) {
                    mInstance = new RepositoryProvider();
                }
            }
        }
        return mInstance;
    }

    private RepositoryProvider() {
        repository = new RepositoryImpl();
    }

    public IRepository getRepository() {
        return repository;
    }
}
