package com.yueka.yueka;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yueka.yueka.separateclass.HttpRequest;
import com.yueka.yueka.separateclass.UserManage;

public class login extends Activity {

    private EditText et_userid;
    private EditText et_userpwd;
    private Handler nHandler;
    private TextView tv_service;
    private Button btn_register;
    private String userpwd;
    private String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btn_register = findViewById(R.id.btn_register);
        tv_service = findViewById(R.id.tv_service);
        et_userid = findViewById(R.id.et_userid);
        et_userpwd = findViewById(R.id.et_userpwd);


        tv_service.setText(Html.fromHtml("登录即代表阅读并同意<a href='https://www.baidu.com/'>服务条款</a>"));
        tv_service.setMovementMethod(LinkMovementMethod.getInstance());
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this,register1.class);
                startActivity(intent);
            }
        });
    }


    public void click(View view) {

        nHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                if( msg.what == 0x110 ) {
                    UserManage.getInstance().saveUserInfo(login.this, userid, userpwd);
                    Intent intent1 = new Intent(login.this, main.class);
                    startActivity(intent1);
                    finish();
                }else if(msg.what == 0x101){
                    AlertDialog.Builder builder  = new AlertDialog.Builder(login.this);
                    builder.setMessage("网路错误，请联系作者QQ：858335157" ) ;
                    builder.setPositiveButton("确认" ,  null );
                    builder.show();
                }else{
                    AlertDialog.Builder builder  = new AlertDialog.Builder(login.this);
                    builder.setMessage("账号或密码错误，请重新输入。" ) ;
                    builder.setPositiveButton("确认" ,  null );
                    builder.show();
                }
                return false;
            }
        }) ;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message m = new Message();
                Character a =loginto();
                if(a=='f'){
                    m.what = 0x111;
                    nHandler.sendMessage(m);
                }else if(a=='s'){
                    m.what = 0x110;
                    nHandler.sendMessage(m);
                }else{
                    m.what = 0x101;
                    nHandler.sendMessage(m);
                }
            }

            private Character loginto(){
                userid = et_userid.getText().toString().trim();
                if(userid.length()!=10){
                    return 'l';
                }
                userpwd = et_userpwd.getText().toString().trim();
                String param = "userid="+userid+"&userpwd="+userpwd;
                String result = HttpRequest.sendPost("http://wyjwyj.vicp.io:25098/YueKa/login",param);
                return result.charAt(10);
            }
        }).start();


    }
}
