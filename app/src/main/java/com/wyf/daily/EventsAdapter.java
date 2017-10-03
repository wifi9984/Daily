package com.wyf.daily;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by admin on 2017/8/26.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ItemHolder>
        implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Event> AllEvents;

    public EventsAdapter(Context context, ArrayList<Event> publicArray) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        AllEvents = publicArray;
    }

    public void onDataUpdate(ArrayList<Event> publicArray) {
        AllEvents = publicArray;
        notifyDataSetChanged();
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.single_event_list,parent,false);
        ItemHolder holder = new ItemHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        ItemHolder itemHolder = holder;
        itemHolder.tv_time.setText(AllEvents.get(position).getTime_s());
        itemHolder.tv_event.setText(AllEvents.get(position).getEvent());
        itemHolder.tv_inform.setText(AllEvents.get(position).getPattern());
        itemHolder.card_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        itemHolder.card_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return false;
    }

    public static class ItemHolder extends RecyclerView.ViewHolder{
        public TextView tv_time;
        public TextView tv_event;
        public TextView tv_inform;
        public CardView card_item;

        public ItemHolder(View v){
            super(v);
            card_item = v.findViewById(R.id.card_item);
            tv_time = v.findViewById(R.id.tv_home_read_start_time);
            tv_event = v.findViewById(R.id.tv_home_read_event);
            tv_inform = v.findViewById(R.id.tv_home_read_inform_time);
        }
    }
}
