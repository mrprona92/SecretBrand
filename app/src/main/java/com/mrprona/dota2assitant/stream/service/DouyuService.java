package com.mrprona.dota2assitant.stream.service;

import com.mrprona.dota2assitant.InitializingBean;
import com.mrprona.dota2assitant.stream.api.Stream;

/**
 * Created by ABadretdinov
 * 26.05.2015
 * 14:47
 */
public interface DouyuService extends InitializingBean {
    Stream getStream(Stream stream);
}
