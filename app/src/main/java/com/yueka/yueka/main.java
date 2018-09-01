package com.yueka.yueka;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class main extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);//默认打开home布局

    }



    //导航栏点击事件监听
    public void navclick(View view){
        switch (view.getId()){
            case R.id.home://去主页
                setContentView(R.layout.home);
                break;
            case R.id.chatting://去约聊
                setContentView(R.layout.chatting);
                break;

            case R.id.forum://去论坛
                setContentView(R.layout.forum);
                break;

            case R.id.list://去魅力榜
                setContentView(R.layout.list);
                break;

            case R.id.recommend://去推荐
                setContentView(R.layout.recommend);
                break;

        }
    }

    public void friendrecommendclick(View view){//打开好友推荐界面
        Intent intent =new Intent(main.this,friendrecommend.class);
        startActivity(intent);
    }

    public void addroom(View view){//打开创建房间界面
        Intent intent =new Intent(main.this,addroom.class);
        startActivity(intent);
    }
}
