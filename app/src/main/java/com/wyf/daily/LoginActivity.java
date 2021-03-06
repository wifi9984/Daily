package com.wyf.daily;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能：用户登录与注册
 *
 * @author wifi9984
 * @date 2018/3/16
 */

public class LoginActivity extends AppCompatActivity {

    @BindView(R2.id.login_toolbar) Toolbar toolbar;
    private Context mContext;
    private SignInFragment signInFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        init();
        initToolBar();
        initFragment(savedInstanceState);
    }

    void init() {
        mContext = this;
    }

    void initToolBar() {
        toolbar.setTitle(R.string.login_title);
        setSupportActionBar(toolbar);
    }

    void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            if (signInFragment == null) {
                signInFragment = new SignInFragment();
            }
            ft.replace(R.id.frame_login, signInFragment).commit();
        }
    }

}
