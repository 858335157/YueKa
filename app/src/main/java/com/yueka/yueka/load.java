package com.yueka.yueka;


import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import com.yueka.yueka.separateclass.UserManage;


public class load extends Activity {

    private static final int GO_HOME = 0;//去主页
    private static final int GO_LOGIN = 1;//去登录页


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load);//打开load xml布局

        if (UserManage.getInstance().hasUserInfo(this))//自动登录判断，SharePrefences中有数据，则跳转到主页，没数据则跳转到登录页
        {
            mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);//有数据，发送消息GO_HOME
        } else {
            mHandler.sendEmptyMessageAtTime(GO_LOGIN, 2000);//没有数据，发送消息GO_LOGIN
        }

    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {//对发送到消息进行处理
            switch (msg.what) {
                case GO_HOME://去主页
                    Intent intent = new Intent(load.this, main.class);//创建新意图，从load跳转到main
                    startActivity(intent);
                    finish();//关闭当前activity
                    break;
                case GO_LOGIN://去登录页
                    Intent intent2 = new Intent(load.this, login.class);//创建新意图，从load跳转到login
                    startActivity(intent2);
                    finish();//关闭当前activity
                    break;
            }
            return false;
        }
    });
}
