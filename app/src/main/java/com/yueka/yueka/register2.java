package com.yueka.yueka;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yueka.yueka.separateclass.HttpRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class register2 extends Activity {

    private String identid;
    private String studyid;
    private String phonenumber;
    private String ident;
    private EditText phone;
    private EditText identfy;
    private TextView tv_hint;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register2);

        phone = findViewById(R.id.et_phone1);
        identfy = findViewById(R.id.et_inputident);
        tv_hint = findViewById(R.id.tv_hint);
        getsIntent();


    }

    private void getsIntent(){
        Intent intent1 = getIntent();
        Bundle bundle =  intent1.getExtras();
        if (bundle != null) {
            identid = bundle.getString("identid");
            studyid = bundle.getString("studyid");
        }else{
            Toast.makeText(register2.this,"数据传输错误！请确保程序正常运行再重试",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void getident(View view) {

        if(isRight()) {
            StringBuilder str1 = new StringBuilder();
            int ran;
            for (int i = 0; i < 4; i++) {
                ran = (int) (Math.random() *10);
                ran%=10;
                str1.append(ran);
            }
            ident = str1.toString();
            AlertDialog.Builder builder = new AlertDialog.Builder(register2.this);
            String str = "【约咖】您的验证码为" + str1.toString() + ",5分钟内有效，请勿告知他人。如非本人操作，请忽略。";
            builder.setMessage(str);
            String tvhint = str+"(因水平有限，暂时就用随机数当作验证码凑凑数。。。。。)";
            tv_hint.setText(tvhint);
            builder.setPositiveButton("确认", null);
            builder.show();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(register2.this);
            builder.setMessage("您输入的电话号码有误，请检查并重试！");
            builder.setPositiveButton("确认", null);
            builder.show();
        }
    }

    private boolean isRight(){
        phonenumber = phone.getText().toString().trim();
        String regExp = "[1][3587]\\d{9}";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phonenumber);
        return m.find();
    }

    public void next2(View view) {
        if(identfy.getText().toString().trim().equals(ident)){
            registerto();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(register2.this);
            builder.setMessage("您输入的验证码有误，请检查并重试！");
            builder.setPositiveButton("确认", null);
            builder.show();
        }

    }

    private void registerto(){
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                if( msg.what == 0x110 ) {
                    Intent intent1 = new Intent(register2.this, register3.class);
                    Bundle bundle = new Bundle();
                    bundle.putCharSequence("identid",identid);
                    bundle.putCharSequence("studyid",studyid);
                    bundle.putCharSequence("phone",phonenumber);
                    intent1.putExtras(bundle);
                    startActivity(intent1);
                    finish();
                }else if(msg.what == 0x101){
                    AlertDialog.Builder builder  = new AlertDialog.Builder(register2.this);
                    builder.setMessage("网路错误，请联系作者QQ：858335157" ) ;
                    builder.setPositiveButton("确认" ,  null );
                    builder.show();
                }else{
                    AlertDialog.Builder builder  = new AlertDialog.Builder(register2.this);
                    builder.setMessage("身份证号，学号或手机号有被注册，请更换" ) ;
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
                Character a = registertoto();
                if(a=='s'){
                    m.what = 0x111;
                    mHandler.sendMessage(m);
                }else if(a=='f'){
                    m.what = 0x110;
                    mHandler.sendMessage(m);
                }else{
                    m.what = 0x101;
                    mHandler.sendMessage(m);
                }
            }

            private Character registertoto(){
                String param = "identid="+identid+"&studyid="+studyid+"&phone="+phonenumber;
                String result = HttpRequest.sendPost("http://wyjwyj.vicp.io:25098/YueKa/find",param);
                return result.charAt(10);
            }
        }).start();
    }
}
