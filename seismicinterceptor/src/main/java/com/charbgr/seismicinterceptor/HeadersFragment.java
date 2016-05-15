package com.charbgr.seismicinterceptor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;


public class HeadersFragment extends Fragment {


    public HeadersFragment() {
        // Required empty public constructor
    }

    public static HeadersFragment newInstance(Map<String, List<String>> headers) {
        HeadersFragment fragment = new HeadersFragment();
        fragment.headers = headers;

        return fragment;
    }

    private Map<String, List<String>> headers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_headers, container, false);

        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            appendKey(sb, entry.getKey());
            appendValue(sb, entry.getValue());
        }

        ((TextView)rootView.findViewById(R.id.headers_detail)).setText(sb.toString());
        return rootView;
    }

    private void appendKey(StringBuilder sb, String key) {
        if(key != null) {
            sb.append('[').append(key).append("]\n");
        }
    }

    private void appendValue(StringBuilder sb, List<String> values) {
        if(values != null){
            sb.append(TextUtils.join(", ", values)).append("\n\n");
        }
    }

}
