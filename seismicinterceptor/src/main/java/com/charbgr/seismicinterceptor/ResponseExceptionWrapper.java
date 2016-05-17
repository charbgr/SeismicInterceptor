package com.charbgr.seismicinterceptor;

import okhttp3.Response;

/**
 * Wraps OkHttp's response and any exception will happen
 */
public class ResponseExceptionWrapper {

    private Response response;
    private Exception exception;

    public ResponseExceptionWrapper(Response response) {
        this.response = response;
    }

    public ResponseExceptionWrapper(Exception exception) {
        this.exception = exception;
    }

    public Response getResponse() {
        return response;
    }

    public Exception getException() {
        return exception;
    }

    public boolean isResponse() {
        return response != null;
    }

    public boolean isException() {
        return exception != null;
    }
}
