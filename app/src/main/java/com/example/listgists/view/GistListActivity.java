package com.example.listgists.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.listgists.R;
import com.example.listgists.view.base.AbstractFragment;
import com.example.listgists.view.data.GistDetailData;
import com.example.listgists.view.data.GistSimpleData;
import com.example.listgists.view.gistlist.GistsListFragment;
import com.example.listgists.view.gitdetail.GistDetailFragment;

public class GistListActivity extends AppCompatActivity
                        implements GistsListFragment.GistsListFragmentObserver {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            // if the activity is destroyed due to system memory claiming
            // and auto created by the system, then we should not
            // add the fragment since the OS will help us to restore the
            // fragment stack
            addFragment(GistsListFragment.newInstance());
        }

    }

    private void addFragment(AbstractFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.contentContainer, fragment, fragment.getFragmentTag());
        fragmentTransaction.addToBackStack(fragment.getClass().getName());  //getName() included package name
        if (fragmentManager.isStateSaved()) {
            fragmentTransaction.commitAllowingStateLoss();
        } else {
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        //if there is only one fragment when pressing back, then just finish the activity
        // a Better way is to listen the fragment lifecycle
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.contentContainer);
        if (fragment instanceof GistDetailFragment) {
            GistDetailData detailData = ((GistDetailFragment)fragment).getDetailData();
            //dosomething
            fragment = fragmentManager
                    .findFragmentByTag(GistsListFragment.TAG);
            if (fragment instanceof GistsListFragment) {
                ((GistsListFragment) fragment).updateData(detailData.getSimpleData());
            }
            super.onBackPressed();

        } else if (fragment instanceof GistsListFragment) {
            finish();
        }
    }

    @Override
    public void gistItemIsSelected(GistSimpleData data) {
        addFragment(GistDetailFragment.newInstance(data));
    }

}
