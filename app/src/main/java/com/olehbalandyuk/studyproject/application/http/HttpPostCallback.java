package com.olehbalandyuk.studyproject.application.http;

interface HttpPostCallback<T> {

    void onResponse(T response);

    void onError(Errors error);
}
