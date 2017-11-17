package com.wyf.daily;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;

import java.util.Date;

/**
 * 自定义的DatePickerDialog
 *
 * @author wifi9984
 * @date 2017/8/31
 */

public class mDatePickerDialog implements View.OnClickListener,DatePicker.OnDateChangedListener {
    private Dialog dialog;
    private View view;
    private DatePicker picker_date;

    public mDatePickerDialog(Context context){
        view = LayoutInflater.from(context).inflate(R.layout.picker_date,null);
        dialog = new Dialog(context,R.style.Theme_AppCompat_Light_Dialog_Alert_Self);
        picker_date = (DatePicker)view.findViewById(R.id.picker_date);
        view.findViewById(R.id.btn_confirm_dp).setOnClickListener(this);
    }

    public void setDate(int year, int month, int day,OnDateSetListener listener){
        picker_date.init(year,month,day,this);
        mDateSetListener = listener;
    }

    public void show(){
        dialog.getWindow().setContentView(view);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    public void dismiss(){
        if(dialog!=null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if(mDateSetListener != null){
            picker_date.clearFocus();
            mDateSetListener.onDateSet(picker_date.getYear(), picker_date.getMonth(), picker_date.getDayOfMonth());
        }
    }

    @Override
    public void onDateChanged(DatePicker view,int year,int monthOfYear,int dayOfMonth){
        picker_date.init(year,monthOfYear,dayOfMonth,this);
    }

    private OnDateSetListener mDateSetListener;
    public interface OnDateSetListener{
        void onDateSet(int year,int monthOfYear,int dayOfMonth);
    }
}
