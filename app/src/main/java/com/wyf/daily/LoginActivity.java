package com.wyf.daily;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;

/**
 * 功能：简单的用户注册
 *
 * @author wifi9984
 * @date 2018/3/16
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private EditText usernameInput;
    private EditText passwordInput;
    private Button signIn;
    private Button signUp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.login_activity);
        init();
    }

    void init() {
        mContext = this;
        usernameInput = findViewById(R.id.login_edt_username);
        passwordInput = findViewById(R.id.login_edt_password);
        signIn = findViewById(R.id.login_btn_signin);
        signUp = findViewById(R.id.login_btn_signup);
        signIn.setOnClickListener(this);
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
            case R.id.login_btn_signin :
                signInUser(usernameInput.getText().toString(), passwordInput.getText().toString());
                break;
            case R.id.login_btn_signup :
                signUpUser(usernameInput.getText().toString(), passwordInput.getText().toString());
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

    void signUpUser(String username, String password) {
        AVUser user = new AVUser();
        user.setUsername(username);
        user.setPassword(password);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
                    signInUser(usernameInput.getText().toString(), passwordInput.getText().toString());
                } else {
                    Toast.makeText(mContext, "注册失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
