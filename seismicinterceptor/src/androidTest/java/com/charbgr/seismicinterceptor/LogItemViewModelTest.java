package com.charbgr.seismicinterceptor;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

import static com.charbgr.seismicinterceptor.OkHttpTestUtils.convertToRequestBody;
import static com.charbgr.seismicinterceptor.OkHttpTestUtils.convertToResponseBody;
import static com.charbgr.seismicinterceptor.OkHttpTestUtils.generateMockRequestWith;

public class LogItemViewModelTest extends InstrumentationTestCase {

    private Context context;
    private Request request;
    private Response response;
    private LogItemViewModel viewModel;


    @SmallTest
    public void testIfSuccessResponseIsCorrectlyCalculated() {
        context = getInstrumentation().getContext();
        request = generateMockRequestWith("GET", null);
        response = new Response.Builder()
                .code(200)
                .message("OK")
                .addHeader("Content-Type", "text/plain")
                .body(convertToResponseBody("MOCK"))
                .protocol(Protocol.HTTP_1_1)
                .request(request)
                .build();

        viewModel = new LogItemViewModel(context, request, response);
        assertEquals(ContextCompat.getColor(context, R.color.success_response), viewModel.getHttpStatusBgColor());
    }

    @SmallTest
    public void testIfFailResponseIsCorrectlyCalculated() {
        context = getInstrumentation().getContext();
        request = generateMockRequestWith("GET", null);
        response = new Response.Builder()
                .code(404)
                .message("OK")
                .addHeader("Content-Type", "text/plain")
                .body(convertToResponseBody("MOCK"))
                .protocol(Protocol.HTTP_1_1)
                .request(request)
                .build();

        viewModel = new LogItemViewModel(context, request, response);
        assertEquals(ContextCompat.getColor(context, R.color.failed_response), viewModel.getHttpStatusBgColor());
    }

    @SmallTest
    public void testIfElementsCorrectlyCalculated() {
        context = getInstrumentation().getContext();
        request = generateMockRequestWith("GET", null);
        response = new Response.Builder()
                .code(404)
                .message("OK")
                .addHeader("Content-Type", "text/plain")
                .body(convertToResponseBody("MOCK"))
                .protocol(Protocol.HTTP_1_1)
                .request(request)
                .build();

        viewModel = new LogItemViewModel(context, request, response);
        assertEquals("text/plain", viewModel.getContentType());
        assertEquals("GET", viewModel.getHttpVerb());
        assertEquals("http://localhost/", viewModel.getUrl());

        request = generateMockRequestWith("POST", convertToRequestBody("foo"));
        response = new Response.Builder()
                .code(404)
                .message("OK")
                .body(convertToResponseBody("MOCK"))
                .protocol(Protocol.HTTP_1_1)
                .request(request)
                .build();

        viewModel = new LogItemViewModel(context, request, response);
        assertEquals("Unknown", viewModel.getContentType());
        assertEquals("POST", viewModel.getHttpVerb());
    }


}