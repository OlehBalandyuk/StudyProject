package com.olehbalandyuk.studyproject.application;

/**
 * This is the container of all links which are needed for requests.
 * Also links for redirects are also included.
 */
public class API {

    // Links which are needed to redirect user to the website
    static final String REGISTER = "http://starcards.tv/register";
    static final String FORGOT_PASSWORD = "http://starcards.tv/";

    // Links which are needed to run requests
    static final String API = "https://api.starcards.tv/user";
    static final String LOGIN = API + "/login";
    static final String USER_INFO = API + "/info";
    static final String PACKETS_SUMMARY = API + "/packets";
    static final String LOG_IN_PACKET = API + "/token";
    static final String LINK = API + "/link";
    static final String TV_CHANNELS = API + "/tv-channels";

    // Features, will be implemented if we won't be running out of time
    static final String MESSAGE_HISTORY = API + "/messages/history";
    static final String MESSAGE = API + "/messages/";
}
