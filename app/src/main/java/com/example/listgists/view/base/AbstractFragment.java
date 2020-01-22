package com.example.listgists.view.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

// a base class contains a few common methods for the fragment creation.
public abstract class AbstractFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        listenDataChange();
    }

    protected void init(View view) {
    // empty Impl, child may need to implement
    }

    protected void listenDataChange(){
        // empty Impl, child may need to implement
    }

    //child must implement this
    public abstract String getFragmentTag();

    // child must implement this
    protected abstract int getLayoutId();


}
