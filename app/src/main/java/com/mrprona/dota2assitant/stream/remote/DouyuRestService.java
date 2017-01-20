package com.mrprona.dota2assitant.stream.remote;

import com.mrprona.dota2assitant.stream.api.douyu.DouyuResult;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by ABadretdinov
 * 26.05.2015
 * 14:54
 */
public interface DouyuRestService {
    @GET("/room/{roomId}?client_sys=android")
    DouyuResult getRoomResult(@Path("roomId") String roomId);
}
