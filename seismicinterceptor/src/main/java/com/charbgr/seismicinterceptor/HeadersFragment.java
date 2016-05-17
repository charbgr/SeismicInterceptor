package com.charbgr.seismicinterceptor;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charbgr.seismicinterceptor.utils.IntentUtils;

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

    private TextView headerBody;
    private FloatingActionButton shareActionBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_headers, container, false);
        bindViews(rootView);

        setUpBody();
        setUpShareActionBtn();

        return rootView;
    }


    private void bindViews(View rootView) {
        this.headerBody = ((TextView) rootView.findViewById(R.id.headers_detail));
        this.shareActionBtn = ((FloatingActionButton) rootView.findViewById(R.id.headers_share_content));
    }

    private void setUpBody() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            appendKey(sb, entry.getKey());
            appendValue(sb, entry.getValue());
        }

        this.headerBody.setText(sb.toString());
    }

    private void setUpShareActionBtn() {
        shareActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = IntentUtils
                        .createShareIntent(getString(R.string.app_name),
                                headerBody.getText().toString());

                getContext().startActivity(shareIntent);
            }
        });
    }


    private void appendKey(StringBuilder sb, String key) {
        if (key != null) {
            sb.append('[').append(key).append("]\n");
        }
    }

    private void appendValue(StringBuilder sb, List<String> values) {
        if (values != null) {
            sb.append(TextUtils.join(", ", values)).append("\n\n");
        }
    }

}
