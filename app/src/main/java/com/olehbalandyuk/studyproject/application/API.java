package com.olehbalandyuk.studyproject.application;

/**
 * This is the container of all links which are needed for requests.
 * Also links for redirects are also included.
 */
public class API {

    // Links which are needed to redirect user to the website
    public static final String REGISTER_URL = "http://starcards.tv/register";
    public static final String FORGOT_PASSWORD_URL = "http://starcards.tv/";

    // Links which are needed to run requests
    public static final String API = "https://api.starcards.tv/user";
    public static final String LOGIN = API + "/login";
    public static final String USER_INFO = API + "/info";
    public static final String PACKETS_SUMMARY = API + "/packets";
    public static final String LOG_IN_PACKET = API + "/token";
    public static final String LINK = API + "/link";
    public static final String TV_CHANNELS = API + "/tv-channels";

    // Features, will be implemented if we won't be running out of time
    public static final String MESSAGE_HISTORY = API + "/messages/history";
    public static final String MESSAGE = API + "/messages/";
}
