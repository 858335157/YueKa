package com.yueka.yueka;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class main extends Activity {
    private View layout_home;
    private View layout_chatting;
    private View layout_forum;
    private View layout_list;
    private View layout_recommend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        layout_home = findViewById(R.id.layout_home);
        layout_chatting = findViewById(R.id.layout_chatting);
        layout_forum = findViewById(R.id.layout_forum);
        layout_list = findViewById(R.id.layout_list);
        layout_recommend = findViewById(R.id.layout_recommend);
    }



    public void navclick(View view){
        switch (view.getId()){
            case R.id.home:
                layout_home.setVisibility(View.VISIBLE);
                layout_chatting.setVisibility(View.GONE);
                layout_forum.setVisibility(View.GONE);
                layout_list.setVisibility(View.GONE);
                layout_recommend.setVisibility(View.GONE);
                break;
            case R.id.chatting:
                layout_home.setVisibility(View.GONE);
                layout_chatting.setVisibility(View.VISIBLE);
                layout_forum.setVisibility(View.GONE);
                layout_list.setVisibility(View.GONE);
                layout_recommend.setVisibility(View.GONE);
                break;

            case R.id.forum:
                layout_home.setVisibility(View.GONE);
                layout_chatting.setVisibility(View.GONE);
                layout_forum.setVisibility(View.VISIBLE);
                layout_list.setVisibility(View.GONE);
                layout_recommend.setVisibility(View.GONE);
                break;

            case R.id.list:
                layout_home.setVisibility(View.GONE);
                layout_chatting.setVisibility(View.GONE);
                layout_forum.setVisibility(View.GONE);
                layout_list.setVisibility(View.VISIBLE);
                layout_recommend.setVisibility(View.GONE);
                break;

            case R.id.recommend:
                layout_home.setVisibility(View.GONE);
                layout_chatting.setVisibility(View.GONE);
                layout_forum.setVisibility(View.GONE);
                layout_list.setVisibility(View.GONE);
                layout_recommend.setVisibility(View.VISIBLE);
                break;

        }
    }

    public void friendrecommendclick(View view){
        Intent intent =new Intent(main.this,friendrecommend.class);
        startActivity(intent);
    }

    public void addroom(View view){
        Intent intent =new Intent(main.this,addroom.class);
        startActivity(intent);
    }
}
