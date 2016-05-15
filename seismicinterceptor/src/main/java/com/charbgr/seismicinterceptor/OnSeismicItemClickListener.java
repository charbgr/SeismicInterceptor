package com.charbgr.seismicinterceptor;

import okhttp3.Request;
import okhttp3.Response;

interface OnSeismicItemClickListener {
    void onSeismicItemClick(Request request, Response response, int position);
}
