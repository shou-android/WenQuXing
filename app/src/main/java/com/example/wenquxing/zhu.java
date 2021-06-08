package com.example.wenquxing;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class zhu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu);
        Button ReminderTest = findViewById(R.id.ReminderTest);
        Button work = findViewById(R.id.work);
        ReminderTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(zhu.this, Reminder.class);
                startActivity(intent);
            }
        });
        work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(zhu.this,plan.class);
                startActivity(intent);
            }
        });
    }
}