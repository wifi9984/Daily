package com.wyf.daily;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;

/**
 * 功能：简单的用户注册
 *
 * @author wifi9984
 * @date 2017/12/22
 */

public class SignUpFragment extends Fragment {
    protected Context mContext;
    protected View mView;
    private Button signUp;
    private EditText username;
    private EditText password;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.fragment_signup,container,false);
        init();
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void init() {
        signUp = mView.findViewById(R.id.user_btn_signup);
        username = mView.findViewById(R.id.user_edt_username);
        password = mView.findViewById(R.id.user_edt_password);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpNewUser(username.getText().toString(), password.getText().toString());
            }
        });
    }

    void signUpNewUser(String username, String password) {
        AVUser user = new AVUser();
        user.setUsername(username);
        user.setPassword(password);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Toast.makeText(mContext,"注册成功",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext,"注册失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
