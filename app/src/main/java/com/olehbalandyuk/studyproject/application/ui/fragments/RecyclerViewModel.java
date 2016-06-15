package com.olehbalandyuk.studyproject.application.ui.fragments;

class RecyclerViewModel {

    private String mId;
    private String mDate;
    private String mTitle;

    void setId(String id) {
        mId = id;
    }

    void setDate(String date) {
        mDate = date;
    }

    void setTitle(String title) {
        mTitle = title;
    }

    String getId() {
        return mId;
    }

    String getDate() {
        return mDate;
    }

    String getTitle() {
        return mTitle;
    }
}