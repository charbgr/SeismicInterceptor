package com.charbgr.seismicinterceptor;

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.support.v4.util.Pair;

import com.squareup.seismic.ShakeDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.charbgr.seismicinterceptor.utils.OkHttpUtils.cloneResponse;

public class SeismicInterceptor implements Interceptor, ShakeDetector.Listener {

    private static SeismicInterceptor instance;

    private SeismicInterceptor() {
        logs = new ArrayList<>();
        this.shakeDetector = new ShakeDetector(this);
    }

    public static SeismicInterceptor create() {
        if (instance == null) {
            instance = new SeismicInterceptor();
        }

        return instance;
    }

    private Context context;
    private ShakeDetector shakeDetector;
    private List<Pair<Request, Response>> logs;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            System.out.println("<-- HTTP FAILED: " + e);
            throw e;
        }

        logs.add(new Pair<>(request, cloneResponse(response)));
        return response;
    }

    public List<Pair<Request, Response>> getLogs() {
        return logs;
    }

    public static void start(Context context) {
        start(context, ShakeDetector.SENSITIVITY_MEDIUM);
    }

    public static void start(Context context, int sensitivity) {
        if (instance == null) {
            create();
        }

        instance.context = context;

        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        instance.shakeDetector.setSensitivity(sensitivity);
        instance.shakeDetector.start(sensorManager);
    }

    public static void stop() {
        if (instance == null) {
            return;
        }

        instance.shakeDetector.stop();
        instance.context = null;
    }

    @Override
    public void hearShake() {
        Intent myIntent = new Intent(context, LogListActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(myIntent);
    }

}
