package com.wyf.daily;

import com.wyf.daily.EventsDBHelper;
import com.wyf.daily.Event;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewEventActivity extends Activity implements View.OnClickListener,
        mDatePickerDialog.OnDateSetListener,mTimePickerDialog.OnTimeSetListener{
    private TextView tv_pick_date;
    private TextView tv_pick_time_start;
    private TextView tv_pick_time_end;
    private TextView tv_select_pattern;
    private EditText edt_event;
    private EventsDBHelper mHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_event);
        tv_pick_date = (TextView)findViewById(R.id.tv_pick_date);
        tv_pick_date.setOnClickListener(this);
        tv_pick_time_start = (TextView)findViewById(R.id.tv_pick_time_start);
        tv_pick_time_start.setOnClickListener(this);
        tv_pick_time_end = (TextView)findViewById(R.id.tv_pick_time_end);
        tv_pick_time_end.setOnClickListener(this);
        tv_select_pattern = (TextView)findViewById(R.id.tv_select_pattern);
        tv_select_pattern.setOnClickListener(this);
        edt_event = (EditText)findViewById(R.id.edt_event);
        tv_pick_date.setText(TimeUtils.getNowDate());
        tv_pick_time_start.setText(TimeUtils.getNowTime());
        tv_pick_time_end.setText(TimeUtils.getNowTime());
        tv_select_pattern.setText("上一件事结束后");
        findViewById(R.id.btn_confirm_add).setOnClickListener(this);
        findViewById(R.id.btn_cancel_add).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHelper = EventsDBHelper.getInstance(this,1);
        mHelper.openWriteLink();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case(R.id.btn_confirm_add):
                //if .... = null
                onSaveEvent();
                onDismiss();
                onStop();
                break;
            case(R.id.btn_cancel_add):
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("正在编辑：");
                builder.setMessage("确认放弃编辑吗？这一事件将不会被保存");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onDismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                //设置按钮文字颜色，POSITIVE使用默认Accent，不需要重新赋值
                Button button = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                button.setTextColor(getResources().getColor(R.color.grey500));
                break;
            case(R.id.tv_pick_date):
                Calendar calendar = Calendar.getInstance();
                mDatePickerDialog picker_date = new mDatePickerDialog(this);
                picker_date.setDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),this);
                picker_date.show();
                break;
            case(R.id.tv_pick_time_start):
                Calendar calendar1 = Calendar.getInstance();
                mTimePickerDialog picker_times = new mTimePickerDialog(this);
                picker_times.setTime(calendar1.get(Calendar.HOUR_OF_DAY),calendar1.get(Calendar.MINUTE),this);
                picker_times.show();
                tv_pick_time_start.setVisibility(View.INVISIBLE);
                break;
            case (R.id.tv_pick_time_end):
                Calendar calendar2 = Calendar.getInstance();
                mTimePickerDialog picker_timee = new mTimePickerDialog(this);
                picker_timee.setTime(calendar2.get(Calendar.HOUR_OF_DAY),calendar2.get(Calendar.MINUTE),this);
                picker_timee.show();
                tv_pick_time_end.setVisibility(View.INVISIBLE);
                break;
            case (R.id.tv_select_pattern):
                final String[] list_pattern = {"上一件事结束后","半小时前","一小时前","三天前","自定义"};
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("选择提醒方式：");
                builder1.setSingleChoiceItems(list_pattern, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String str = list_pattern[which];
                        tv_select_pattern.setText(str);
                    }
                });
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder1.show();
                break;
        }
    }

    public void onDismiss(){
        //看似dismiss，其实是跳转回主界面并进行刷新
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public int pattern(String str){
        //把pattern转化为数字
        Integer pattern;
        switch (str){
            case ("半小时前"):
                pattern = 2;
                break;
            case ("一小时前"):
                pattern = 3;
                break;
            default:
                pattern = 1;
                break;
        }
        return pattern;
    }

    public void onSaveEvent(){
        Event event = new Event(edt_event.getText().toString(),tv_pick_date.getText().toString(),
                tv_pick_time_start.getText().toString(),tv_pick_time_end.getText().toString(),
                pattern(tv_select_pattern.getText().toString()));
        mHelper.insert(event);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHelper.closeLink();
    }

    @Override
    public void onDateSet(int year, int monthOfYear, int dayOfMonth) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年M月d日 - EE");
        Date selDate = new Date(year - 1900,monthOfYear,dayOfMonth);
        String strDate = dateFormat.format(selDate);
        tv_pick_date.setText(strDate);
    }

    @Override
    public void onTimeSet(int hour, int min) {
        int year = 1;
        int month = 1;
        int date = 1;
        SimpleDateFormat timeFormat = new SimpleDateFormat("a - h:mm");
        Date selTime = new Date(year,month,date,hour,min);
        String strTime = timeFormat.format(selTime);
        if(tv_pick_time_start.getVisibility() == View.INVISIBLE){
            tv_pick_time_start.setVisibility(View.VISIBLE);
            tv_pick_time_start.setText(strTime);
            tv_pick_time_end.setText(strTime);
        }else if(tv_pick_time_end.getVisibility() == View.INVISIBLE){
            tv_pick_time_end.setVisibility(View.VISIBLE);
            tv_pick_time_end.setText(strTime);
        }

    }
}
