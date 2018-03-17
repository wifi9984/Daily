package com.wyf.daily;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;

/**
 * Created by 11512 on 2018/3/17.
 */

public class SignInFragment extends android.app.Fragment implements View.OnClickListener {
    protected Context mContext;
    protected View mView;
    private ImageView logoImage;
    private AutoCompleteTextView usernameInput;
    private AutoCompleteTextView passwordInput;
    private Button signIn;
    private Button signUp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.login_fragment_default, container, false);
        init();
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void init() {
        logoImage = mView.findViewById(R.id.login_img_avator);
        usernameInput = mView.findViewById(R.id.login_atv_username);
        passwordInput = mView.findViewById(R.id.login_atv_password);
        signIn = mView.findViewById(R.id.login_btn_signin);
        signIn.setOnClickListener(this);
        signUp = mView.findViewById(R.id.login_btn_signup);
        signUp.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_signin:
                signInUser(usernameInput.getText().toString(), passwordInput.getText().toString());
                break;
            case R.id.login_btn_signup:
                break;
            default: break;
        }
    }

    void signInUser (String username, String password) {
        AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null){
                    Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(mContext, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(mContext, "登录失败", Toast.LENGTH_SHORT).show();
                    // 若返回类型为账户不存在，提示注册
                }
            }
        });
    }
}
