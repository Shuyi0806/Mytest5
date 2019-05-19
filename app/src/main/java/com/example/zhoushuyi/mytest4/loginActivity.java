package com.example.zhoushuyi.mytest4;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by zhoushuyi on 2019/5/17.
 */


public class loginActivity extends AppCompatActivity {

    private EditText username;
    private EditText passward;
    private Button loginbutton;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.useredit);
        passward = (EditText) findViewById(R.id.passwardedit);
        loginbutton = (Button) findViewById(R.id.loginbutton);
    }

    /**
     * login event
     * @param v
     */
    public void OnMyLoginClick(View v){
        if(pubFun.isEmpty(username.getText().toString()) || pubFun.isEmpty(passward.getText().toString())){
            Toast.makeText(this, "手机号或密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        //call DBOpenHelper
        DBOpenHelper helper = new DBOpenHelper(this,"qianbao.db",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.query("user_tb",null,"userID=? and pwd=?",new String[]{username.getText().toString(),passward.getText().toString()},null,null,null);
        if(c!=null && c.getCount() >= 1){
            String[] cols = c.getColumnNames();
            while(c.moveToNext()){
                for(String ColumnName:cols){
                    Log.i("info",ColumnName+":"+c.getString(c.getColumnIndex(ColumnName)));
                }
            }
            c.close();
            db.close();

            //将登陆用户信息存储到SharedPreferences中
            SharedPreferences mySharedPreferences= getSharedPreferences("setting", Activity.MODE_PRIVATE); //实例化SharedPreferences对象
            SharedPreferences.Editor editor = mySharedPreferences.edit();//实例化SharedPreferences.Editor对象
            editor.putString("userID", username.getText().toString()); //用putString的方法保存数据
            editor.commit(); //提交当前数据

            this.finish();
        }
        else{
            Toast.makeText(this, "手机号或密码输入错误！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Jump to the registration interface
     * @param v
     */
    public void OnMyRegistClick(View v)  {
        Intent intent=new Intent(loginActivity.this,RegistActivity.class);
        //intent.putExtra("info", "No66778899");
        loginActivity.this.startActivity(intent);
    }

    /**
     * Jump to reset password interface
     * @param v
     */
    public void OnMyResPwdClick(View v){
        Intent intent=new Intent(loginActivity.this,ResPwdActivity.class);
        loginActivity.this.startActivity(intent);
    }
}