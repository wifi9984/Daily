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
import android.transition.ArcMotion;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

/**
 *  EventsFragment来呈现“所有事项”界面
 *
 *  @author wifi9984
 *  @date 2017/12/22
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

    void init(){
        fab_add = mView.findViewById(R.id.fab_add_event);
        srl_main = mView.findViewById(R.id.srl_main);
        fab_add.setOnClickListener(this);
        srl_main.setOnRefreshListener(this);
        srl_main.setColorSchemeResources(R.color.indigo400,R.color.colorPrimaryDark);
        // RecyclerView初始化
        mRecyclerView = mView.findViewById(R.id.rv_events);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false){
            // 重写LinearLayoutManager，修复RecyclerView嵌套在ScrollView中导致的滚动冲突，解决卡顿
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<Event> AllEvents = mHelper.allEvents(mHelper.getReadableDatabase());
        mAdapter = new EventsAdapter(this.getActivity(),AllEvents);
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
    public void onClick(View v){
        if(v.getId() == R.id.fab_add_event){
            Intent intent = new Intent(getActivity(),NewEventActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation
                    (this.getActivity(), fab_add, "transition_morph_view_add");
            startActivity(intent, options.toBundle());
            onStop();
        }
    }

    private Handler mHandler = new Handler();
    @Override
    public void onRefresh(){
        mHandler.postDelayed(mRefresh,750);
        mAdapter.onDataUpdate(mHelper.allEvents(mHelper.getReadableDatabase()));
    }
    private Runnable mRefresh = new Runnable() {
        @Override
        public void run() {
            srl_main.setRefreshing(false);
        }
    };
}
