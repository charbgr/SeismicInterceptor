package com.charbgr.seismicinterceptor.utils;

import android.content.Intent;

import static android.content.Intent.ACTION_SEND;
import static android.content.Intent.EXTRA_SUBJECT;
import static android.content.Intent.EXTRA_TEXT;

public class IntentUtils {

    private IntentUtils() {
        //no instances
    }

    public static Intent createShareIntent(String subject, String shareBody) {
        Intent sharingIntent = new Intent(ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(EXTRA_TEXT, shareBody);
        return sharingIntent;
    }
}
