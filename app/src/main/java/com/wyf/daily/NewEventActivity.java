package com.wyf.daily;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.transition.ArcMotion;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 从EventsFragment按加号按钮添加新事件的Activity
 *
 * @author wifi9984
 * @date 2017/12/22
 */

public class NewEventActivity extends Activity implements View.OnClickListener,
        CustomDatePickerDialog.OnDateSetListener,CustomTimePickerDialog.OnTimeSetListener{
    private TextView tvPickDate;
    private TextView tvPickTimeStart;
    private TextView tvPickTimeEnd;
    private TextView tvSelectPattern;
    private EditText edtEvent;
    private EventsDBHelper mHelper;
    private RelativeLayout rootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置WindowManager使得Activity以FloatingPage的样式展示，同时使用设定的宽高和Gravity
        Window win = this.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        win.setAttributes(lp);
        setContentView(R.layout.add_event);
        init();
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
                // “确认添加”的操作，需要检查是否为空：if .... = null
                onSaveEvent();
                onDismiss();
                break;
            case(R.id.btn_cancel_add):
                // 点击取消时需要提醒用户确认一次
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
                // 设置按钮文字颜色，POSITIVE使用默认Accent，不需要重新赋值
                Button button = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                button.setTextColor(getResources().getColor(R.color.grey500));
                break;
            case(R.id.tv_pick_date):
                // 加载+初始化DatePickerDialog，设定事件日期
                Calendar calendar = Calendar.getInstance();
                CustomDatePickerDialog pickerDate = new CustomDatePickerDialog(this);
                pickerDate.setDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),this);
                pickerDate.show();
                break;
            case(R.id.tv_pick_time_start):
                // 加载+初始化TimePickerDialog，设定开始日期
                Calendar calendar1 = Calendar.getInstance();
                CustomTimePickerDialog pickerTimes = new CustomTimePickerDialog(this);
                pickerTimes.setTime(calendar1.get(Calendar.HOUR_OF_DAY),calendar1.get(Calendar.MINUTE),this);
                pickerTimes.show();
                tvPickTimeStart.setSelected(true);
                break;
            case (R.id.tv_pick_time_end):
                // 加载+初始化TimePickerDialog，设定结束日期
                Calendar calendar2 = Calendar.getInstance();
                CustomTimePickerDialog pickerTimee = new CustomTimePickerDialog(this);
                pickerTimee.setTime(calendar2.get(Calendar.HOUR_OF_DAY),calendar2.get(Calendar.MINUTE),this);
                pickerTimee.show();
                tvPickTimeEnd.setSelected(true);
                break;
            case (R.id.tv_select_pattern):
                // 选择提醒模式，有点迷...就是用数字存储提醒的模式
                final String[] listPattern = {"上一件事结束后","半小时前","一小时前","三天前","自定义"};
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("选择提醒方式：");
                builder1.setSingleChoiceItems(listPattern, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String str = listPattern[which];
                        tvSelectPattern.setText(str);
                    }
                });
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder1.show();
                break;
            default:
                break;
        }
    }

    void init(){
        rootView = findViewById(R.id.activity_add_event);
        tvPickDate = (TextView)findViewById(R.id.tv_pick_date);
        tvPickDate.setOnClickListener(this);
        tvPickTimeStart = (TextView)findViewById(R.id.tv_pick_time_start);
        tvPickTimeStart.setOnClickListener(this);
        tvPickTimeEnd = (TextView)findViewById(R.id.tv_pick_time_end);
        tvPickTimeEnd.setOnClickListener(this);
        tvSelectPattern = (TextView)findViewById(R.id.tv_select_pattern);
        tvSelectPattern.setOnClickListener(this);
        edtEvent = (EditText)findViewById(R.id.edt_event);
        tvPickDate.setText(TimeUtils.getNowDate());
        tvPickTimeStart.setText(TimeUtils.getNowTime());
        tvPickTimeEnd.setText(TimeUtils.getNowTime());
        tvSelectPattern.setText("上一件事结束后");
        findViewById(R.id.btn_confirm_add).setOnClickListener(this);
        findViewById(R.id.btn_cancel_add).setOnClickListener(this);

        ArcMotion arcMotion = new ArcMotion();
        arcMotion.setMinimumHorizontalAngle(50f);
        arcMotion.setMinimumVerticalAngle(50f);

        Interpolator interpolator = AnimationUtils.loadInterpolator(this,
                android.R.interpolator.fast_out_slow_in);

        CustomChangeBounds changeBounds = new CustomChangeBounds();
        changeBounds.setPathMotion(arcMotion);
        changeBounds.setInterpolator(interpolator);
        changeBounds.addTarget(rootView);

        getWindow().setSharedElementEnterTransition(changeBounds);
        getWindow().setSharedElementExitTransition(changeBounds);
    }

    public void onDismiss(){
        // 看似dismiss，其实是跳转回主界面并进行刷新
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public int pattern(String str){
        // 把pattern转化为数字
        int pattern;
        switch (str){
            case ("半小时前"):
                pattern = 2;
                break;
            case ("一小时前"):
                pattern = 3;
                break;
            case ("三天前"):
                pattern = 4;
                break;
            case ("自定义"):
                pattern = 5;
                break;
            default:
                pattern = 1;
                break;
        }
        return pattern;
    }

    /**
     * 保存Event，用到数据库的insert操作
     */
    public void onSaveEvent(){
        Event event = new Event(edtEvent.getText().toString(), tvPickDate.getText().toString(),
                tvPickTimeStart.getText().toString(), tvPickTimeEnd.getText().toString(),
                pattern(tvSelectPattern.getText().toString()));
        mHelper.insert(event);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHelper.closeLink();
    }

    // 预想通过按回车键结束事件的输入，目前的方法仍存在问题
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm.isActive()){
                imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),0);
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onDateSet(int year, int monthOfYear, int dayOfMonth) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年M月d日 - EE");
        Date selDate = new Date(year - 1900,monthOfYear,dayOfMonth);
        String strDate = dateFormat.format(selDate);
        tvPickDate.setText(strDate);
    }

    @Override
    public void onTimeSet(int hour, int min) {
        int year = 1;
        int month = 1;
        int date = 1;
        SimpleDateFormat timeFormat = new SimpleDateFormat("a - h:mm");
        Date selTime = new Date(year,month,date,hour,min);
        String strTime = timeFormat.format(selTime);
        if(tvPickTimeStart.isSelected()){
            tvPickTimeStart.setSelected(false);
            tvPickTimeStart.setText(strTime);
            tvPickTimeEnd.setText(strTime);
        }else if(tvPickTimeEnd.isSelected()){
            tvPickTimeEnd.setSelected(false);
            tvPickTimeEnd.setText(strTime);
        }
    }
}
