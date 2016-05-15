package com.charbgr.seismicinterceptor;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import okhttp3.Request;
import okhttp3.Response;

class LogItemViewModel {

    private final Context context;
    private final Request request;
    private final Response response;

    private String httpVerb;

    private int httpStatusBgColor;
    private int httpsBadgeTintColor;
    private String url;
    private String contentType;

    public LogItemViewModel(Context context, Request request, Response response) {
        this.context = context;
        this.request = request;
        this.response = response;

        setup();
    }

    private void setup() {
        setupRequestValues();
        setupResponseValues();
        setupUrl();
    }


    private void setupRequestValues() {
        this.httpVerb = request.method();
        this.httpsBadgeTintColor = ContextCompat.getColor(
                context, request.isHttps() ? R.color.gray_30 : R.color.gray_70
        );

    }


    private void setupResponseValues() {
        if (response == null)
            return;

        this.httpStatusBgColor = ContextCompat.getColor(
                context,
                response.isSuccessful() ? R.color.success_response : R.color.failed_response
        );

        this.contentType = response.header("Content-Type", "Unknown").split(";")[0];
    }


    private void setupUrl() {
        this.url = request.url().toString();
    }

    public String getHttpVerb() {
        return httpVerb;
    }

    public int getHttpStatusBgColor() {
        return httpStatusBgColor;
    }

    public String getUrl() {
        return url;
    }

    public int getHttpsBadgeTintColor() {
        return httpsBadgeTintColor;
    }

    public String getContentType() {
        return contentType;
    }
}
