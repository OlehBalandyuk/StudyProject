package com.olehbalandyuk.studyproject.application.ui.fragments.current_packet;

class CurrentPacketRecyclerViewModel {

    private String mTitle;
    private String mNumber;
    private String mGenre;
    private String mInfo;
    private boolean mIsAvailable;
    private boolean mIsFavorite;
    private String mLogoUrl;

    void setTitle(String title) {
        mTitle = title;
    }

    void setNumber(String number) {
        mNumber = number;
    }

    void setGenre(String genre) {
        mGenre = genre;
    }

    void setInfo(String info) {
        mInfo = info;
    }

    void setIsAvailable(String isAvailable) {
        mIsAvailable = Boolean.parseBoolean(isAvailable);
    }

    void setIsFavorite(String isFavorite) {
        mIsFavorite = Boolean.parseBoolean(isFavorite);
    }

    void setLogoUrl(String url) {
        mLogoUrl = url;
    }

    String getTitle() {
        return mTitle;
    }

    String getNumber() {
        return mNumber;
    }

    String getGenre() {
        return mGenre;
    }

    String getInfo() {
        return mInfo;
    }

    boolean isAvailable() {
        return mIsAvailable;
    }

    boolean isFavorite() {
        return mIsFavorite;
    }

    String getLogoUrl() {
        return mLogoUrl;
    }

}