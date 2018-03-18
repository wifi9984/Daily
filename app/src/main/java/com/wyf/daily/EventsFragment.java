package com.wyf.daily;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  EventsFragment来呈现“所有事项”界面
 *
 *  @author wifi9984
 *  @date 2018/3/18
 */

public class EventsFragment extends android.app.Fragment
        implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.events_fab_add) FloatingActionButton fabAddItem;
    @BindView(R2.id.events_srl) SwipeRefreshLayout mSwipeRefresh;
    @BindView(R2.id.events_rv) RecyclerView mRecyclerView;
    private EventsDBHelper mHelper;
    protected View mView;
    protected Context mContext;
    private EventsAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mHelper = EventsDBHelper.getInstance(mContext,1);
        mView = inflater.inflate(R.layout.events_fragment,container,false);
        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mHelper.closeLink();
    }

    void init() {
        fabAddItem.setOnClickListener(this);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setColorSchemeResources(R.color.indigo400,R.color.colorPrimaryDark);
        // RecyclerView初始化
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false){
            // 重写LinearLayoutManager，修复RecyclerView嵌套在ScrollView中导致的滚动冲突，解决卡顿
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<Event> allEvents = mHelper.allEvents(mHelper.getReadableDatabase());
        mAdapter = new EventsAdapter(this.getActivity(),allEvents);
        mAdapter.setOnItemClickListener(new EventsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
            }
        });
        mAdapter.setOnItemLongClickListener(new EventsAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int position) {
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(),DividerItemDecoration.HORIZONTAL));
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.events_fab_add){
            Intent intent = new Intent(getActivity(),NewEventActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation
                    (this.getActivity(), fabAddItem, "transition_morph_view_add");
            startActivity(intent, options.toBundle());
            onStop();
        }
    }

    private Handler mHandler = new Handler();
    @Override
    public void onRefresh() {
        mHandler.postDelayed(mRefresh,750);
        mAdapter.onDataUpdate(mHelper.allEvents(mHelper.getReadableDatabase()));
    }
    private Runnable mRefresh = new Runnable() {
        @Override
        public void run() {
            mSwipeRefresh.setRefreshing(false);
        }
    };
}
