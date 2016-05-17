package com.charbgr.seismicinterceptor;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.charbgr.seismicinterceptor.utils.OkHttpUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

public class LogDetailActivity extends AppCompatActivity {

    SeismicInterceptor seismicInterceptor = SeismicInterceptor.create();

    public static final String LOG_POSITION = "log-position";
    private int logPosition;

    private Request request;
    private Response response;

    TabLayout tabLayout;
    ViewPager viewPager;
    TabsAdapter tabsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_detail);

        this.logPosition = getIntent().getIntExtra(LOG_POSITION, -1);
        if (logPosition == -1) {
            finish();
        }

        Pair<Request, Response> pair = seismicInterceptor.getLogs().get(logPosition);
        this.request = pair.first;
        this.response = pair.second;

        bindViews();
        setUpTabs();

    }

    private void setUpTabs() {
        tabsAdapter = new TabsAdapter(getSupportFragmentManager());
        addTab(getString(R.string.log_info), generateBasicInfo(), null);
        addTab(getString(R.string.log_request), request.headers().toMultimap(), OkHttpUtils.requestToString(request));
        addTab(getString(R.string.log_response), response.headers().toMultimap(), OkHttpUtils.responseToString(response));

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(tabsAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void bindViews() {
        tabLayout = (TabLayout) findViewById(R.id.log_detail_tabs);
        viewPager = (ViewPager) findViewById(R.id.log_detail_viewpager);
    }

    private void addTab(String tabName, Map<String, List<String>> headers, String body) {

        if(body != null){
            headers.put("Body", Arrays.asList(body));
        }

        if (!headers.isEmpty()) {
            HeadersFragment headersFragment = HeadersFragment.newInstance(headers);
            tabsAdapter.addFragment(headersFragment, tabName);
        }
    }

    private Map<String, List<String>> generateBasicInfo() {
        Map<String, List<String>> info = new LinkedHashMap<>();
        info.put("URL", Arrays.asList(request.url().toString()));
        info.put("VERB", Arrays.asList(request.method()));
        info.put("Status", Arrays.asList(String.valueOf(response.code())));

        if(request.cacheControl() != null)
            info.put("Cache-Request", Arrays.asList(request.cacheControl().toString()));

        if(response.cacheControl() != null)
            info.put("Cache-Response", Arrays.asList(response.cacheControl().toString()));

        return info;
    }

    class TabsAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;
        private List<String> mTitles;

        public TabsAdapter(FragmentManager fm) {
            super(fm);
            mFragments = new ArrayList<>();
            mTitles = new ArrayList<>();
        }

        public void addFragment(Fragment fragment, String fragmentTitle) {
            mFragments.add(fragment);
            mTitles.add(fragmentTitle);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

    }
}
