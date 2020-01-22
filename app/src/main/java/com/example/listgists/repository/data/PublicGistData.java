package com.example.listgists.repository.data;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// this is the class contains one public gist info
// For demo only, we contain very minimum fields
public class PublicGistData {
    public class Owner {
        @SerializedName("id")
        private String id;
        public String getId() {
            return id;
        }

        @SerializedName("login")
        private String login;
        public String getLogin() {
            return login;
        }
    }
    @SerializedName("id")
    private String id;

    @SerializedName("url")
    private String url;

    @SerializedName("forks_url")
    private String forksUrl;

    @SerializedName("commits_url")
    private String commitsUrl;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("comments")
    private String mComments;

    // the files Json contains dynamic keys.
    // Not the best solution to make it Map<String, Object>
    @SerializedName("files")
    private Map<String, LinkedTreeMap<String, String>> result;

    @SerializedName("owner")
    private Owner owner;

    public String getUrl() {
        return url;
    }

    public String getForksUrl() {
        return forksUrl;
    }

    public String getCommitsUrl() {
        return commitsUrl;
    }

    public String getId() {
        return id;
    }

    public String getFileName() {
        if (result != null && result.size() > 0) {
            for (LinkedTreeMap<String, String> value :  result.values()) {
                if (value.containsKey("type")) {
                    return value.get("type");
                }
            }
        }
        return null;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getComments() {
        return mComments;
    }

    public String getDescription() {
        return mDescription;
    }
}
