package com.example.listgists;

import android.app.Application;
import android.content.res.Configuration;

import com.example.listgists.repository.IRepository;
import com.example.listgists.repository.RepositoryProvider;

public class GistApplication extends Application {

    private IRepository repository;
    private static GistApplication context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        repository = RepositoryProvider.getInstance().getRepository();
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public IRepository getRepository() {
        return repository;
    }

    public static GistApplication get() {
        return context;
    }
}
