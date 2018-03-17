package com.wyf.daily;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
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
 * 正常登录页面
 *
 * @author wifi9984
 * @date 2018/3/17
 */

public class SignInFragment extends android.app.Fragment implements View.OnClickListener {
    protected Context mContext;
    protected View mView;
    private ImageView logoImage;
    private AutoCompleteTextView usernameInput;
    private AutoCompleteTextView passwordInput;
    private Button signIn;
    private Button signUp;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

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
        logoImage = mView.findViewById(R.id.signin_img_avator);
        usernameInput = mView.findViewById(R.id.signin_atv_username);
        passwordInput = mView.findViewById(R.id.signin_atv_password);
        signIn = mView.findViewById(R.id.signin_btn_signin);
        signIn.setOnClickListener(this);
        signUp = mView.findViewById(R.id.signin_btn_signup);
        signUp.setOnClickListener(this);
        // getFragmentManager用于Fragment跳转
        fragmentManager = getFragmentManager();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin_btn_signin:
                signInUser(usernameInput.getText().toString(), passwordInput.getText().toString());
                break;
            case R.id.signin_btn_signup:
                fragmentTransaction = fragmentManager.beginTransaction();
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                SignUpFragment signUpFragment = new SignUpFragment();
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                bundle.putString("password", password);
                signUpFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frame_login, signUpFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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
