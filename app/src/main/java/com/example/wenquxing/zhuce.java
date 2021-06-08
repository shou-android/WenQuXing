package com.example.wenquxing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class zhuce extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        Button login=(Button)findViewById(R.id.btn_login1);
        Button fanhui=(Button)findViewById(R.id.btn_return);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(zhuce.this, zhu.class);
                startActivity(intent);
            }
        });
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(zhuce.this,login.class);
                startActivity(intent);
            }
        });
    }
}