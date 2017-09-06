package com.wyf.daily;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by admin on 2017/8/26.
 */

public class EventsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener,View.OnLongClickListener{

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Event> AllEvents;

    public EventsAdapter(Context context, ArrayList<Event> publicArray){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        AllEvents = publicArray;
    }

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(v);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(mOnItemClickListener != null){
            mOnItemClickListener.onItemLongClick(v);
        }
        return false;
    }

    public static interface OnRecyclerItemClickListener{
        void onItemClick(View view);
        void onItemLongClick(View view);
    }

    private OnRecyclerItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerItemClickListener listener){
        mOnItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        RecyclerView.ViewHolder holder = null;
        v = mInflater.inflate(R.layout.single_event_list,parent,false);
        holder = new ItemHolder(v);
        v.setOnClickListener(this);
        v.setOnLongClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, final int position) {
        ItemHolder holder = (ItemHolder)vh;
        holder.tv_time.setText(AllEvents.get(position).getTime_s());
        holder.tv_event.setText(AllEvents.get(position).getEvent());
        holder.tv_inform.setText(AllEvents.get(position).getPattern());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public TextView tv_time;
        public TextView tv_event;
        public TextView tv_inform;
        public CardView card_item;

        public ItemHolder(View v){
            super(v);
            card_item = (CardView)v.findViewById(R.id.card_item);
            tv_time = (TextView)v.findViewById(R.id.tv_home_read_start_time);
            tv_event = (TextView)v.findViewById(R.id.tv_home_read_event);
            tv_inform = (TextView)v.findViewById(R.id.tv_home_read_inform_time);
        }
    }
}
