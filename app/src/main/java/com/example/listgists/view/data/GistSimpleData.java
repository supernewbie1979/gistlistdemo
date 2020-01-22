package com.example.listgists.view.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The fewer gist fields for a single item in recyclerview
 */
public class GistSimpleData implements Parcelable {

    private String mId; // the identity for a Gist
    private String mUrl;
    private String mFileName;
    private boolean mIsFavourite = false;
    private int mSharedCount;
    public GistSimpleData(String id) {
        this.mId = id;
    }

    protected GistSimpleData(Parcel in) {
        mId = in.readString();
        mUrl = in.readString();
        mFileName = in.readString();
        mIsFavourite = in.readByte() != 0;
        mSharedCount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mUrl);
        dest.writeString(mFileName);
        dest.writeByte((byte) (mIsFavourite ? 1 : 0));
        dest.writeInt(mSharedCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GistSimpleData> CREATOR = new Creator<GistSimpleData>() {
        @Override
        public GistSimpleData createFromParcel(Parcel in) {
            return new GistSimpleData(in);
        }

        @Override
        public GistSimpleData[] newArray(int size) {
            return new GistSimpleData[size];
        }
    };

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getId() {
        return mId;
    }

    public void setFileName(String fileName) {
        this.mFileName = fileName;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setFavourite(boolean favourite) {
        this.mIsFavourite = favourite;
    }
    public boolean getFavourite() {
        return mIsFavourite;
    }

    public void setSharedCount(int sharedCount) {
        this.mSharedCount = sharedCount;
    }

    public int getSharedCount() {
        return mSharedCount;
    }

}
