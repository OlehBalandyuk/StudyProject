package com.olehbalandyuk.studyproject.application.http;


public interface HttpRequestCallback {
    void onResponse(HttpRequestResult response);

    void onError(HttpRequestResult error);
}
