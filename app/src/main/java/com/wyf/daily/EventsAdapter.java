package com.wyf.daily;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *  Adapter用于呈现数据库存储的事件详情
 *  从数据库拉取数据，写到每一个Event对象里面，再通过对象读取数据加载到RecyclerView中
 *  这一块我堵了好久，RecyclerView作为一个高级控件，需要多看文档= =
 *
 *  @author wifi9984
 *  @date 2017/9/1
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ItemHolder>
        implements View.OnClickListener,View.OnLongClickListener {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Event> allEvents;

    public EventsAdapter(Context context, ArrayList<Event> publicArray) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        allEvents = publicArray;
    }

    public void onDataUpdate(ArrayList<Event> publicArray) {
        allEvents = publicArray;
        notifyDataSetChanged();
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.events_recycle_item,parent,false);
        ItemHolder holder = new ItemHolder(v);
        v.setOnClickListener(this);
        v.setOnLongClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, final int position) {
        ItemHolder itemHolder = holder;
        itemHolder.tvTime.setText(allEvents.get(position).getTimeStart());
        itemHolder.tvEvent.setText(allEvents.get(position).getEvent());
        itemHolder.tvInform.setText(setPattern(position));
        itemHolder.itemLayout.setTag(position);
        itemHolder.cardItem.setTag(position);
    }

    @Override
    public int getItemCount() {
        return allEvents.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (mOnItemLongClickListener != null) {
            mOnItemLongClickListener.onItemLongClick(view,(int)view.getTag());
        }
        return false;
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        public TextView tvTime;
        public TextView tvEvent;
        public TextView tvInform;
        public CardView cardItem;
        public LinearLayout itemLayout;

        public ItemHolder(View v) {
            super(v);
            cardItem = v.findViewById(R.id.card_item);
            itemLayout = v.findViewById(R.id.item_layout);
            tvTime = v.findViewById(R.id.tv_home_read_start_time);
            tvEvent = v.findViewById(R.id.tv_home_read_event);
            tvInform = v.findViewById(R.id.tv_home_read_inform_time);
        }
    }

    /**
     * Pattern的显示效果实现
     *
     * @param position
     * @return pattern
     */
    private String setPattern(int position) {
        String pattern = "" ;
        Integer code = allEvents.get(position).getPattern();
        switch (code) {
            case 1:
                pattern = "上一件事结束后提醒";
                break;
            case 2:
                pattern = "提前半小时提醒";
                break;
            case 3:
                pattern = "提前一小时提醒";
                break;
            case 4:
                pattern = "提前3天提醒";
                break;
            case 5:
                pattern = "自定义提醒";
                break;
            default:
                pattern = "上一件事结束后提醒";
                break;
        }
        return pattern;
    }

    /**
     * 自建OnItemClickListener
     */
    private OnItemClickListener mOnItemClickListener = null;
    public static interface OnItemClickListener {
        /**
         *
         * @param v
         * @param position
         */
        void onItemClick(View v,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /**
     * 自建OnItemLongClickListener
     */
    private OnItemLongClickListener mOnItemLongClickListener = null;
    public static interface OnItemLongClickListener{
        /**
         *
         * @param v
         * @param position
         */
        void onItemLongClick(View v,int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listner){
        this.mOnItemLongClickListener = listner;
    }
}
