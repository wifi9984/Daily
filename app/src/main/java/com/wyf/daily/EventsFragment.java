package com.wyf.daily;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by admin on 2017/8/7.
 */

public class EventsFragment extends android.app.Fragment implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{

    private FloatingActionButton fab_add;
    private SwipeRefreshLayout srl_main;
    private EventsDBHelper mHelper;
    protected View mView;
    protected Context mContext;
    private RecyclerView mRecyclerView;
    private EventsAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mHelper = EventsDBHelper.getInstance(mContext,1);
        mView = inflater.inflate(R.layout.events_fragment,container,false);
        fab_add = mView.findViewById(R.id.fab_add);
        srl_main = mView.findViewById(R.id.srl_main);
        fab_add.setOnClickListener(this);
        srl_main.setOnRefreshListener(this);
        srl_main.setColorSchemeResources(R.color.indigo400,R.color.colorPrimaryDark);
        mRecyclerView = mView.findViewById(R.id.rv_events);
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        manager.setOrientation(LinearLayout.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        ArrayList<Event> AllEvents = mHelper.AllEvents(mHelper.getReadableDatabase());
        mAdapter = new EventsAdapter(this.getActivity(),AllEvents);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(),DividerItemDecoration.HORIZONTAL));
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

    public void onClick(View v){
        if(v.getId() == R.id.fab_add){
            Intent intent = new Intent(getActivity(),NewEventActivity.class);
            startActivity(intent);
            onStop();
        }

    }

    private Handler mHandler = new Handler();
    public void onRefresh(){
        mHandler.postDelayed(mRefresh,1500);
        mAdapter.onDataUpdate(mHelper.AllEvents(mHelper.getReadableDatabase()));
    }
    private Runnable mRefresh = new Runnable() {
        @Override
        public void run() {
            srl_main.setRefreshing(false);
        }
    };

}
