package com.charbgr.seismicinterceptor.listeners;

import okhttp3.Request;
import okhttp3.Response;

public interface OnSeismicItemClickListener {
    void onSeismicItemClick(Request request, Response response, int position);
}
