package com.example.listgists.view.data;

/**
 * a single row of data shown in the detail page.
 */
public class DetailDisplayData {
    //the string id from the resource
    private int mResId;
    // the value for the show
    private String mValue;

    public DetailDisplayData(int resId, String value) {
        this.mResId = resId;
        this.mValue = value;
    }

    public int getResId() {
        return mResId;
    }

    public String getValue() {
        return mValue;
    }

}
