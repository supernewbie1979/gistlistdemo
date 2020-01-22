package com.example.listgists.view.data;

/**
 * A data wrapper class contains the data to be consumed by the UI and the error and messages
 * if the data cannot be got from the API.
 * @param <T> generic type for the UI data
 */
public class GistUiDataWrapper<T> {

    private boolean mSuccess = true;

    private String mErrorMessage;

    private T mGistUiData;

    public GistUiDataWrapper() {

    }

    public void setGistUiData(T data) {
        this.mGistUiData = data;
    }

    public T getGistUiData() {
        return mGistUiData;
    }

    public void setErrorMessage(String errorMessage) {
        this.mErrorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public void setSuccess(boolean success) {
        this.mSuccess = success;
    }

    public boolean isSuccess() {
        return this.mSuccess;
    }
}
