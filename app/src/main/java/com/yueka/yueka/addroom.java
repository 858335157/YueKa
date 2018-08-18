package com.yueka.yueka;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class addroom extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addroom);




    }

    public void backclick(View view) {
        finish();
    }
}
