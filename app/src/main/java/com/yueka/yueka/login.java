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

    private EditText et_userid;         //用户输入的用户学号
    private EditText et_userpwd;        //用户输入的密码
    private Handler nHandler;
    private TextView tv_service;
    private Button btn_register;        //注册界面跳转按钮
    private String userid;              //用户学号
    private String userpwd;             //密码


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);//打开login xml布局

        //通过id获取控件（绑定控件与对象）
        btn_register = findViewById(R.id.btn_register);
        tv_service = findViewById(R.id.tv_service);
        et_userid = findViewById(R.id.et_userid);
        et_userpwd = findViewById(R.id.et_userpwd);


        //嵌入html链接标签，暂时服务条款没做出来，直接链接到百度首页
        tv_service.setText(Html.fromHtml("登录即代表阅读并同意<a href='https://www.baidu.com/'>服务条款</a>"));
        tv_service.setMovementMethod(LinkMovementMethod.getInstance());

        //给注册按钮添加监听，
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this,register1.class);//创建新意图，从login跳转到register
                startActivity(intent);
            }
        });
    }


    //登录按钮的点击事件
    public void click(View view) {

        //网络操作不能在主线程进行，所以采用新建线程（new Thread)与服务器端交互，主线程与新线程用massage消息来交互
        nHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                if( msg.what == 0x110 ) {//消息代码为0x110，即代表登录信息正确，登录成功，进入主界面
                    UserManage.getInstance().saveUserInfo(login.this, userid);//把用户学号储存进SharePrefences（具体参考com.yueka.yueka.separateclass.UserManage），方便自动登录
                    Intent intent1 = new Intent(login.this, main.class);//创建新意图，从login跳转到register
                    startActivity(intent1);
                    finish();
                }else if(msg.what == 0x101){//消息代码为0x101，即代表服务器端出现错误，可能服务器没打开，或者服务器端代码修改导致不可用，需联系作者。
                    AlertDialog.Builder builder  = new AlertDialog.Builder(login.this);//创建一个可交互的消息提示框
                    builder.setMessage("网路错误，请联系作者QQ：858335157" ) ;//消息
                    builder.setPositiveButton("确认" ,  null );//添加一个确定按钮
                    builder.show();
                }else{//消息代码为0x111，即代表登录信息错误，可能是密码错误，也可能是学号错误，需重新输入
                    AlertDialog.Builder builder  = new AlertDialog.Builder(login.this);//创建一个可交互的消息提示框
                    builder.setMessage("学号或密码错误，请重新输入。" ) ;//消息
                    builder.setPositiveButton("确认" ,  null );//添加一个确定按钮
                    builder.show();
                }
                return false;
            }
        }) ;
        new Thread(new Runnable() {//新线程，网络操作
            @Override
            public void run() {
                Message m = new Message();//新建message对象
                Character a =loginto();//得到发送网络请求返回来的信息
                if(a=='f'){//如果是f则代表失败（false)
                    m.what = 0x111;
                    nHandler.sendMessage(m);
                }else if(a=='s'){//如果s是则代表成功（success)
                    m.what = 0x110;
                    nHandler.sendMessage(m);
                }else{//没有返回信息，服务器问题
                    m.what = 0x101;
                    nHandler.sendMessage(m);
                }
            }

            private Character loginto(){
                userid = et_userid.getText().toString().trim();//获取用户输入的学号
                if(userid.length()!=10){//一个简单的学号效验，需要骄骄写一个学号效验的类！
                    return 'f';
                }
                userpwd = et_userpwd.getText().toString().trim();//获取用户输入的密码
                String param = "userid="+userid+"&userpwd="+userpwd;//链接好后发送至服务器
                String result = HttpRequest.sendPost("http://wyjwyj.vicp.io:25098/YueKa/login",param);
                return result.charAt(10);//返回服务器返回的信息的第十个字符（具体原因懒得解释）
            }
        }).start();//开启新线程


    }
}

