package com.charbgr.seismicinterceptor;

import com.charbgr.seismicinterceptor.utils.OkHttpUtils;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class OkHttpUtilsTest {

    @Test
    public void testStringifyWithContentHeaders() {
        Map<String, List<String>> map = new LinkedHashMap<>();
        map.put("foo", Arrays.asList("bar", "qux"));

        assertEquals("[foo]\nbar, qux\n\n", OkHttpUtils.stringifyHeaders(map));
    }

    @Test
    public void testStringifyWithoutContentHeaders() {
        Map<String, List<String>> map = new LinkedHashMap<>();
        map.put("foo", null);

        assertEquals("", OkHttpUtils.stringifyHeaders(map));
    }
}