package com.example.wenquxing;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class login extends Activity {
    private String zhanghao="wqx";
    private String mima="12345";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText username=(EditText)findViewById(R.id.et_number);
        final EditText password=(EditText)findViewById(R.id.et_password);
        Button login=(Button)findViewById(R.id.btn_login);
        Button zhuce=(Button)findViewById(R.id.btn_zhuce);
        final SharedPreferences sp=getSharedPreferences("login",MODE_PRIVATE);
        String usernam1=sp.getString("user",null);
        String password1=sp.getString("pass",null);
        if(usernam1!=null&&password1!=null){
            Intent intent=new Intent(login.this, zhu.class);
            startActivity(intent);
        }
        else{
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String in_username=username.getText().toString();
                    String in_password=password.getText().toString();
                    SharedPreferences.Editor editor=sp.edit();
                    if(in_username.equals(zhanghao)&&in_password.equals(mima))
                    {
                        editor.putString("user",in_password);
                        editor.putString("pass",in_username);
                        editor.commit();
                        Intent intent=new Intent(login.this, zhu.class);
                        startActivity(intent);
                        Toast.makeText(login.this,"登录成功",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(login.this,"账号密码错误",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(login.this, zhuce.class);
                startActivity(intent);
            }
        });
    }
}