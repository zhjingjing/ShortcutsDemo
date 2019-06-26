package com.zh.shortcuts;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.zh.shortcuts.databinding.ActivityUserInfoBinding;

public class UserInfoActivity extends AppCompatActivity {
    private ActivityUserInfoBinding binding;
    String msg;
    public static void launch(Context context,String msg){
        Intent intent=new Intent(context,UserInfoActivity.class);
        intent.putExtra("msg",msg);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil. setContentView(this,R.layout.activity_user_info);
        binding.setPresenter(this);
        msg=getIntent().getStringExtra("msg");

        if (msg==null|| TextUtils.isEmpty(msg)){
            msg="这是默认页面";
        }
        binding.tvMsg.setText(msg);
    }
}
