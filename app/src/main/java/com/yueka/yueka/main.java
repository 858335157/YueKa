package com.yueka.yueka;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class main extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

    }



    public void navclick(View view){
        switch (view.getId()){
            case R.id.home:
                setContentView(R.layout.home);
                break;
            case R.id.chatting:
                setContentView(R.layout.chatting);
                break;

            case R.id.forum:
                setContentView(R.layout.forum);
                break;

            case R.id.list:
                setContentView(R.layout.list);
                break;

            case R.id.recommend:
                setContentView(R.layout.recommend);
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
