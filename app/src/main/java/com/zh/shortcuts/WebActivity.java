package com.zh.shortcuts;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.zh.shortcuts.databinding.ActivityWebBinding;

public class WebActivity extends AppCompatActivity {
    private ActivityWebBinding binding;

    public static void launch(Context context,String url){
        Intent intent=new Intent(context,WebActivity.class);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil. setContentView(this,R.layout.activity_web);
        binding.setPresenter(this);
        String url=getIntent().getStringExtra("url");
        if (url==null|| TextUtils.isEmpty(url)){
            url="https://blog.csdn.net/qq_23025319";
        }
        binding.webView.loadUrl(url);
    }


}
