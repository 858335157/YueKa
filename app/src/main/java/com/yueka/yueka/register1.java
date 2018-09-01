package com.yueka.yueka;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class register1 extends Activity {//注册流程第一步


    private EditText et_identid;
    private EditText et_studyid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register1);//打开注册1界面

        //获取控件
        et_identid = findViewById(R.id.et_identid);
        et_studyid = findViewById(R.id.et_studyid);


    }

    public void next(View view) {//下一步按钮监听
        if(isRight()){//如果输入信息正确
            Intent intent = new Intent(register1.this,register2.class);//把输入的信息带到下一activity
            Bundle bundle = new Bundle();
            bundle.putCharSequence("identid",et_identid.getText().toString().trim());//身份证号
            bundle.putCharSequence("studyid",et_studyid.getText().toString().trim());//学号
            intent.putExtras(bundle);
            startActivity(intent);

        }else{
            AlertDialog.Builder builder  = new AlertDialog.Builder(register1.this);//创建一个可交互的消息提示框
            builder.setMessage("对不起，您输入的身份证号或学号错误" ) ;
            builder.setPositiveButton("确认" ,  null );
            builder.show();
        }


    }
    public boolean isRight() {//验证输入信息是否正确
        String identid = et_identid.getText().toString().trim();//得到用户输入的身份证号
        String studyid = et_studyid.getText().toString().trim();//得到用户输入的学号
        return isidentify(identid) && studyid.length() == 10 && isInt(10, studyid);//验证1：身份证号验证（isidentify类）。2：学号长度是否为10。3学号是否都为整数。

    }

    private boolean isInt(int num,String str) {//字符串全为整数的效验类
        for(int i = 0; i < num ; i++ ){
            if(!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    private boolean isidentify(String str) {//身份证号验证，骄骄写的
        int[] coe = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};  //系数
        int[] rem = {1, 0, -1, 9, 8, 7, 6, 5, 4, 3, 2};               //余数
        int sum = 0;
        if (str.length() != 18) {
            return false;
        }
        if (str.charAt(0) == '0' || str.charAt(0) == '9') {
            return false;
        }
        for (int i = 0; i < 17; i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                return false;
            }
            sum += coe[i] * (str.charAt(i) - '0');
        }
        sum %= 11;
        return ((str.charAt(17) - '0') == rem[sum] || sum == 2) && (sum != 2 || str.charAt(17) == 'x' || str.charAt(17) == 'X');

    }
}
