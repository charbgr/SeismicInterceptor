package com.charbgr.seismicinterceptor.listeners;

import com.charbgr.seismicinterceptor.ResponseExceptionWrapper;

import okhttp3.Request;
import okhttp3.Response;

public interface OnSeismicItemClickListener {
    void onSeismicItemClick(Request request, ResponseExceptionWrapper responseExceptionWrapper, int position);
}
