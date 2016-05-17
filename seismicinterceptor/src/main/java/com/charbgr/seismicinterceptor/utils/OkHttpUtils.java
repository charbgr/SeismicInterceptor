package com.charbgr.seismicinterceptor.utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpEngine;
import okio.Buffer;
import okio.BufferedSource;

public class OkHttpUtils {

    private static final int DEFAULT_INDENT_SPACES = 4;
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public static Response cloneResponse(Response response) {
        try {
            return response.newBuilder().body(response.peekBody(Long.MAX_VALUE)).build();
        } catch (IOException ignore) {
            return null;
        }
    }

    public static String requestToString(final Request request) {

        if (request.body() == null)
            return null;

        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "No request data";
        }
    }

    public static String responseToString(final Response response) {
        ResponseBody responseBody = response.body();

        if (HttpEngine.hasBody(response)) {
            BufferedSource source = responseBody.source();
            try {
                source.request(Long.MAX_VALUE); // Buffer the entire body.
            } catch (IOException e) {
                return null;
            }
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    return null;
                }
            }

            if (responseBody.contentLength() != 0) {
                String jsonString = buffer.clone().readString(charset);
                try {
                    JSONArray json = new JSONArray(jsonString);
                    return json.toString(DEFAULT_INDENT_SPACES).replaceAll("\\\\/", "/");
                } catch (JSONException e) {
                    return null;
                }

            }
            return null;
        }

        return null;
    }
}
