package com.yueka.yueka;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yueka.yueka.separateclass.HttpRequest;
import com.yueka.yueka.separateclass.UserManage;

public class register3 extends Activity{

    private String identid;
    private String studyid;
    private String phone;
    private String password;
    private EditText et_pwd1;
    private EditText et_pwd2;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register3);
        getsIntent();
        et_pwd1 = findViewById(R.id.et_pwd1);
        et_pwd2 = findViewById(R.id.et_pwd2);

    }

    private void getsIntent(){
        Intent intent1 = getIntent();
        Bundle bundle =  intent1.getExtras();
        if (bundle != null) {
            identid = bundle.getString("identid");
            studyid = bundle.getString("studyid");
            phone = bundle.getString("phone");
        }else{
            Toast.makeText(register3.this,"数据传输错误！请确保程序正常运行再重试",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void register(View view) {
        if((et_pwd1.getText().toString().trim().length()<8)||(et_pwd1.getText().toString().trim().length()>20)){
            Toast.makeText(register3.this,"密码长度限定8~20位，请检查并重试！",Toast.LENGTH_SHORT).show();
        }else if(!et_pwd1.getText().toString().trim().equals(et_pwd2.getText().toString().trim())){
            Toast.makeText(register3.this,"两次密码不一致，请检查并重试！",Toast.LENGTH_SHORT).show();
        }else{
            password = et_pwd1.getText().toString().trim();
            registerto();
        }
    }

    private void registerto(){
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                if( msg.what == 0x110 ) {
                    UserManage.getInstance().saveUserInfo(register3.this, studyid);
                    Intent intent1 = new Intent(register3.this, main.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);
                }else if(msg.what == 0x101){
                    AlertDialog.Builder builder  = new AlertDialog.Builder(register3.this);
                    builder.setMessage("网路错误，请联系作者QQ：858335157" ) ;
                    builder.setPositiveButton("确认" ,  null );
                    builder.show();
                }else{
                    AlertDialog.Builder builder  = new AlertDialog.Builder(register3.this);
                    builder.setMessage("未知错误，请联系作者QQ：858335157" ) ;
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
                if(a=='f'){
                    m.what = 0x111;
                    mHandler.sendMessage(m);
                }else if(a=='s'){
                    m.what = 0x110;
                    mHandler.sendMessage(m);
                }else{
                    m.what = 0x101;
                    mHandler.sendMessage(m);
                }
            }

            private Character registertoto(){
                String param = "identid="+identid+"&studyid="+studyid+"&phone="+phone+"&password="+password;
                String result = HttpRequest.sendPost("http://wyjwyj.vicp.io:25098/YueKa/register",param);
                return result.charAt(10);
            }
        }).start();
    }
}
