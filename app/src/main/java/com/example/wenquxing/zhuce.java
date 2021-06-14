package com.example.wenquxing;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wenquxing.ai.Login.Login;

public class zhuce extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        final EditText user=(EditText)findViewById(R.id.et_number);
        final EditText pass=(EditText)findViewById(R.id.et_password);
        Button login=(Button)findViewById(R.id.btn_login1);
        Button fanhui=(Button)findViewById(R.id.btn_return);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String in_username=user.getText().toString();
                String in_password=pass.getText().toString();
                if(in_username.equals("wqx")&&in_password.equals("12345"))
                {
                    Intent intent=new Intent(zhuce.this, Login.class);
                    startActivity(intent);
                    Toast.makeText(zhuce.this,"注册成功",Toast.LENGTH_LONG).show();
                }
            }
        });
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(zhuce.this,Login.class);
                startActivity(intent);
            }
        });
    }
}