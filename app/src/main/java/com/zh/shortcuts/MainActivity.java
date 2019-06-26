package com.zh.shortcuts;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.pm.ShortcutManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.zh.shortcuts.databinding.ActivityMainBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 本项目主要介绍android 7.0新增功能-桌面图标快捷操作
 * 实现方法
 * 静态配置：写死清单文件中。
 * 动态配置：代码中动态设置 ShortcutManager。
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil. setContentView(this,R.layout.activity_main);
        binding.setPresenter(this);

    }


    public void onUserInfoClicked(){
        UserInfoActivity.launch(this,"这是个人主页默认页面");
    }
    public void onWebClicked(int type){
        if (type==0){
            WebActivity.launch(this,"https://www.baidu.com");
        }else{
            WebActivity.launch(this,"https://github.com/zhjingjing");
        }
    }

    public void onPinnedCreateClicked(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);

                if (shortcutManager.isRequestPinShortcutSupported()) {
                    ShortcutInfo pinShortcutInfo =
                            new ShortcutInfo.Builder(this, "create0").build();
                    Intent pinnedShortcutCallbackIntent =
                            shortcutManager.createShortcutResultIntent(pinShortcutInfo);
                    PendingIntent successCallback = PendingIntent.getBroadcast(this, /* request code */ 0,
                            pinnedShortcutCallbackIntent, /* flags */ 0);
                    shortcutManager.requestPinShortcut(pinShortcutInfo,
                            successCallback.getIntentSender());
                }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onCreateClicked(){

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);

            List<ShortcutInfo> list= shortcutManager.getDynamicShortcuts();
            if (list==null){
                list=new ArrayList<>();
            }

            Intent intent=new Intent(this,UserInfoActivity.class);
            intent.putExtra("msg","这是个人主页快捷方式进入"+list.size());
            intent.setAction(Intent.ACTION_VIEW);
            ShortcutInfo shortcut = null;
            shortcut = new ShortcutInfo.Builder(this, "create"+list.size())
                    .setShortLabel("个人主页"+list.size())
                    .setLongLabel("个人主页"+list.size())
                    .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
                    .setIntent(intent)
                    .build();
            list.add(shortcut);
            try{
                if (shortcutManager.setDynamicShortcuts(list)){
                    Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                Toast.makeText(this,"已达到最大快捷方式数",Toast.LENGTH_SHORT).show();
            }

//            shortcutManager.addDynamicShortcuts(Arrays.asList(shortcut));

        }
    }


    public void onUpdateClicked(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
            List<ShortcutInfo> list= shortcutManager.getDynamicShortcuts();
            if (list==null){
                list=new ArrayList<>();
            }
            Intent intent=new Intent(this,UserInfoActivity.class);
            intent.putExtra("msg","这是个人主页快捷方式进入"+0+"修改后的");
            intent.setAction(Intent.ACTION_VIEW);

            ShortcutInfo shortcut = null;
            shortcut = new ShortcutInfo.Builder(this, "create6")
                    .setShortLabel("个人主页0")
                    .setLongLabel("个人主页0")
                    .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
                    .setIntent(intent)
                    .build();
            if ( shortcutManager.updateShortcuts(Arrays.asList(shortcut))){
                Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"修改失败",Toast.LENGTH_SHORT).show();
            }


        }
    }

    public void onDelClicked(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
            shortcutManager.removeAllDynamicShortcuts();
        }
    }



}
