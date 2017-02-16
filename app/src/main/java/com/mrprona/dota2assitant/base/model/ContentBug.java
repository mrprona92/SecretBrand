package com.mrprona.dota2assitant.base.model;

/**
 * Created by BinhTran on 2/6/17.
 */

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties

public class ContentBug {

    private String contentBug;

    public String getContentBug() {
        return contentBug;
    }
    public void setContentBug(String contentBug) {
        this.contentBug = contentBug;
    }
    public ContentBug(String contentBug) {
        this.contentBug = contentBug;
    }

    public ContentBug() {

    }
}
