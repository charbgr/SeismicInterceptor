package com.charbgr.seismicinterceptor.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import org.json.JSONArray;
import org.json.JSONException;

import static java.net.HttpURLConnection.HTTP_NOT_MODIFIED;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static okhttp3.internal.http.StatusLine.HTTP_CONTINUE;

public class OkHttpUtils {

    private static final int DEFAULT_INDENT_SPACES = 4;
    private static final Charset UTF8 = Charset.forName("UTF-8");


    public static String stringifyHeaders(Map<String, List<String>> headers) {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                appendKey(sb, entry.getKey());
                appendValue(sb, entry.getValue());
            }
        }

        return sb.toString();
    }

    private static void appendKey(StringBuilder sb, String key) {
        if (key != null) {
            sb.append('[').append(key).append("]\n");
        }
    }

    private static void appendValue(StringBuilder sb, List<String> values) {
        if (values != null) {
            sb.append(TextUtils.join(", ", values)).append("\n\n");
        }
    }

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

        if (hasBody(response)) {
            BufferedSource source = responseBody.source();
            try {
                source.request(Long.MAX_VALUE); // Buffer the entire body.
            } catch (IOException e) {
                return null;
            }
            Buffer buffer = source.getBuffer();

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

    /**
     * Returns true if the response must have a (possibly 0-length) body. See RFC 2616 section 4.3.
     */
    public static boolean hasBody(Response response) {
        // HEAD requests never yield a body regardless of the response headers.
        if (response.request().method().equals("HEAD")) {
            return false;
        }

        int responseCode = response.code();
        if ((responseCode < HTTP_CONTINUE || responseCode >= 200)
            && responseCode != HTTP_NO_CONTENT
            && responseCode != HTTP_NOT_MODIFIED) {
            return true;
        }

        // If the Content-Length or Transfer-Encoding headers disagree with the
        // response code, the response is malformed. For best compatibility, we
        // honor the headers.
        long contentLength = stringToLong(response.header("Content-Length"));
        if (contentLength != -1 || "chunked".equalsIgnoreCase(response.header("Transfer-Encoding"))) {
            return true;
        }

        return false;
    }

    private static long stringToLong(String s) {
        if (s == null) return -1;
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
