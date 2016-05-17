package com.charbgr.seismicinterceptor;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpHelperUtils {
    public static RequestBody convertToRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain; foo"), value);
    }

    public static ResponseBody convertToResponseBody(String value) {
        return ResponseBody.create(MediaType.parse("text/plain; foo"), value);
    }


    public static Response generateMockResponseWith(Request request, int networkCode) {
        Response response = new Response.Builder()
                .code(networkCode)
                .message("OK")
                .body(convertToResponseBody("MOCK"))
                .protocol(Protocol.HTTP_1_1)
                .request(request)
                .build();

        return response;
    }

    public static Request generateMockRequestWith(String methodName, RequestBody requestBody) {
        Request request = new Request.Builder()
                .method(methodName, requestBody)
                .url(HttpUrl.parse("http://localhost/"))
                .build();
        return request;
    }
}
