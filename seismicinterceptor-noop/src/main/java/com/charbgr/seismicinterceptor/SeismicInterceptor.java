package com.charbgr.seismicinterceptor;

import android.content.Context;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class SeismicInterceptor implements Interceptor {

    private static SeismicInterceptor instance;

    private SeismicInterceptor() {
    }

    public static SeismicInterceptor create() {
        if (instance == null) {
            instance = new SeismicInterceptor();
        }

        return instance;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        return chain.proceed(request);
    }

    public static void start(Context context) {
    }

    public static void start(Context context, int sensitivity) {

    }

    public static void stop() {
    }
}
