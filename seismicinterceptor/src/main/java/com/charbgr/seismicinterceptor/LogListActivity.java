package com.charbgr.seismicinterceptor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public class LogListActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener, OnSeismicItemClickListener {

    SeismicInterceptor interceptor = SeismicInterceptor.create();

    RecyclerView recyclerView;
    LogAdapter adapter;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpRecyclerView();
        setUpAdapter();
        setUpSwipeToRefresh();

        adapter.setOnSeismicItemClickListener(this);
    }

    private void setUpAdapter() {
        adapter = new LogAdapter(interceptor.getLogs());
        recyclerView.setAdapter(adapter);
    }

    private void setUpRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.seismic_log_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void setUpSwipeToRefresh() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.seismic_log_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        setUpAdapter();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onSeismicItemClick(Request request, Response response, int position) {
        Intent intent = new Intent(this, LogDetailActivity.class);
        intent.putExtra(LogDetailActivity.LOG_POSITION, position);
        startActivity(intent);
    }
}
