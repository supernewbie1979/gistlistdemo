package com.example.listgists.service.data;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

// this is the class contains one gist owned by an user.
// For demo only, we contain only one url.
public class UserGistRawData {
    @SerializedName("url")
    private String url;
    public String getUrl() {
        return url;
    }
}
