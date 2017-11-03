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
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * EventsFragment来呈现“所有事项”界面
 */

public class EventsFragment extends android.app.Fragment implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{

    private FloatingActionButton fab_add;
    private SwipeRefreshLayout srl_main;
    private EventsDBHelper mHelper;
    protected View mView;
    protected View popView;
    protected Context mContext;
    private RecyclerView mRecyclerView;
    private EventsAdapter mAdapter;
    private PopupWindow popDeleteItem;
    private Button btn_delete_item;
    public int x,y;

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
        WindowManager wm = this.getActivity().getWindowManager();
        popDeleteItem = new PopupWindow(this.getActivity());
        popDeleteItem.setWidth(wm.getDefaultDisplay().getWidth()/8);
        popDeleteItem.setHeight(wm.getDefaultDisplay().getHeight()/20);
        popDeleteItem.setBackgroundDrawable(null);
        popView = inflater.inflate(R.layout.pop_delete_item,null);
        popDeleteItem.setContentView(popView);
        popDeleteItem.setOutsideTouchable(false);
        popDeleteItem.setFocusable(true);
        btn_delete_item = popView.findViewById(R.id.btn_delete_pop);
        btn_delete_item.setOnClickListener(this);
        mRecyclerView = mView.findViewById(R.id.rv_events);
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        manager.setOrientation(LinearLayout.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        ArrayList<Event> AllEvents = mHelper.AllEvents(mHelper.getReadableDatabase());
        mAdapter = new EventsAdapter(this.getActivity(),AllEvents);
        mAdapter.setOnItemClickListener(new EventsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
            }
        });
        mAdapter.setOnItemLongClickListener(new EventsAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int position) {
                int xoff = x - popDeleteItem.getWidth() / 2;
                int yoff = 0 - (v.getHeight() - y) - popDeleteItem.getHeight();
                popDeleteItem.showAsDropDown(v, xoff, yoff);
            }
        });
        mAdapter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                x = (int) motionEvent.getX();
                y = (int) motionEvent.getY();
                return false;
            }
        });
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
        }else if(v.getId() == R.id.btn_delete_pop){
            //执行SQLite的delete方法
            //getPosition存储选中的item的position
            popDeleteItem.dismiss();
        }

    }

    private Handler mHandler = new Handler();
    public void onRefresh(){
        mHandler.postDelayed(mRefresh,750);
        mAdapter.onDataUpdate(mHelper.AllEvents(mHelper.getReadableDatabase()));
    }
    private Runnable mRefresh = new Runnable() {
        @Override
        public void run() {
            srl_main.setRefreshing(false);
        }
    };
}
