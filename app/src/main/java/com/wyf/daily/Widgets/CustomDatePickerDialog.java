package com.wyf.daily.Widgets;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;

import com.wyf.daily.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 自定义的DatePickerDialog
 *
 * @author wifi9984
 * @date 2017/8/31
 */

public class CustomDatePickerDialog implements View.OnClickListener,DatePicker.OnDateChangedListener {

    private Dialog dialog;
    private View view;
    private DatePicker datePicker;

    public CustomDatePickerDialog(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_date_picker_dialog,null);
        ButterKnife.bind(view);
        dialog = new Dialog(context,R.style.Theme_AppCompat_Light_Dialog_Alert_Self);
        datePicker = view.findViewById(R.id.custom_date_picker_dp);
        view.findViewById(R.id.custom_date_picker_btn_confirm).setOnClickListener(this);
    }

    public void setDate(int year, int month, int day,OnDateSetListener listener) {
        datePicker.init(year,month,day,this);
        mDateSetListener = listener;
    }

    public void show() {
        dialog.getWindow().setContentView(view);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    public void dismiss() {
        if(dialog!=null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if(mDateSetListener != null){
            datePicker.clearFocus();
            mDateSetListener.onDateSet(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        }
    }

    @Override
    public void onDateChanged(DatePicker view,int year,int monthOfYear,int dayOfMonth) {
        datePicker.init(year,monthOfYear,dayOfMonth,this);
    }

    private OnDateSetListener mDateSetListener;
    public interface OnDateSetListener {
        void onDateSet(int year,int monthOfYear,int dayOfMonth);
    }
}
