package com.badr.infodota.stream.service;

import com.badr.infodota.InitializingBean;
import com.badr.infodota.stream.api.Stream;

/**
 * Created by ABadretdinov
 * 26.05.2015
 * 14:47
 */
public interface DouyuService extends InitializingBean {
    Stream getStream(Stream stream);
}
