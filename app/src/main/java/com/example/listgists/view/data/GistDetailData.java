package com.example.listgists.view.data;

import android.os.Parcel;

/**
 * This class contains one gist detail information.
 * The class object will be converted to DetailDisplayData which is for
 * showing in detail UI
 */
public class GistDetailData extends GistSimpleData {
    private String mForksUrl;
    private String mCommitsUrl;
    private String mDescription;
    private String mComments;

    public GistDetailData(String id) {
        super(id);
    }

    protected GistDetailData(Parcel in) {
        super(in);

        mForksUrl = in.readString();
        mCommitsUrl = in.readString();
        mDescription = in.readString();
        mComments = in.readString();
    }

    public static final Creator<GistDetailData> CREATOR = new Creator<GistDetailData>() {
        @Override
        public GistDetailData createFromParcel(Parcel in) {
            return new GistDetailData(in);
        }

        @Override
        public GistDetailData[] newArray(int size) {
            return new GistDetailData[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(mForksUrl);
        dest.writeString(mCommitsUrl);
        dest.writeString(mDescription);
        dest.writeString(mForksUrl);
    }

    public int describeContents() {
        return 0;
    }

    public void setForksUrl(String forksUrl) {
        this.mForksUrl = forksUrl;
    }

    public String getForksUrl() {
        return mForksUrl;
    }

    public void setCommitsUrl(String commitsUrl) {
        this.mCommitsUrl = commitsUrl;
    }

    public String getCommitsUrl() {
        return mCommitsUrl;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setComments(String comments) {
        this.mComments = comments;
    }

    public String getComments() {
        return mComments;
    }

    public GistSimpleData getSimpleData() {
        return this;
    }

}
