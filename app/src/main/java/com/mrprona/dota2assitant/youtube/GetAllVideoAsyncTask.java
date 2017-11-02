package com.mrprona.dota2assitant.youtube;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public abstract class GetAllVideoAsyncTask extends AsyncTask<String, Void, Pair<String, List<Video>>> {
    private static final String TAG = "GetPlaylistAsyncTask";
    private static final Long YOUTUBE_PLAYLIST_MAX_RESULTS = 10L;

    private static final String YOUTUBE_PLAYLIST_PART = "snippet";

    private static final String YOUTUBE_VIDEOS_PART = "snippet,contentDetails,statistics"; // video resource properties that the response will include.
    private static final String YOUTUBE_VIDEOS_FIELDS = "items(id,snippet(title,description,thumbnails/high),contentDetails/duration,statistics)"; // selector specifying which fields to include in a partial response.

    private YouTube mYouTubeDataApi;
    private String mChannelId;

    public GetAllVideoAsyncTask(YouTube api, String channelID) {
        mYouTubeDataApi = api;
        mChannelId=channelID;
    }

    @Override
    protected Pair<String, List<Video>> doInBackground(String... params) {
        final String nextPageToken;

        if (params.length == 1) {
            nextPageToken = params[0];
        } else {
            nextPageToken = null;
        }

        // get details of the videos on this playlist page
        SearchListResponse searchListResponse = null;
        try {
            searchListResponse= mYouTubeDataApi.search()
                    .list(YOUTUBE_PLAYLIST_PART)
                    .setOrder("date")
                    .setPageToken(nextPageToken)
                    .setKey(ApiKey.YOUTUBE_API_KEY)
                    .setChannelId(mChannelId).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (searchListResponse == null) {
            Log.e(TAG, "Failed to get playlist");
            return null;
        }

        List<String> videoIds = new ArrayList();

        // pull out the video id's from the playlist page
        for (SearchResult item : searchListResponse.getItems()) {
            videoIds.add(item.getId().getVideoId());
        }

        // get details of the videos on this playlist page
        VideoListResponse videoListResponse = null;
        try {
            videoListResponse = mYouTubeDataApi.videos()
                    .list(YOUTUBE_VIDEOS_PART)
                    .setFields(YOUTUBE_VIDEOS_FIELDS)
                    .setKey(ApiKey.YOUTUBE_API_KEY)
                    .setId(TextUtils.join(",", videoIds)).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Pair(searchListResponse.getNextPageToken(), videoListResponse.getItems());
    }
}
