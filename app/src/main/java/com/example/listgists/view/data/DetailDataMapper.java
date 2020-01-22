package com.example.listgists.view.data;

import androidx.annotation.NonNull;

import com.example.listgists.R;

import java.util.ArrayList;
import java.util.List;

/**
 * this class contains the data which can be consumed by the UI.
 */
public class DetailDataMapper {

    private static int[] elementsToDisplay =
            new int[]{R.string.detail_id,
                      R.string.detail_favourite,
                      R.string.detail_url,
                      R.string.detail_file_name,
                      R.string.detail_shared_count,
                      R.string.detail_forks_url,
                      R.string.detail_commits_url,
                      R.string.detail_comments,
                      R.string.detail_description};

    public List<DetailDisplayData> getDataToDisplay(@NonNull GistDetailData data) {
        List<DetailDisplayData> displayData = new ArrayList<>();
        if (data != null) {
            displayData.add(new DetailDisplayData(R.string.detail_id, data.getId()));
            displayData.add(new DetailDisplayData(R.string.favourite,
                                                  String.valueOf(data.getFavourite())));

            displayData.add(new DetailDisplayData(R.string.detail_url, data.getUrl()));

            displayData.add(new DetailDisplayData(R.string.detail_file_name, data.getFileName()));
            if (data.getSharedCount() >=5) {
                displayData.add(new DetailDisplayData(R.string.detail_shared_count,
                        String.valueOf(data.getSharedCount())));
            }

            displayData.add(new DetailDisplayData(R.string.detail_forks_url, data.getForksUrl()));
            displayData.add(new DetailDisplayData(R.string.detail_commits_url, data.getCommitsUrl()));
            displayData.add(new DetailDisplayData(R.string.detail_comments, data.getComments()));
            displayData.add(new DetailDisplayData(R.string.detail_description, data.getDescription()));
        }
        return displayData;

    }
}
