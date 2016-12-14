package com.parser;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A playlist element.
 *
 * @author dkuffner
 */
public interface Element {

    String getTitle();


    int getDuration();

    /**
     * URI to media or playlist.
     *
     * @return the URI.
     */
    URI getURI();

    /**
     * Media can be encrypted.
     *
     * @return true if media encrypted.
     */
    boolean isEncrypted();

    /**
     * Element can be another playlist.
     *
     * @return true if element a playlist.
     */
    boolean isPlayList();

    /**
     * Element is a media file.
     *
     * @return true if element a media file and not a playlist.
     */
    boolean isMedia();

    /**
     * If media is encryped than will this method return a info object.
     *
     * @return the info object or null if media not encrypted.
     */
    EncryptionInfo getEncryptionInfo();

    /**
     * If element a playlist than this method will return a PlaylistInfo object.
     *
     * @return a info object or null in case of element is not a playlist.
     */
    PlaylistInfo getPlayListInfo();

    /**
     * The program date.
     *
     * @return -1 in case of program date is not set.
     */
    long getProgramDate();

    String getQuality();

    class List extends ArrayList<Element> {
        public List(Collection<? extends Element> collection) {
            super(collection);
        }
    }
}
