package com.wyf.daily;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *  Adapter用于呈现数据库存储的事件详情
 *  从数据库拉取数据，写到每一个Event对象里面，再通过对象读取数据加载到RecyclerView中
 *  这一块我堵了好久，RecyclerView作为一个高级控件，需要多看文档= =
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ItemHolder>
        implements View.OnClickListener,View.OnLongClickListener,View.OnTouchListener{

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
        v.setOnClickListener(this);
        v.setOnLongClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        ItemHolder itemHolder = holder;
        itemHolder.tv_time.setText(AllEvents.get(position).getTime_s());
        itemHolder.tv_event.setText(AllEvents.get(position).getEvent());
        itemHolder.tv_inform.setText(setPattern(position));
        itemHolder.card_item.setTag(position);
    }

    @Override
    public int getItemCount() {
        return AllEvents.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (mOnItemLongClickListener != null){
            mOnItemLongClickListener.onItemLongClick(view,(int)view.getTag());
        }
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (mOnTouchListener != null){
            mOnTouchListener.onTouch(view,motionEvent);
        }
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

    // Pattern的显示效果实现
    public String setPattern(int position){
        String pattern = "" ;
        Integer code = AllEvents.get(position).getPattern();
        switch (code){
            case 1:
                pattern = "将在上一事项结束后提醒你";
                break;
            case 2:
                pattern = "将在开始前半小时提醒你";
                break;
            case 3:
                pattern = "将在开始前一小时提醒你";
                break;
            case 4:
                pattern = "将在开始前3天提醒你";
                break;
            case 5:
                pattern = "使用自定义模式提醒";
                break;
        }
        return pattern;
    }

    // 自建OnItemClickListener
    private OnItemClickListener mOnItemClickListener = null;
    public static interface OnItemClickListener{
        void onItemClick(View v,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    // 自建OnItemLongClickListener
    private OnItemLongClickListener mOnItemLongClickListener = null;
    public static interface OnItemLongClickListener{
        void onItemLongClick(View v,int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listner){
        this.mOnItemLongClickListener = listner;
    }

    private View.OnTouchListener mOnTouchListener = null;
    public static interface OnTouch{
        void onTouch(View v,MotionEvent motionEvent);
    }

    public void setOnTouchListener(View.OnTouchListener listener){
        this.mOnTouchListener = listener;
    }

}
