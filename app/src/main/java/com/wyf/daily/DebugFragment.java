package com.wyf.daily;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * This fragment is used to debug.
 */

public class DebugFragment extends android.app.Fragment implements View.OnClickListener {

    protected View mView;
    protected Context mContext;
    private Button btn_read;
    private Button btn_clean;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.debug_fragment,container,false);
        btn_read = (Button)mView.findViewById(R.id.btn_debug_read);
        btn_clean = (Button)mView.findViewById(R.id.btn_debug_clean);
        btn_read.setOnClickListener(this);
        btn_clean.setOnClickListener(this);
        return mView;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_debug_read){

        }else if(v.getId() == R.id.btn_debug_clean){

        }
    }
}
