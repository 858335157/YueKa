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
        setContentView(R.layout.load);

        if (UserManage.getInstance().hasUserInfo(this))//自动登录判断，SharePrefences中有数据，则跳转到主页，没数据则跳转到登录页
        {
            mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);
        } else {
            mHandler.sendEmptyMessageAtTime(GO_LOGIN, 2000);
        }

    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME://去主页
                    Intent intent = new Intent(load.this, main.class);
                    startActivity(intent);
                    finish();
                    break;
                case GO_LOGIN://去登录页
                    Intent intent2 = new Intent(load.this, login.class);
                    startActivity(intent2);
                    finish();
                    break;
            }
            return false;
        }
    });
}
