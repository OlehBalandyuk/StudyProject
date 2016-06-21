package com.olehbalandyuk.studyproject.application.ui.fragments.cabinet;

class CabinetRecyclerViewModel {

    private String mId;
    private String mDate;
    private String mTitle;

    void setId(String id) {
        mId = id;
    }

    void setDateEnd(String date) {
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