package com.wyf.daily;

import com.wyf.daily.EventsDBHelper;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by admin on 2017/8/7.
 */

public class EventsFragment extends android.app.Fragment implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{

    private FloatingActionButton fab_add;
    private SwipeRefreshLayout srl_main;
    private TextView tv_title;
    private TextView tv_event;
    private TextView tv_inform;
    private EventsDBHelper mHelper;
    protected View mView;
    protected Context mContext;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.events_fragment,container,false);
        fab_add = (FloatingActionButton)mView.findViewById(R.id.fab_add);
        srl_main = (SwipeRefreshLayout)mView.findViewById(R.id.srl_main);
        fab_add.setOnClickListener(this);
        srl_main.setOnRefreshListener(this);
        srl_main.setColorSchemeResources(R.color.indigo400,R.color.colorPrimaryDark);
        tv_event = (TextView)mView.findViewById(R.id.tv_home_read_event);
        tv_title = (TextView)mView.findViewById(R.id.tv_home_read_start_time);
        tv_inform = (TextView)mView.findViewById(R.id.tv_home_read_inform_time);
        mRecyclerView = (RecyclerView)mView.findViewById(R.id.rv_events);
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        manager.setOrientation(LinearLayout.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mHelper = EventsDBHelper.getInstance(this.getActivity(),1);
        mHelper.openWriteLink();
        ArrayList<Event> AllEvents = mHelper.AllEvents(this.getActivity());
        EventsAdapter adapter = new EventsAdapter(this.getActivity(),AllEvents);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(null);
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
        mHelper.closeLink();
    }

    public void onClick(View v){
        if(v.getId() == R.id.fab_add){
            Intent intent = new Intent(getContext(),NewEventActivity.class);
            startActivity(intent);
            onStop();
        }

    }

    private Handler mHandler = new Handler();
    public void onRefresh(){
        mHandler.postDelayed(mRefresh,1500);
    }
    private Runnable mRefresh = new Runnable() {
        @Override
        public void run() {
            srl_main.setRefreshing(false);
        }
    };

}
