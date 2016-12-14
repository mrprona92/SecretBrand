package com.badr.infodota.stream.service;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.stream.api.Stream;
import com.badr.infodota.stream.api.douyu.DouyuResult;
import com.badr.infodota.stream.api.douyu.Room;

/**
 * Created by ABadretdinov
 * 26.05.2015
 * 14:48
 */
public class DouyuServiceImpl implements DouyuService {
    @Override
    public Stream getStream(Stream stream) {
        DouyuResult result = BeanContainer.getInstance().getDouyuRestService().getRoomResult(stream.getEmbedId());
        Room room = result.getRoom();
        stream.setViewers(room.getOnline());
        stream.setChannel(room.getId());
        stream.setProvider("douyu");
        stream.setTitle(room.getName());
        stream.setImage(room.getImage());
        return stream;
    }

    @Override
    public void initialize() {
    }
}
